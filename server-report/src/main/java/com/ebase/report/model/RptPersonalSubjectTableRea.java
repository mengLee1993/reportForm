package com.ebase.report.model;

import java.io.Serializable;

/**
 */
public class RptPersonalSubjectTableRea implements Serializable {
    /**
     * Database Column Remarks:
     *   个人主题表关联ID
     */
    private Long reaId;

    /**
     * Database Column Remarks:
     *   个人主题ID
     */
    private Long personalSubjectId;

    /**
     * Database Column Remarks:
     *   主表ID
     */
    private Long masterTableId;

    /**
     * Database Column Remarks:
     *   主表字段ID
     */
    private Long masterFieldId;

    /**
     * Database Column Remarks:
     *   关联表ID
     */
    private Long lookupTableId;

    /**
     * Database Column Remarks:
     *   关联字段ID
     */
    private Long lookupFieldId;

    /**
     * Database Column Remarks:
     *   连接类型
     */
    private String joinType;

    /**
     */
    private static final long serialVersionUID = 1L;

    public Long getReaId() {
        return reaId;
    }

    public void setReaId(Long reaId) {
        this.reaId = reaId;
    }

    public Long getPersonalSubjectId() {
        return personalSubjectId;
    }

    public void setPersonalSubjectId(Long personalSubjectId) {
        this.personalSubjectId = personalSubjectId;
    }

    public Long getMasterTableId() {
        return masterTableId;
    }

    public void setMasterTableId(Long masterTableId) {
        this.masterTableId = masterTableId;
    }

    public Long getMasterFieldId() {
        return masterFieldId;
    }

    public void setMasterFieldId(Long masterFieldId) {
        this.masterFieldId = masterFieldId;
    }

    public Long getLookupTableId() {
        return lookupTableId;
    }

    public void setLookupTableId(Long lookupTableId) {
        this.lookupTableId = lookupTableId;
    }

    public Long getLookupFieldId() {
        return lookupFieldId;
    }

    public void setLookupFieldId(Long lookupFieldId) {
        this.lookupFieldId = lookupFieldId;
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType == null ? null : joinType.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", reaId=").append(reaId);
        sb.append(", personalSubjectId=").append(personalSubjectId);
        sb.append(", masterTableId=").append(masterTableId);
        sb.append(", masterFieldId=").append(masterFieldId);
        sb.append(", lookupTableId=").append(lookupTableId);
        sb.append(", lookupFieldId=").append(lookupFieldId);
        sb.append(", joinType=").append(joinType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}