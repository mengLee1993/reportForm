package com.ebase.report.service.init.impl;

import com.ebase.report.cache.CacheConfig;
import com.ebase.report.cache.CacheProperty;
import com.ebase.report.cache.ReportCacheManager;
import com.ebase.report.core.db.handler.ReportHandler;
import com.ebase.report.dao.RptDatasourceMapper;
import com.ebase.report.model.RptDataTable;
import com.ebase.report.model.RptDatasource;
import com.ebase.report.service.init.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: InitServiceImpl
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/11/2 19:06
 */
@Service
public class InitServiceImpl implements InitService {

    @Resource
    ReportHandler reportHandler;

    @Autowired
    RptDatasourceMapper rptDatasourceMapper;

    @Autowired
    CacheProperty cacheProperty;

    @Override
    public void init(String... strings) {

        System.out.println("------初始化缓存------");
        System.out.println("------缓存地址 ----"+cacheProperty.getDiskPath());
        // 缓存初始化
        CacheConfig.init(cacheProperty.getDiskPath());
        System.out.println("------初始化缓存完成------");

        System.out.println("------初始化数据库连接池------");
        List<RptDatasource> list = rptDatasourceMapper.selectAll();

        for (RptDatasource datasource : list) {
            try {
                reportHandler.createConn(datasource);
            } catch (Exception e) {
                System.err.println("------数据库："+ datasource.getDatasourceName() +",连接池创建失败------");
            }
        }
        System.out.println("------初始化数据库连接池完成------");
    }
}
