package com.ebase.report.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
@SuppressWarnings("rawtypes")
public class EhCacheEventListenerObject implements CacheEventListener {

    protected Logger logger = LogManager.getLogger(getClass());

    @Override
    public void onEvent(CacheEvent event) {
        //一级缓存超时放进二级缓存中
        logger.info("超时了,key:"+event.getKey());
//
    }
}
