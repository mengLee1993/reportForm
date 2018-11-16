package com.ebase.report.main;

import com.ebase.report.service.init.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: spring boot 启动后加载系统资源
 * 1. 初始数据源连接池
 * 2. 初始化缓存（数据源、数据表、字段等）
 */
@Component
@Order(1)//如果多个自定义的 ApplicationRunner  ，用来标明执行的顺序
public class ReportCommandLineRunner implements CommandLineRunner {


    @Autowired
    private InitService initService;

    @Override
    public void run(String... strings) throws Exception {
        initService.init(strings);
    }

    private void testDb() {

    }
}
