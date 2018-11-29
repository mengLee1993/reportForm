package com.ebase.report.model;

import java.io.Serializable;
import java.util.Date;

/**
 */
public class RptPersonalSubject implements Serializable {
    /**RPT_PERSONAL_SUBJECT
     * Database Column Remarks:
     *   个人主题ID
     */
    private Long personalSubjectId;

    /**
     * Database Column Remarks:
     *   主题类型
     */
    private String subjectType;

    /**
     * Database Column Remarks:
     *   主题来源
     */
    private String subjectSource;

    /**
     * Database Column Remarks:
     *   主题来源ID
     */
    private Long subjectSourceId;

    /**
     * Database Column Remarks:
     *   数据表ID
     */
    private Long tableId;

    /**
     * Database Column Remarks:
     *   主题名称
     */
    private String subjectName;

    /**
     * Database Column Remarks:
     *   所属人ID
     */
    private String userId;

    /**
     * Database Column Remarks:
     *   数据模型
     */
    private String dataModel;

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
     * 角色id
     */
    private String roleId;

    /**
     */
    private static final long serialVersionUID = 1L;

    //////
    private Long datasourceId;

    public Long getPersonalSubjectId() {
        return personalSubjectId;
    }

    public void setPersonalSubjectId(Long personalSubjectId) {
        this.personalSubjectId = personalSubjectId;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType == null ? null : subjectType.trim();
    }

    public String getSubjectSource() {
        return subjectSource;
    }

    public void setSubjectSource(String subjectSource) {
        this.subjectSource = subjectSource == null ? null : subjectSource.trim();
    }

    public Long getSubjectSourceId() {
        return subjectSourceId;
    }

    public void setSubjectSourceId(Long subjectSourceId) {
        this.subjectSourceId = subjectSourceId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getDataModel() {
        return dataModel;
    }

    public void setDataModel(String dataModel) {
        this.dataModel = dataModel == null ? null : dataModel.trim();
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
        sb.append(", personalSubjectId=").append(personalSubjectId);
        sb.append(", subjectType=").append(subjectType);
        sb.append(", subjectSource=").append(subjectSource);
        sb.append(", subjectSourceId=").append(subjectSourceId);
        sb.append(", tableId=").append(tableId);
        sb.append(", subjectName=").append(subjectName);
        sb.append(", userId=").append(userId);
        sb.append(", dataModel=").append(dataModel);
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Long getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(Long datasourceId) {
        this.datasourceId = datasourceId;
    }
}