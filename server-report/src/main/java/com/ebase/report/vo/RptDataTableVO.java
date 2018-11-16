package com.ebase.report.vo;

import com.ebase.report.common.RemoveStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class RptDataTableVO  implements Serializable {

    private  String datasourceChineseName; //数据库中文名称

    /**
     * Database Column Remarks:
     *   数据表ID
     */
    private Long tableId;

    /**
     * Database Column Remarks:
     *   数据源ID
     */
    private Long datasourceId;

    /**
     * Database Column Remarks:
     *   数据表名称
     */
    private String tableName;

    /**
     * Database Column Remarks:
     *   数据表CODE
     */
    private String tableCode;

    /**
     * Database Column Remarks:
     *   备注
     */
    private String demo;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDt;

    /**
     * Database Column Remarks:
     *   数据源名称
     */
    private String datasourceName;

    /**
     * Database Column Remarks:
     *   主题表添加情况
     */
    private String status = "0";

    /**
     */
    private static final long serialVersionUID = 1L;


    private int pageSize = 10 ;

    private int pageNum = 1;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pagNum) {
        this.pageNum = pagNum;
    }

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

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(Long datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode == null ? null : tableCode.trim();
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo == null ? null : demo.trim();
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

    public String getDatasourceName() {
        return datasourceName;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tableId=").append(tableId);
        sb.append(", datasourceId=").append(datasourceId);
        sb.append(", tableName=").append(tableName);
        sb.append(", tableCode=").append(tableCode);
        sb.append(", demo=").append(demo);
        sb.append(", removeStatus=").append(removeStatus);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdDt=").append(createdDt);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedDt=").append(updatedDt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}
