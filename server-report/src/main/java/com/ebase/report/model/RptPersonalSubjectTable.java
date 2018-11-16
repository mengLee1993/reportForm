package com.ebase.report.model;

import java.io.Serializable;
import java.util.Date;

/**
 */
public class RptPersonalSubjectTable implements Serializable {
    /**
     * Database Column Remarks:
     *   个人主题数据表ID
     */
    private Long persionalSubjectTableId;

    /**
     * Database Column Remarks:
     *   个人主题ID
     */
    private Long personalSubjectId;

    /**
     * Database Column Remarks:
     *   数据表ID
     */
    private Long tableId;

    /**
     * Database Column Remarks:
     *   数据表类型
     */
    private String tableType;

    /**
     * Database Column Remarks:
     *   备注
     */
    private String demo;

    /**
     * Database Column Remarks:
     *   删除状态
     */
    private Byte removeStatus;

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

    public Long getPersionalSubjectTableId() {
        return persionalSubjectTableId;
    }

    public void setPersionalSubjectTableId(Long persionalSubjectTableId) {
        this.persionalSubjectTableId = persionalSubjectTableId;
    }

    public Long getPersonalSubjectId() {
        return personalSubjectId;
    }

    public void setPersonalSubjectId(Long personalSubjectId) {
        this.personalSubjectId = personalSubjectId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType == null ? null : tableType.trim();
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo == null ? null : demo.trim();
    }

    public Byte getRemoveStatus() {
        return removeStatus;
    }

    public void setRemoveStatus(Byte removeStatus) {
        this.removeStatus = removeStatus;
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
        sb.append(", persionalSubjectTableId=").append(persionalSubjectTableId);
        sb.append(", personalSubjectId=").append(personalSubjectId);
        sb.append(", tableId=").append(tableId);
        sb.append(", tableType=").append(tableType);
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