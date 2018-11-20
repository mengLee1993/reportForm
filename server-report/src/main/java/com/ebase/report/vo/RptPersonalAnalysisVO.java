package com.ebase.report.vo;

import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.model.dynamic.ReportDatasource;

import java.io.Serializable;
import java.util.Date;

/**
 */
public class RptPersonalAnalysisVO implements Serializable {
    /**
     * Database Column Remarks:
     *   自定义分析报表ID
     */
    private Long personalAnalysisId;

    /**
     * Database Column Remarks:
     *   个人主题ID
     */
    private Long personalSubjectId;

    /**
     * Database Column Remarks:
     *   报表名称
     */
    private String reportName;

    /**
     * Database Column Remarks:
     *   用户ID
     */
    private String userId;

    /**
     * Database Column Remarks:
     *   创建人
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
     * Database Column Remarks:
     *   配置JOSN
     */
    private String configJson;


    private ReportDatasource reportDatasource;

    /**
     * 主题名称
     */
    private String subjectName;

    /**
     * 数据表 名称
     */
    private String tableName;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 分享者ID
     */
    private Long sharingPersonId;

    /**
     * 分享者名称
     */
    private String sharingPersonName;


    private Byte deleteType;  // 0 删除  1  取消分享

    /**
     */
    private static final long serialVersionUID = 1L;

    private int pageNum = 1;

    private int pageSize = 10;

    public int getPageNum() { return pageNum; }

    public void setPageNum(int pageNum) { this.pageNum = pageNum; }

    public int getPageSize() { return pageSize; }

    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public Long getPersonalAnalysisId() {
        return personalAnalysisId;
    }

    public void setPersonalAnalysisId(Long personalAnalysisId) {
        this.personalAnalysisId = personalAnalysisId;
    }

    public Long getPersonalSubjectId() {
        return personalSubjectId;
    }

    public void setPersonalSubjectId(Long personalSubjectId) {
        this.personalSubjectId = personalSubjectId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName == null ? null : reportName.trim();
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

    public String getConfigJson() {
        return configJson;
    }

    public void setConfigJson(String configJson) {
        this.configJson = configJson == null ? null : configJson.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", personalAnalysisId=").append(personalAnalysisId);
        sb.append(", personalSubjectId=").append(personalSubjectId);
        sb.append(", reportName=").append(reportName);
        sb.append(", userId=").append(userId);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdDt=").append(createdDt);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedDt=").append(updatedDt);
        sb.append(", configJson=").append(configJson);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public ReportDatasource getReportDatasource() {
        return reportDatasource;
    }

    public void setReportDatasource(ReportDatasource reportDatasource) {
        this.reportDatasource = reportDatasource;
        if(reportDatasource != null){
            this.configJson = JsonUtil.toJson(reportDatasource);
        }
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getSharingPersonId() {
        return sharingPersonId;
    }

    public void setSharingPersonId(Long sharingPersonId) {
        this.sharingPersonId = sharingPersonId;
    }

    public String getSharingPersonName() {
        return sharingPersonName;
    }

    public void setSharingPersonName(String sharingPersonName) {
        this.sharingPersonName = sharingPersonName;
    }

    public Byte getDeleteType() {
        return deleteType;
    }

    public void setDeleteType(Byte deleteType) {
        this.deleteType = deleteType;
    }
}