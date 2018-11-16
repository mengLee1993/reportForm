package com.ebase.report.main;

import com.ebase.report.core.EnvironmentUtil;
import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.core.db.conn.ConnPoolType;
import com.ebase.report.core.db.conn.DataSourceConfig;
import com.ebase.report.core.db.conn.DataSourceManager;
import com.ebase.report.core.db.exception.DbException;
import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

//作为一个配置类
//@Configuration
//扫描Mapper接口
@MapperScan(basePackages = "com.ebase.report.dao")
//开启事务
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "com.ebase.report")
@EnableCaching
public class ServerReportApplication {


    private static Logger LOG = LoggerFactory.getLogger(ServerReportApplication.class);

    public static void main(String[] args) {
        /*SpringApplication.run(ServerReportApplication.class, args);
         */
        EnvironmentUtil.setSystemEnv(args);
        SpringApplication application = new SpringApplication(ServerReportApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
        LOG.info("service-report start success");

//        //初始化的时候加载一个库 其实这是不对的
//        DataSourceConfig sourceConfig = new DataSourceConfig();
//        sourceConfig.setConnPoolType(ConnPoolType.CONN_POOL_TYPE_HIKARI);
//        sourceConfig.setDataBaseType(DataBaseType.DB_TYPE_MYSQL);
//        sourceConfig.setDataSourceName("reports");
//        sourceConfig.setDataSourceURL("jdbc:mysql://127.0.0.1:3306/reports?useUnicode=true&characterEncoding=UTF8&useSSL=true");
//        sourceConfig.setDataSourceUserName("root");
//        sourceConfig.setDataSourcePassword("cjkaka");
//        sourceConfig.setDataSourceMaxActive(15);
//
//        try {
//            // 初始化数据库连接
//            DataSourceManager.get().append(sourceConfig);
//        } catch (DbException e) {
//
//        }

    }

    //分页插件
//    @Bean
//    public PageHelper pageHelper() {
//        PageHelper pageHelper = new PageHelper();
//        Properties p = new Properties();
//        p.setProperty("offsetAsPageNum", "true");
//        p.setProperty("rowBoundsWithCount", "true");
//        p.setProperty("reasonable", "true");
//        p.setProperty("dialect","mysql");
//        p.setProperty("offsetAsPageNum","false");
//        p.setProperty("rowBoundsWithCount","false");
//        p.setProperty("rowBoundsWithCount","false");
//        p.setProperty("pageSizeZero","true");
//        p.setProperty("reasonable","true");
//        pageHelper.setProperties(p);
//
//        return pageHelper;
//    }

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("dialect","mysql");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
