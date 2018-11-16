package com.ebase.report.cache;

import com.ebase.report.model.RptDataField;
import com.ebase.report.model.RptDataTable;
import org.ehcache.Cache;
import org.ehcache.CacheManager;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class ReportCacheManager {
    private CacheManager cacheManager;

    private static final ReportCacheManager instance = new ReportCacheManager();

    private ReportCacheManager() {

    }

    public static ReportCacheManager get() {
        return instance;
    }

    //
    public Cache<String, RptDataTable> getTableCache() {
        return cacheManager.getCache("rptDataTable", String.class, RptDataTable.class);
    }

    public Cache<String, RptDataField> getFieldCache() {
        return cacheManager.getCache("rptDataField", String.class, RptDataField.class);
    }

    public void setCacheManager(CacheManager cacheManager){
        this.cacheManager = cacheManager;
    }
}
