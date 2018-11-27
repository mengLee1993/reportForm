package com.ebase.report.model;

import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.core.utils.StringUtil;
import com.ebase.report.model.dynamic.ReportMeasure;
import com.ebase.report.model.dynamic.RptFieldIndex;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 */
public class RptMeasures implements Serializable {
    /**
     * Database Column Remarks:
     *   指标ID
     */
    private Long measureId;

    /**
     * 指标名称1
     */
    private String measuresName;

    /**
     * Database Column Remarks:
     *   指标字段ID
     */
    private Long fieldId;

    /**
     *  指标code
     */
    private String fieldCode;

    /**
     * 指标名称
     */
    private String fieldName;

    /**
     * Database Column Remarks:
     *   指标类型   /系统/自定义    自定义/    SUM/COUNT/AVG/MIN/MAX
     */
    private String measureType;

    /**
     * Database Column Remarks:
     *   主题ID
     */
    private Long subjectId;

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
     * Database Column Remarks:
     *   指标表达式
     */
    private String measureRule;

    //添加组合名称
    private String combinationName;


//    private RptMeasuresBody rptMeasuresBody;

    private ReportMeasure reportMeasure;


    //  给前端用 永远是 1  0 否  1是
    private Byte isChecked = 1;


    private Byte isSystem = 0;  //1 的时候就是系统生成的

    //初始化指标
//    private List<RptFieldIndex> rptFieldIndexList;

    //删除指标时使用
    private Long personalAnalysisId; //报表id

    /**
     */
    private static final long serialVersionUID = 1L;

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getMeasureType() {
        return measureType == null ? measureType : measureType.toUpperCase();
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType == null ? null : measureType.trim();
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(measureType)){
            measureType = measureType.toUpperCase();
        }
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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

    public String getMeasureRule() {
        return measureRule;
    }

    public void setMeasureRule(String measureRule) {
        this.measureRule = measureRule == null ? null : measureRule.trim();
        if(!StringUtils.isEmpty(measureRule)){
            reportMeasure = JsonUtil.fromJson(measureRule,ReportMeasure.class);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", measureId=").append(measureId);
        sb.append(", fieldId=").append(fieldId);
        sb.append(", measureType=").append(measureType);
        sb.append(", subjectId=").append(subjectId);
        sb.append(", removeStatus=").append(removeStatus);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdDt=").append(createdDt);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedDt=").append(updatedDt);
        sb.append(", measureRule=").append(measureRule);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }


    public ReportMeasure getReportMeasure() {
        return reportMeasure;
    }

    public void setReportMeasure(ReportMeasure reportMeasure) {
        this.reportMeasure = reportMeasure;
        if(reportMeasure != null){
            this.measureRule = JsonUtil.toJson(reportMeasure);
        }
    }

    public String getMeasuresName() {
        return measuresName;
    }

    public void setMeasuresName(String measuresName) {
        this.measuresName = measuresName;
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(measuresName)){
            fieldName = measuresName;
        }
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public Byte getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Byte isChecked) {
        this.isChecked = isChecked;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(fieldName)){
            measuresName = fieldName;
        }
    }

    public Byte getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Byte isSystem) {
        this.isSystem = isSystem;
    }

    public Long getPersonalAnalysisId() {
        return personalAnalysisId;
    }

    public void setPersonalAnalysisId(Long personalAnalysisId) {
        this.personalAnalysisId = personalAnalysisId;
    }

    public String getCombinationName() {
        return StringUtil.assemblingView(fieldCode, fieldName);
    }

    public void setCombinationName(String combinationName) {
        this.combinationName = combinationName;
    }
}