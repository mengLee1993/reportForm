package com.ebase.report.core.pageUtil;

import com.ebase.report.core.db.DataBaseType;

public class PageReportDetail extends PageDTO{

    private static final String limit = " limit ";



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
