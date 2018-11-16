package com.ebase.report.model.dynamic;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

//动态数据表

public class ReportTable {

    //数据表id
    private Long tableId;

    //数据表code
    private String tableCode;

    //数据表名称
    private String tableName;

    //数据表备注
    private String demo;

    //数据表类型
    private String tableType;

    //数据源名称
    private String datasourceName;

    //主题id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long personalSubjectId;


    private int pageSize = 10 ;

    private int pageNum = 1 ;
//    private

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public Long getPersonalSubjectId() {
        return personalSubjectId;
    }

    public void setPersonalSubjectId(Long personalSubjectId) {
        this.personalSubjectId = personalSubjectId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getDatasourceName() {
        return datasourceName;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
    }
}
