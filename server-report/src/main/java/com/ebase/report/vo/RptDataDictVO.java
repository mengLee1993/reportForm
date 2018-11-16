package com.ebase.report.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 */
public class RptDataDictVO implements Serializable {
    /**
     * Database Column Remarks:
     *   数据字典ID
     */
    private Long dataDictId;

    /**
     * Database Column Remarks:
     *   字段ID
     */
    private Long fieldId;

    /**
     * Database Column Remarks:
     *   数据表ID
     */
    private Long tableId;

    /**
     * Database Column Remarks:
     *   数据值
     */
    private String fieldValue;

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
     */
    private static final long serialVersionUID = 1L;

    private int pageSize = 10 ;

    private int pageNum ;

    public Integer getPageSize() { return pageSize; }

    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public Integer getPageNum() { return pageNum; }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Long getDataDictId() {
        return dataDictId;
    }

    public void setDataDictId(Long dataDictId) {
        this.dataDictId = dataDictId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue == null ? null : fieldValue.trim();
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
        sb.append(", dataDictId=").append(dataDictId);
        sb.append(", fieldId=").append(fieldId);
        sb.append(", tableId=").append(tableId);
        sb.append(", fieldValue=").append(fieldValue);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdDt=").append(createdDt);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedDt=").append(updatedDt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}