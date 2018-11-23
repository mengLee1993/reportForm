package com.ebase.report.core.pageUtil;

import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.core.utils.StringUtil;

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
    public static String getDetailSql(String sql,String databaseType,PageDTO pageDTO,Integer count){

        String detailSql = "";
        if(databaseType.equals(DataBaseType.TYPE_NAME_MYSQL)){
            //mysql
            pageDTO.setTotal(count);
            detailSql = sql + limit + pageDTO.getStartRow() + "," + pageDTO.getPageSize();

        }else if(databaseType.equals(DataBaseType.TYPE_NAME_ORACLE)){
            //oracle
            Paging paging = new Paging(pageDTO.getPageNum(),pageDTO.getPageSize());
            paging.setTotal(count);

            Object offset = paging.getSearchMap().get(OFFSET);
            Object end = paging.getSearchMap().get(END);

            String[] strings2 = new String[]{sql,String.valueOf(offset),String.valueOf(end)};

            detailSql = StringUtil.replaceEach(oracle_sql, SQL_VARIABLE, strings2);

        }else if(databaseType.equals(DataBaseType.TYPE_NAME_DB2)){
            //db2

            pageDTO.setTotal(count);
            detailSql = sql + limit + pageDTO.getStartRow() + "," + pageDTO.getPageSize();

        }else if(databaseType.equals(DataBaseType.TYPE_NAME_HIVE)){
            //hive

        }


        return detailSql;
    }




}
