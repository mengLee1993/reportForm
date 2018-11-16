package com.ebase.report.model.jurisdiction;

import java.io.Serializable;
import java.util.Date;

/**
 */
public class AcctOrgSys implements Serializable {
    /**
     * Database Column Remarks:
     *   关联标识
     */
    private Long relaId;

    /**
     * Database Column Remarks:
     *   组织标识
     */
    private String orgId;

    /**
     * Database Column Remarks:
     *   系统标识
     */
    private Long sysId;

    /**
     * Database Column Remarks:
     *   系统组标识
     */
    private Long[] sysIds;

    /**
     * Database Column Remarks:
     *   系统组标识
     */
    private String[] orgIds;

    /**
     * Database Column Remarks:
     *   关系状态
     */
    private Byte status;

    /**
     * Database Column Remarks:
     *   创建人
     */
    private String createdBy;

    /**
     * Database Column Remarks:
     *   创建时间
     */
    private Date createdTime;

    /**
     */
    private static final long serialVersionUID = 1L;

    public String[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String[] orgIds) {
        this.orgIds = orgIds;
    }

    public Long[] getSysIds() {
        return sysIds;
    }

    public void setSysIds(Long[] sysIds) {
        this.sysIds = sysIds;
    }

    public Long getRelaId() {
        return relaId;
    }

    public void setRelaId(Long relaId) {
        this.relaId = relaId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", relaId=").append(relaId);
        sb.append(", orgId=").append(orgId);
        sb.append(", sysId=").append(sysId);
        sb.append(", status=").append(status);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}