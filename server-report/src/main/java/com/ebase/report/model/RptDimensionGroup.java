package com.ebase.report.model;

import java.io.Serializable;

/**
 */
public class RptDimensionGroup implements Serializable {
    /**
     * Database Column Remarks:
     *   维度组ID
     */
    private Long dimensionGroupId;

    /**
     * Database Column Remarks:
     *   维度ID
     */
    private Long dimensionId;

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
     *   维度名称
     */
    private String dimensionName;

    /**
     * Database Column Remarks:
     *   排序值
     */
    private Integer seq;

    /**
     */
    private static final long serialVersionUID = 1L;

    public Long getDimensionGroupId() {
        return dimensionGroupId;
    }

    public void setDimensionGroupId(Long dimensionGroupId) {
        this.dimensionGroupId = dimensionGroupId;
    }

    public Long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(Long dimensionId) {
        this.dimensionId = dimensionId;
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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dimensionGroupId=").append(dimensionGroupId);
        sb.append(", dimensionId=").append(dimensionId);
        sb.append(", tableId=").append(tableId);
        sb.append(", fieldId=").append(fieldId);
        sb.append(", dimensionName=").append(dimensionName);
        sb.append(", seq=").append(seq);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}