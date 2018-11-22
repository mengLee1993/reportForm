package com.ebase.report.core.db.conn;

import com.ebase.report.core.db.DataBaseUtil;
import com.ebase.report.core.db.exception.DbException;
import com.jolbox.bonecp.BoneCPDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class DataSourceManager {
    //
    private static final Logger logger = LoggerFactory.getLogger(DataSourceManager.class);

    private static final long HEALTH_CHECKER_INTERVAL_MSECS = 1000 * 60 * 2;

    //
    private static Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
    private static Map<String, DataSourceConfig> dataSourceConfigMap = new HashMap<String, DataSourceConfig>();
    //
    private static final DataSourceManager instance = new DataSourceManager();

    private DataSourceManager() {
    }

    public static DataSourceManager get() {
        return instance;
    }

    /**
     * @param dataSourceConfig
     * @throws DbException
     */
    public synchronized void append(DataSourceConfig dataSourceConfig) throws DbException {
        if (dataSourceMap.containsKey(dataSourceConfig.getDataSourceName())) {
            closeDataSource(dataSourceConfigMap.get(dataSourceConfig.getDataSourceName()));
        }

        try {

            dataSourceConfigMap.put(dataSourceConfig.getDataSourceName(), dataSourceConfig);
            DataSource returnDs = createDataSource(dataSourceConfig);
            if(null != returnDs){
                dataSourceMap.put(dataSourceConfig.getDataSourceName(), returnDs);
            }
        } catch (DbException e) {
            logger.error("创建数据库连接池异常，数据源："+dataSourceConfig.getDataSourceName(), e);
        }

        //start the timer to validate the sql.
//        Timer dataSourceHealthCheckTimer = new Timer();
//        dataSourceHealthCheckTimer.scheduleAtFixedRate(new DataSourceHealthChecker(), HEALTH_CHECKER_INTERVAL_MSECS, HEALTH_CHECKER_INTERVAL_MSECS);
    }

    public DataSource createDataSource(DataSourceConfig dataSourceConfig) throws DbException {
        DataSource returnDs = null;
        if (ConnPoolType.CONN_POOL_TYPE_BONECP.equals(dataSourceConfig.getConnPoolType())) {
            returnDs = createDataSourceBoneCP(dataSourceConfig);
        } else if (ConnPoolType.CONN_POOL_TYPE_HIKARI.equals(dataSourceConfig.getConnPoolType())) {
            returnDs = createDataSourceHikari(dataSourceConfig);
        } else {
            logger.error("未知的数据库类型");
        }

        return returnDs;
    }

    private DataSource createDataSourceBoneCP(DataSourceConfig dataSourceConfig) {
        BoneCPDataSource boneCPDs = new BoneCPDataSource();

        boneCPDs.setPoolName(dataSourceConfig.getDataSourceName());
        boneCPDs.setDriverClass(dataSourceConfig.getDataBaseType().getDriverClassName());
        boneCPDs.setJdbcUrl(dataSourceConfig.getDataSourceURL());

        //set the password and username of the db account
        boneCPDs.setUsername(dataSourceConfig.getDataSourceUserName());
        if (!StringUtils.isEmpty(dataSourceConfig.getDataSourceValidateSQL())) {
            boneCPDs.setInitSQL(dataSourceConfig.getDataSourceValidateSQL());
        }

        boneCPDs.setPassword(dataSourceConfig.getDataSourcePassword());

        boneCPDs.setMaxConnectionsPerPartition(dataSourceConfig.getDataSourceMaxActive());
        boneCPDs.setMinConnectionsPerPartition(dataSourceConfig.getDataSourceInitialSize());
        boneCPDs.setPartitionCount(dataSourceConfig.getDataSourcePoolPartitions());
        boneCPDs.setIdleMaxAge(dataSourceConfig.getDataSourceMaxIdle(), TimeUnit.MILLISECONDS);
        boneCPDs.setConnectionTimeout(dataSourceConfig.getDataSourceMaxWait(), TimeUnit.MILLISECONDS);
        boneCPDs.setIdleConnectionTestPeriodInMinutes(dataSourceConfig.getIdleConnectionTestPeriodSecs());

        return boneCPDs;
    }

    private DataSource createDataSourceHikari(DataSourceConfig dataSourceConfig) {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName(dataSourceConfig.getDataBaseType().getDriverClassName());
        hikariConfig.setJdbcUrl(dataSourceConfig.getDataSourceURL());
        hikariConfig.setUsername(dataSourceConfig.getDataSourceUserName());
        hikariConfig.setPassword(dataSourceConfig.getDataSourcePassword());

        hikariConfig.setMinimumIdle(dataSourceConfig.getDataSourceInitialSize());
        hikariConfig.setMaximumPoolSize(dataSourceConfig.getDataSourceMaxActive());
        hikariConfig.setConnectionTestQuery(dataSourceConfig.getDataSourceValidateSQL());

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "256");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "1024");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", true);
        hikariConfig.addDataSourceProperty("useLocalSessionState", true);
        hikariConfig.addDataSourceProperty("useLocalTransactionState", true);
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", true);
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", true);
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", true);
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", true);
        hikariConfig.addDataSourceProperty("maintainTimeStats", false);

        return new HikariDataSource(hikariConfig);
    }

    public DataSource getDataSource(String dsName) {
        DataSource ds = dataSourceMap.get(dsName.toLowerCase());
        if (ds == null) {
            logger.error("Creating the datasource, " + dsName + ", failed.");
        }

        return ds;
    }

    public DataSourceConfig getDataSourceConfig(String dsName) {
        return dataSourceConfigMap.get(dsName.toLowerCase());
    }

    public void closeDataSource(DataSourceConfig dataSourceConfig){
        if (ConnPoolType.CONN_POOL_TYPE_BONECP.equals(dataSourceConfig.getConnPoolType())) {
            BoneCPDataSource dataSource = (BoneCPDataSource)dataSourceMap.get(dataSourceConfig.getDataSourceName());
            dataSource.close();
        } else if (ConnPoolType.CONN_POOL_TYPE_HIKARI.equals(dataSourceConfig.getConnPoolType())) {
            HikariDataSource dataSource = (HikariDataSource)dataSourceMap.get(dataSourceConfig.getDataSourceName());
            dataSource.close();
        } else {
            logger.error("未知的数据库类型");
        }
        dataSourceMap.remove(dataSourceConfig.getDataSourceName());
    }

    class DataSourceHealthChecker extends TimerTask {

        public void run() {
            logger.debug("Start Datasource Health Checking.");

            for (Map.Entry<String, DataSource> entry : dataSourceMap.entrySet()) {
                Connection conn = null;

                try {
                    //
                    conn = entry.getValue().getConnection();
                    //
                    if (conn != null) {
                        //
                        DataSourceConfig config = getDataSourceConfig(entry.getKey());
                        String validateSql = config.getDataBaseType().getValidateSql();
                        if (!StringUtils.isEmpty(validateSql)) {
                            logger.info("The datasource " + entry.getKey() + "'s current time is " + processValidateSql(conn, validateSql));
                        } else {
                            logger.info("DataSourceHealthChecker run: the datasource " + entry.getKey() + " hasn't validate sql.");
                        }
                    } else {
                        logger.info("DataSourceHealthChecker run: the datasource " + entry.getKey() + "'s connection is null.");
                    }
                } catch (Exception e) {
                    logger.error("Some errors occurred when checking the datasource " + entry.getKey() + "'s Health.", e);
                } finally {
                    DataBaseUtil.closeConnection(conn);
                }
            }

            logger.debug("Datasource Health Checking is Completed.");
        }

        private Timestamp processValidateSql(Connection conn, String sql) throws SQLException {
            Timestamp returnValue = null;

            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                pstmt = conn.prepareStatement(sql);

                rs = pstmt.executeQuery();
                if (rs.next()) {
                    returnValue = rs.getTimestamp(1);
                }
            } finally {
                DataBaseUtil.closeResultSet(rs);
                DataBaseUtil.closeStatment(pstmt);
            }

            return returnValue;
        }
    }

    //清空数据连接
    public void destroy(String dataSourceName){
        DataSourceConfig dataSourceConfig = dataSourceConfigMap.get(dataSourceName);
        closeDataSource(dataSourceConfig);
    }

}
