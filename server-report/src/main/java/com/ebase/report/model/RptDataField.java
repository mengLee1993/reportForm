package com.ebase.report.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 */
public class RptDataField implements Serializable {

    private String datasourceChineseName;   //数据库中文名称
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
     *   数据表名称
     */
    private String tableName;

    /**
     * Database Column Remarks:
     *   数据表编码
     */
    private String tableCode;

    /**
     * Database Column Remarks:
     *   字段编码
     */
    private String fieldCode;

    /**
     * Database Column Remarks:
     *   字段名称
     */
    private String fieldName;

    /**
     * Database Column Remarks:
     *   字段类型
     */
    private String fieldType;

    /**
     * Database Column Remarks:
     *   维度指标
     */
    private String dimensionIndex;

    /**
     * Database Column Remarks:
     *   精度
     */
    private Integer accuracy;

    /**
     * Database Column Remarks:
     *   是否行级授权 1授权，0未授权
     */
    private Byte rowLevelAuth;

    /**
     * Database Column Remarks:
     *   备注
     */
    private String remark;

    /**
     * Database Column Remarks:
     *   元数据数量
     */
    private Integer metadataCount;

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
     * Database Column Remarks:
     *   数据源名称
     */
    private String datasourceName;


    private List<RptDataDict>  rptDataDicts;
    /**
     */
    private static final long serialVersionUID = 1L;


    private Byte isChecked ;  //是否勾选

    private Integer pageSize = 3;

    private Integer pageNum = 1;

    private List<Long> ids = new ArrayList<>(); //用于批量删除

    public List<Long> getIds() { return ids; }

    public void setIds(List<Long> ids) { this.ids = ids; }


    public Integer getPageSize() { return pageSize; }

    public String getDatasourceChineseName() {
        return datasourceChineseName;
    }

    public void setDatasourceChineseName(String datasourceChineseName) {
        this.datasourceChineseName = datasourceChineseName;
    }

    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }

    public Integer getPageNum() { return pageNum; }

    public void setPageNum(Integer pageNum) { this.pageNum = pageNum; }

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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode == null ? null : fieldCode.trim();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType == null ? null : fieldType.trim();
    }

    public String getDimensionIndex() {
        return dimensionIndex;
    }

    public void setDimensionIndex(String dimensionIndex) {
        this.dimensionIndex = dimensionIndex == null ? null : dimensionIndex.trim();
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public Byte getRowLevelAuth() {
        return rowLevelAuth;
    }

    public void setRowLevelAuth(Byte rowLevelAuth) {
        this.rowLevelAuth = rowLevelAuth;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getMetadataCount() {
        return metadataCount;
    }

    public void setMetadataCount(Integer metadataCount) {
        this.metadataCount = metadataCount;
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

    public List<RptDataDict> getRptDataDicts() {
        return rptDataDicts;
    }

    public void setRptDataDicts(List<RptDataDict> rptDataDicts) {
        this.rptDataDicts = rptDataDicts;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fieldId=").append(fieldId);
        sb.append(", tableId=").append(tableId);
        sb.append(", fieldCode=").append(fieldCode);
        sb.append(", fieldName=").append(fieldName);
        sb.append(", fieldType=").append(fieldType);
        sb.append(", dimensionIndex=").append(dimensionIndex);
        sb.append(", accuracy=").append(accuracy);
        sb.append(", rowLevelAuth=").append(rowLevelAuth);
        sb.append(", remark=").append(remark);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdDt=").append(createdDt);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedDt=").append(updatedDt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public Byte getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Byte isChecked) {
        this.isChecked = isChecked;
    }
}