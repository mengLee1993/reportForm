package com.ebase.report;

import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.core.db.conn.ConnPoolType;
import com.ebase.report.core.db.conn.DataSourceConfig;
import com.ebase.report.core.db.conn.DataSourceManager;
import com.ebase.report.core.db.exception.DbException;
import com.ebase.report.core.db.handler.ReportHandler;
import com.ebase.report.main.ServerReportApplication;
import com.ebase.report.model.RptDataField;
import com.ebase.report.model.RptDataTable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Connection;
import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerReportApplication.class)
@EnableAutoConfiguration
public class DbTest {
    @Resource
    ReportHandler reportHandler;

    @Test
    public void testDb() {
        DataSourceConfig sourceConfig = new DataSourceConfig();
        sourceConfig.setConnPoolType(ConnPoolType.CONN_POOL_TYPE_HIKARI);
        sourceConfig.setDataBaseType(DataBaseType.DB_TYPE_DB2);
        sourceConfig.setDataSourceName("sgdw");
        sourceConfig.setDataSourceURL("jdbc:db2://10.68.28.25:50049/report:user=db2inst1;password=db2inst1");
        //jdbc:db2://10.68.28.25:50049/sgdw:user=db2inst1;password=db2inst1;

        sourceConfig.setDataSourceUserName("db2inst1");
        sourceConfig.setDataSourcePassword("db2inst1");
        sourceConfig.setDataSourceMaxActive(15);

        try {
            // 初始化数据库连接
            DataSourceManager.get().append(sourceConfig);
        } catch (DbException e) {

        }

        DataSourceConfig mysqlConfig = new DataSourceConfig();
        mysqlConfig.setConnPoolType(ConnPoolType.CONN_POOL_TYPE_HIKARI);
        mysqlConfig.setDataBaseType(DataBaseType.DB_TYPE_MYSQL);
        mysqlConfig.setDataSourceName("report");
        mysqlConfig.setDataSourceURL("jdbc:mysql://10.5.210.201:3306/reports?useUnicode=true&amp;byteacterEncoding=utf-8&amp;useOldAliasMetadataBehavior=true");
        mysqlConfig.setDataSourceUserName("root");
        mysqlConfig.setDataSourcePassword("root");
        mysqlConfig.setDataSourceMaxActive(15);

        try {
            // 初始化数据库连接
            DataSourceManager.get().append(mysqlConfig);
        } catch (DbException e) {

        }

        try {
            List<RptDataTable> tables = reportHandler.queryAllTables("sgdw");

            List<RptDataField> fields = reportHandler.queryFields("sgdw", "movie_data_10w");

            for (RptDataTable dataTable : tables) {
                System.out.println(dataTable.getTableCode());
            }

            for (RptDataField field : fields) {
                System.out.println(field.getFieldCode() + "," + field.getFieldName() + "," + field.getFieldType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
