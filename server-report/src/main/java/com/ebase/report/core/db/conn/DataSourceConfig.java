package com.ebase.report.core.db.conn;

import com.ebase.report.core.db.DataBaseType;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 数据库配置
 */
public class DataSourceConfig {
    //数据库名称
    private String dataSourceName;
    //数据库类型
    private DataBaseType dataBaseType = DataBaseType.DB_TYPE_MYSQL;
    private ConnPoolType connPoolType = ConnPoolType.CONN_POOL_TYPE_HIKARI;
    private String dataSourceURL;

    private String dataSourceUserName;
    private String dataSourcePassword;

    //
    private String dataSourceValidateSQL;

    private int idleConnectionTestPeriodSecs = 120;

    // 连接池的初始连接数
    private int dataSourceInitialSize = 5;
    // 连接池最大连接数
    private int dataSourceMaxActive = 15;
    // 超时时间
    private long dataSourceMaxWait = 30000;     //checkoutTimeout
    //The maximum number of connections that can remain idle in the pool,
    //without extra ones being released, or negative for no limit.
    private int dataSourceMaxIdle = 5;
    //The maximum number of open statements that can be allocated from the statement pool
    //at the same time, or non-positive for no limit.
    private int dataSourceMaxStatements = 0;
    // For partitioned data sources like BoneCP
    private int dataSourcePoolPartitions = 1;
    // over 5 minutes, the idle connections will be returned to shrink the pool
    private int dataSourceIdleMaxMilliseconds = 1000 * 60 * 5;

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public DataBaseType getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(DataBaseType dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public ConnPoolType getConnPoolType() {
        return connPoolType;
    }

    public void setConnPoolType(ConnPoolType connPoolType) {
        this.connPoolType = connPoolType;
    }

    public String getDataSourceURL() {
        return dataSourceURL;
    }

    public void setDataSourceURL(String dataSourceURL) {
        this.dataSourceURL = dataSourceURL;
    }

    public String getDataSourceUserName() {
        return dataSourceUserName;
    }

    public void setDataSourceUserName(String dataSourceUserName) {
        this.dataSourceUserName = dataSourceUserName;
    }

    public String getDataSourcePassword() {
        return dataSourcePassword;
    }

    public void setDataSourcePassword(String dataSourcePassword) {
        this.dataSourcePassword = dataSourcePassword;
    }

    public String getDataSourceValidateSQL() {
        return dataSourceValidateSQL;
    }

    public void setDataSourceValidateSQL(String dataSourceValidateSQL) {
        this.dataSourceValidateSQL = dataSourceValidateSQL;
    }

    public int getIdleConnectionTestPeriodSecs() {
        return idleConnectionTestPeriodSecs;
    }

    public void setIdleConnectionTestPeriodSecs(int idleConnectionTestPeriodSecs) {
        this.idleConnectionTestPeriodSecs = idleConnectionTestPeriodSecs;
    }

    public int getDataSourceInitialSize() {
        return dataSourceInitialSize;
    }

    public void setDataSourceInitialSize(int dataSourceInitialSize) {
        this.dataSourceInitialSize = dataSourceInitialSize;
    }

    public int getDataSourceMaxActive() {
        return dataSourceMaxActive;
    }

    public void setDataSourceMaxActive(int dataSourceMaxActive) {
        this.dataSourceMaxActive = dataSourceMaxActive;
    }

    public long getDataSourceMaxWait() {
        return dataSourceMaxWait;
    }

    public void setDataSourceMaxWait(long dataSourceMaxWait) {
        this.dataSourceMaxWait = dataSourceMaxWait;
    }

    public int getDataSourceMaxIdle() {
        return dataSourceMaxIdle;
    }

    public void setDataSourceMaxIdle(int dataSourceMaxIdle) {
        this.dataSourceMaxIdle = dataSourceMaxIdle;
    }

    public int getDataSourceMaxStatements() {
        return dataSourceMaxStatements;
    }

    public void setDataSourceMaxStatements(int dataSourceMaxStatements) {
        this.dataSourceMaxStatements = dataSourceMaxStatements;
    }

    public int getDataSourcePoolPartitions() {
        return dataSourcePoolPartitions;
    }

    public void setDataSourcePoolPartitions(int dataSourcePoolPartitions) {
        this.dataSourcePoolPartitions = dataSourcePoolPartitions;
    }

    public int getDataSourceIdleMaxMilliseconds() {
        return dataSourceIdleMaxMilliseconds;
    }

    public void setDataSourceIdleMaxMilliseconds(int dataSourceIdleMaxMilliseconds) {
        this.dataSourceIdleMaxMilliseconds = dataSourceIdleMaxMilliseconds;
    }
}

