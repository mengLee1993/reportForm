package com.ebase.report.cache;

import com.ebase.report.model.RptDataField;
import com.ebase.report.model.RptDataTable;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.event.EventType;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
@Configuration
@EnableCaching
public class CacheConfig {

    //监听器配置
    private static CacheEventListenerConfigurationBuilder cacheEventListenerConfiguration = CacheEventListenerConfigurationBuilder
            .newEventListenerConfiguration(new EhCacheEventListenerObject(), EventType.EXPIRED)
            .unordered().asynchronous();

    /**
     * 初始化Ehcache缓存对象
     */
    public static void init(String diskPath) {
        System.out.println("[Ehcache 配置初始化<开始>]");
        // 配置默认缓存属性
        CacheConfiguration<String, RptDataTable> rptTableCacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                // 缓存数据K和V的数值类型
                // 在ehcache3.3中必须指定缓存键值类型,如果使用中类型与配置的不同,会报类转换异常
                String.class, RptDataTable.class,
                ResourcePoolsBuilder
                        .newResourcePoolsBuilder()
                        //设置缓存堆容纳元素个数(JVM内存空间)超出个数后会存到offheap中
                        .heap(1000L, EntryUnit.ENTRIES)
                        //设置堆外储存大小(内存存储) 超出offheap的大小会淘汰规则被淘汰
                        .offheap(10l, MemoryUnit.MB)
                        // 配置磁盘持久化储存(硬盘存储)用来持久化到磁盘,这里设置为false不启用
                        .disk(50l, MemoryUnit.MB, false)
//        ).withExpiry(Expirations.timeToLiveExpiration(Duration.of(0L, TimeUnit.SECONDS))
//        ).withExpiry(Expirations.timeToIdleExpiration(
//                //设置被访问后过期时间(同时设置和TTL和TTI之后会被覆盖,这里TTI生效,之前版本xml配置后是两个配置了都会生效)
//                Duration.of(1L, TimeUnit.SECONDS))

        ).add(cacheEventListenerConfiguration).build();

        // 缓存淘汰策略 默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
        /*.withEvictionAdvisor(
                new OddKeysEvictionAdvisor<Long, String>())*/

        // CacheManager管理缓存
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                // 硬盘持久化地址
                .with(CacheManagerBuilder.persistence(diskPath))
                // 设置一个默认缓存配置
                .withCache("rptDataTable", rptTableCacheConfig)
                //创建之后立即初始化
                .build(true);

        CacheConfiguration<String, RptDataField> rptFieldCacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                // 缓存数据K和V的数值类型
                // 在ehcache3.3中必须指定缓存键值类型,如果使用中类型与配置的不同,会报类转换异常
                String.class, RptDataField.class,
                ResourcePoolsBuilder
                        .newResourcePoolsBuilder()
                        //设置缓存堆容纳元素个数(JVM内存空间)超出个数后会存到offheap中
                        .heap(10000L, EntryUnit.ENTRIES)
                        //设置堆外储存大小(内存存储) 超出offheap的大小会淘汰规则被淘汰
                        .offheap(100L, MemoryUnit.MB)
                        // 配置磁盘持久化储存(硬盘存储)用来持久化到磁盘,这里设置为false不启用
                        .disk(500L, MemoryUnit.MB, false)
        ).withExpiry(Expirations.timeToLiveExpiration(Duration.of(2L, TimeUnit.SECONDS))
        ).withExpiry(Expirations.timeToIdleExpiration(
                //设置被访问后过期时间(同时设置和TTL和TTI之后会被覆盖,这里TTI生效,之前版本xml配置后是两个配置了都会生效)
                Duration.of(2L, TimeUnit.SECONDS))

        ).add(cacheEventListenerConfiguration).build();

        cacheManager.createCache("rptDataField", rptFieldCacheConfig);

        ReportCacheManager.get().setCacheManager(cacheManager);

        System.out.println("[Ehcache配置初始化<完成>]");

    }
}