package com.ebase.report.core.pageUtil;

import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.core.utils.StringUtil;
import com.ebase.report.model.dynamic.ReportDatasource;
import com.ebase.report.model.dynamic.ReportDynamicParam;

import java.util.ArrayList;
import java.util.List;

public class PageReportDetail extends PageDTO{

    private static final String limit = " limit ";

    //orcale sql拼装

    private static final String oracle_sql = "SELECT * FROM  \n" +
            "(  \n" +
            "SELECT A.*, ROWNUM RN  \n" +
            "FROM (${sql}) A  \n" +
            "WHERE ROWNUM <= ${end}  \n" +
            ")  \n" +
            "WHERE RN >= ${stat}";

    private static final  String [] SQL_VARIABLE = new String[]{"${sql}","${stat}","${end}"};

    private static final  String OFFSET = "offset";

    private static final  String END = "end";
    /**
     * 拼装分页sql
     * @return
     */
    public static String getDetailSql(String sql, ReportDatasource reportDatasource, PageDTO pageDTO){

        long count = pageDTO.getTotal(); //总数

        String databaseType = reportDatasource.getDatabaseType(); //数据库类型

        ReportDynamicParam reportDynamicParam = reportDatasource.getReportDynamicParam();  //参数 -- （再进行一次sql拼装，order逻辑)

        String detailSql = "";
        if(databaseType.equals(DataBaseType.TYPE_NAME_MYSQL)){
            //mysql
            pageDTO.setTotal(count);

            sql = reportDynamicParam.toOrderSql(new StringBuilder(sql), reportDatasource.getDatabaseType()); //去排序

            detailSql = sql + limit + pageDTO.getStartRow() + "," + pageDTO.getPageSize();

        }else if(databaseType.equals(DataBaseType.TYPE_NAME_ORACLE)){
            //oracle
            Paging paging = new Paging(pageDTO.getPageNum(),pageDTO.getPageSize());
            paging.setTotal(count);

            Object offset = paging.getSearchMap().get(OFFSET);
            Object end = paging.getSearchMap().get(END);

            //去排序
            sql = reportDynamicParam.toOrderSql(new StringBuilder(sql), reportDatasource.getDatabaseType()); //去排序

            String[] strings2 = new String[]{sql,String.valueOf(offset),String.valueOf(end)};

            detailSql = StringUtil.replaceEach(oracle_sql, SQL_VARIABLE, strings2);

        }else if(databaseType.equals(DataBaseType.TYPE_NAME_DB2)){
            //db2

            pageDTO.setTotal(count);

            sql = reportDynamicParam.toOrderSql(new StringBuilder(sql), reportDatasource.getDatabaseType()); //去排序


            detailSql = sql + limit + pageDTO.getStartRow() + "," + pageDTO.getPageSize();

        }else if(databaseType.equals(DataBaseType.TYPE_NAME_HIVE)){
            //hive

        }


        return detailSql;
    }

    /**
     * 所有的分页对象
     * @param limitcount
     * @param count
     * @return
     */
    public static List<PageDTO> getPages(Integer limitcount, Integer count) {

        Integer page = getPage(limitcount, count);

        List<PageDTO> list = new ArrayList<>(page);

        for(int i = 0; i < page; i ++){
            // 先判断
            int x = limitcount;
            if(i == (page - 1)){
                x = count % limitcount == 0 ? limitcount : count % limitcount;
            }

            PageDTO pageDTO = new PageDTO(i,x);
            pageDTO.setTotal(count);
            list.add(pageDTO);
        }

        return list;
    }

    /**
     * 分页总数
     * @param limitcount
     * @param count
     * @return
     */
    public static Integer getPage(Integer limitcount, Integer count) {
        Integer page = 0;
        if(count % limitcount != 0){
            page = count / limitcount + 1;
        }else{
            page = count / limitcount;
        }
        return page;
    }


}
