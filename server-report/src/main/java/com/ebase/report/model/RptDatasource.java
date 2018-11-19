package com.ebase.report.model;

import com.ebase.report.common.RemoveStatusEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 */
public class RptDatasource implements Serializable {

    private String datasourceChineseName;   //数据库中文名称

    /**
     * Database Column Remarks:
     *   数据源ID
     */
    private Long datasourceId;

    /**
     * Database Column Remarks:
     *   数据源名称
     */
    private String datasourceName;

    /**
     * Database Column Remarks:
     *   数据库类型
     */
    private String databaseType;

    /**
     * Database Column Remarks:
     *   连接池类型
     */
    private String connpoolType;

    /**
     * Database Column Remarks:
     *   数据库连接地址
     */
    private String datasourceUrl;

    /**
     * Database Column Remarks:
     *   数据库用户名
     */
    private String userName;

    /**
     * Database Column Remarks:
     *   数据库密码
     */
    private String password;

    /**
     * Database Column Remarks:
     *   连接池初始连接数
     */
    private Integer initialSize = 5;

    /**
     * Database Column Remarks:
     *   连接池最大连接数
     */
    private Integer maxActive = 15;

    /**
     * Database Column Remarks:
     *   超时时间
     */
    private Integer maxWait = 30000;

    /**
     * Database Column Remarks:
     *   保持空闲的最大连接数
     */
    private Integer maxIdle = 5;

    /**
     * Database Column Remarks:
     *   删除状态
     */
    private RemoveStatusEnum removeStatus;     //使用枚举表示移除状态

    /**
     * Database Column Remarks:
     *   创建人
     */
    private String createdBy;

    /**
     * Database Column Remarks:
     *   创建时间
     */
    private Date createdDt;

    /**
     * Database Column Remarks:
     *   修改人
     */
    private String updatedBy;

    /**
     * Database Column Remarks:
     *   修改时间
     */
    private Date updatedDt;



    /**
     */
    private static final long serialVersionUID = 1L;


    /*
        --- --  -- -  数据授权 用
    */
    public List<RptDataTable> rptDataTables;  //树下对所有表

    //------------使用枚举的方式表示移除状态------------------------------------------
    public int getRemoveStatus() {
        if (this.removeStatus == null) {
            return 0;
        }
        return this.removeStatus.getRemoveStatus();
    }
    public void setRemoveStatus(int removeStatus) {
        this.removeStatus= RemoveStatusEnum.getRemoveStatusEnumFromCode(removeStatus);
    }
    //--------------------------------------------------------------------------------


    public String getDatasourceChineseName() {
        return datasourceChineseName;
    }

    public void setDatasourceChineseName(String datasourceChineseName) {
        this.datasourceChineseName = datasourceChineseName;
    }

    public Long getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(Long datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getDatasourceName() {
        return datasourceName;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName == null ? null : datasourceName.trim();
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType == null ? null : databaseType.trim();
    }

    public String getConnpoolType() {
        return connpoolType;
    }

    public void setConnpoolType(String connpoolType) {
        this.connpoolType = connpoolType == null ? null : connpoolType.trim();
    }

    public String getDatasourceUrl() {
        return datasourceUrl;
    }

    public void setDatasourceUrl(String datasourceUrl) {
        this.datasourceUrl = datasourceUrl == null ? null : datasourceUrl.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getInitialSize() {
        return initialSize = initialSize == null ? null : this.initialSize;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }

    public Integer getMaxActive() {
        return maxActive = maxActive == null ? null : this.maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Integer getMaxWait() {
        return maxWait = maxWait == null ? null : this.maxWait;
    }

    public void setMaxWait(Integer maxWait) {
        this.maxWait = maxWait;
    }

    public Integer getMaxIdle() {
        return maxIdle = maxIdle == null ? null : this.maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", datasourceId=").append(datasourceId);
        sb.append(", datasourceName=").append(datasourceName);
        sb.append(", databaseType=").append(databaseType);
        sb.append(", connpoolType=").append(connpoolType);
        sb.append(", datasourceUrl=").append(datasourceUrl);
        sb.append(", userName=").append(userName);
        sb.append(", password=").append(password);
        sb.append(", initialSize=").append(initialSize);
        sb.append(", maxActive=").append(maxActive);
        sb.append(", maxWait=").append(maxWait);
        sb.append(", maxIdle=").append(maxIdle);
        sb.append(", removeStatus=").append(removeStatus);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdDt=").append(createdDt);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedDt=").append(updatedDt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public List<RptDataTable> getRptDataTables() {
        return rptDataTables;
    }

    public void setRptDataTables(List<RptDataTable> rptDataTables) {
        this.rptDataTables = rptDataTables;
    }
}