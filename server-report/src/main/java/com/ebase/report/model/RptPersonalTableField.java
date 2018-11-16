package com.ebase.report.model;

import java.io.Serializable;
import java.util.Date;

/**
 */
public class RptPersonalTableField implements Serializable {
    /**
     * Database Column Remarks:
     *   个人数据表字段ID
     */
    private Long persionalTableFieldId;

    /**
     * Database Column Remarks:
     *   数据表ID
     */
    private Long tableId;

    /**
     * Database Column Remarks:
     *   字段ID
     */
    private Long fieldId;

    /**
     * Database Column Remarks:
     *   字段名称
     */
    private String fieldName;

    /**
     * Database Column Remarks:
     *   所属人ID
     */
    private String userId;

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

    public Long getPersionalTableFieldId() {
        return persionalTableFieldId;
    }

    public void setPersionalTableFieldId(Long persionalTableFieldId) {
        this.persionalTableFieldId = persionalTableFieldId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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
        sb.append(", persionalTableFieldId=").append(persionalTableFieldId);
        sb.append(", tableId=").append(tableId);
        sb.append(", fieldId=").append(fieldId);
        sb.append(", fieldName=").append(fieldName);
        sb.append(", userId=").append(userId);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdDt=").append(createdDt);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedDt=").append(updatedDt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}