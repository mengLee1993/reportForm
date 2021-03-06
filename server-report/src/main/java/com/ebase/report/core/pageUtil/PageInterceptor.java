package com.ebase.report.core.pageUtil;

import com.ebase.report.core.utils.StringUtil;
import com.github.pagehelper.Dialect;
import com.github.pagehelper.PageException;
import com.github.pagehelper.cache.Cache;
import com.github.pagehelper.cache.CacheFactory;
import com.github.pagehelper.util.MSUtils;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;

@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
)})
public class PageInterceptor implements Interceptor {

    protected Cache<String, MappedStatement> msCountMap = null;
    private Dialect dialect;
    private String default_dialect_class = "com.github.pagehelper.PageHelper";
    private Field additionalParametersField;
    private String countSuffix = "_COUNT";
    private SqlSource sqlSource;
    CCJSqlParserManager parserManager = new CCJSqlParserManager();

    public Object intercept(Invocation invocation) throws Throwable {
        try {
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement)args[0];
            Object parameter = args[1];
            RowBounds rowBounds = (RowBounds)args[2];
            ResultHandler resultHandler = (ResultHandler)args[3];
            Executor executor = (Executor)invocation.getTarget();
            CacheKey cacheKey;
            BoundSql boundSql;
            if (args.length == 4) {
                boundSql = ms.getBoundSql(parameter);
                cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
            } else {
                cacheKey = (CacheKey)args[4];
                boundSql = (BoundSql)args[5];
            }
//            String sql = boundSql.getSql();
//            Select select = (Select)parserManager.parse(new StringReader(sql));
//            //访问各个visitor
//            select.getSelectBody().accept(new SelectVisitorImpl());
//            ReflectUtil.setFieldValue(boundSql, "sql",  select.toString());
//            System.out.println(boundSql.getSql());

//            Statement statement = (Statement) CCJSqlParserUtil.parse(boundSql.getSql());
//        	Select selectStatement = (Select) statement;
//        	SelectBody selectBody = selectStatement.getSelectBody();
//            PlainSelect plainSelect = (PlainSelect) selectBody;
//    		Expression where = plainSelect.getWhere();
    		//String name = plainSelect.getFromItem().getAlias().getName();

            //ReflectUtil.setFieldValue(boundSql, "sql",  boundSql.getSql()+" and acct.status=1 ");
//            DataAuthoritySql dataAuthoritySql=(DataAuthoritySql)boundSql;
//            //dataAuthoritySql.setSql(dataAuthoritySql.getSql()+" and 1=1 ");
//            boundSql=(BoundSql)dataAuthoritySql;

            List resultList;
            if (this.dialect.skip(ms, parameter, rowBounds)) {
                resultList = executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
            } else {
                String msId = ms.getId();
                Configuration configuration = ms.getConfiguration();
                Map<String, Object> additionalParameters = (Map)this.additionalParametersField.get(boundSql);
                if (this.dialect.beforeCount(ms, parameter, rowBounds)) {
                    String countMsId = msId + this.countSuffix;
                    MappedStatement countMs = this.getExistedMappedStatement(configuration, countMsId);
                    Long count;
                    if (countMs != null) {
                        CacheKey countKey = executor.createCacheKey(countMs, parameter, RowBounds.DEFAULT, boundSql);
                        BoundSql countBoundSql = countMs.getBoundSql(parameter);
                        Object countResultList = executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
                        count = ((Number)((List)countResultList).get(0)).longValue();
                        return count;
                    } else {
                        countMs = (MappedStatement)this.msCountMap.get(countMsId);
                        if (countMs == null) {
                            countMs = MSUtils.newCountMappedStatement(ms, countMsId);
                            this.msCountMap.put(countMsId, countMs);
                        }

                        count = this.executeAutoCount(executor, countMs, parameter, boundSql, rowBounds, resultHandler);
                    }

                    if (!this.dialect.afterCount(count, parameter, rowBounds)) {
                        Object var24 = this.dialect.afterPage(new ArrayList(), parameter, rowBounds);
                        return var24;
                    }
                }

                if (!this.dialect.beforePage(ms, parameter, rowBounds)) {
                    resultList = executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, boundSql);
                } else {
                    parameter = this.dialect.processParameterObject(ms, parameter, boundSql, cacheKey);
                    String pageSql = this.dialect.getPageSql(ms, boundSql, parameter, rowBounds, cacheKey);
                    BoundSql pageBoundSql = new BoundSql(configuration, pageSql, boundSql.getParameterMappings(), parameter);
                    Iterator var17 = additionalParameters.keySet().iterator();

                    while(true) {
                        if (!var17.hasNext()) {
                            resultList = executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, pageBoundSql);
                            break;
                        }

                        String key = (String)var17.next();
                        pageBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
                    }
                }
            }

            Object var22 = this.dialect.afterPage(resultList, parameter, rowBounds);
            return var22;
        } finally {
            this.dialect.afterAll();
        }
    }



    private Long executeAutoCount(Executor executor, MappedStatement countMs, Object parameter, BoundSql boundSql, RowBounds rowBounds, ResultHandler resultHandler) throws IllegalAccessException, SQLException {
        Map<String, Object> additionalParameters = (Map)this.additionalParametersField.get(boundSql);
        CacheKey countKey = executor.createCacheKey(countMs, parameter, RowBounds.DEFAULT, boundSql);
        String countSql = this.dialect.getCountSql(countMs, boundSql, parameter, rowBounds, countKey);
        BoundSql countBoundSql = new BoundSql(countMs.getConfiguration(), countSql, boundSql.getParameterMappings(), parameter);
        Iterator var11 = additionalParameters.keySet().iterator();

        while(var11.hasNext()) {
            String key = (String)var11.next();
            countBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
        }

        Object countResultList = executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
        Long count = (Long)((List)countResultList).get(0);
        return count;
    }

    private MappedStatement getExistedMappedStatement(Configuration configuration, String msId) {
        MappedStatement mappedStatement = null;

        try {
            mappedStatement = configuration.getMappedStatement(msId, false);
        } catch (Throwable var5) {
            ;
        }

        return mappedStatement;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        this.msCountMap = CacheFactory.createCache(properties.getProperty("msCountCache"), "ms", properties);
        String dialectClass = properties.getProperty("dialect");
        if (StringUtil.isEmpty(dialectClass)) {
            dialectClass = this.default_dialect_class;
        }

        try {
            Class<?> aClass = Class.forName(dialectClass);
            this.dialect = (Dialect)aClass.newInstance();
        } catch (Exception var6) {
            throw new PageException(var6);
        }

        this.dialect.setProperties(properties);
        String countSuffix = properties.getProperty("countSuffix");
        if (StringUtil.isNotEmpty(countSuffix)) {
            this.countSuffix = countSuffix;
        }

        try {
            this.additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
            this.additionalParametersField.setAccessible(true);
        } catch (NoSuchFieldException var5) {
            throw new PageException(var5);
        }
    }
}
