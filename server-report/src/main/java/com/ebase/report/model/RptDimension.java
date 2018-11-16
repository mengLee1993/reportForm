package com.ebase.report.model;

import java.io.Serializable;
import java.util.Date;

/**
 */
public class RptDimension implements Serializable {
    /**
     * Database Column Remarks:
     *   维度ID
     */
    private Long dimensionId;

    /**
     * Database Column Remarks:
     *   主题ID
     */
    private Long subjectId;

    /**
     * Database Column Remarks:
     *   数据表ID
     */
    private Long tableId;

    /**
     * Database Column Remarks:
     *   字段表ID
     */
    private Long fieldId;

    /**
     * Database Column Remarks:
     *   维度名称
     */
    private String dimensionName;

    /**
     * Database Column Remarks:
     *   是否维度组
     */
    private Byte dimensionGroup;

    /**
     */
    private String createdBy;

    /**
     */
    private Date createdDt;

    /**
     */
    private String updatedBy;

    /**
     */
    private Date updatedDt;

    /**
     */
    private static final long serialVersionUID = 1L;

    public Long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(Long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName == null ? null : dimensionName.trim();
    }

    public Byte getDimensionGroup() {
        return dimensionGroup;
    }

    public void setDimensionGroup(Byte dimensionGroup) {
        this.dimensionGroup = dimensionGroup;
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
        sb.append(", dimensionId=").append(dimensionId);
        sb.append(", subjectId=").append(subjectId);
        sb.append(", tableId=").append(tableId);
        sb.append(", fieldId=").append(fieldId);
        sb.append(", dimensionName=").append(dimensionName);
        sb.append(", dimensionGroup=").append(dimensionGroup);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdDt=").append(createdDt);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedDt=").append(updatedDt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}