package com.ebase.report.model;

import java.io.Serializable;
import java.util.Date;

/**
 */
public class RptAnalyseLog implements Serializable {
    /**
     * Database Column Remarks:
     *   日志ID
     */
    private Long logId;

    /**
     * Database Column Remarks:
     *   操作人ID
     */
    private String opUserid;

    /**
     * 操作人名称
     */
    private String opUserName;

    /**
     * Database Column Remarks:
     *   操作IP
     */
    private String opIp;

    /**
     * Database Column Remarks:
     *   日志时间
     */
    private Date logTime;

    /**
     * Database Column Remarks:
     *   执行sql内容
     */
    private String analyseSql;


    /**
     * sql执行时间
     */
    private Long sqlExecutionTime;

    /**
     */
    private static final long serialVersionUID = 1L;

    private int startRow ;

    private int pageSize = 10;



    private Long datasourceId;          //数据源表中的ID

    private String datasourceName;      //数据源表中的名字

    private Long tableId;               //数据表的ID

    private String tableName;           //数据表的名字

    private Date makeTime ;             //操作时间

    public Date getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Date makeTime) {
        this.makeTime = makeTime;
    }


    private Long personalSubjectId;  //主题ID

    private String subjectName;  //主题名称

    public Long getDatasourceId() { return datasourceId; }

    public void setDatasourceId(Long datasourceId) { this.datasourceId = datasourceId; }

    public String getDatasourceName() { return datasourceName; }

    public void setDatasourceName(String datasourceName) { this.datasourceName = datasourceName; }

    public Long getTableId() { return tableId; }

    public void setTableId(Long tableId) { this.tableId = tableId;  }

    public String getTableName() { return tableName;  }

    public void setTableName(String tableName) { this.tableName = tableName; }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getOpUserid() {
        return opUserid;
    }

    public void setOpUserid(String opUserid) {
        this.opUserid = opUserid == null ? null : opUserid.trim();
    }

    public String getOpIp() {
        return opIp;
    }

    public void setOpIp(String opIp) {
        this.opIp = opIp == null ? null : opIp.trim();
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getAnalyseSql() {
        return analyseSql;
    }

    public void setAnalyseSql(String analyseSql) {
        this.analyseSql = analyseSql == null ? null : analyseSql.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logId=").append(logId);
        sb.append(", opUserid=").append(opUserid);
        sb.append(", opIp=").append(opIp);
        sb.append(", logTime=").append(logTime);
        sb.append(", analyseSql=").append(analyseSql);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPersonalSubjectId() {
        return personalSubjectId;
    }

    public void setPersonalSubjectId(Long personalSubjectId) {
        this.personalSubjectId = personalSubjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }

    public Long getSqlExecutionTime() {
        return sqlExecutionTime;
    }

    public void setSqlExecutionTime(Long sqlExecutionTime) {
        this.sqlExecutionTime = sqlExecutionTime;
    }
}