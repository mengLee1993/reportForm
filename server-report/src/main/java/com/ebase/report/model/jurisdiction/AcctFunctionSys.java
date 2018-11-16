package com.ebase.report.model.jurisdiction;

import java.io.Serializable;
import java.util.Date;

/**
 */
public class AcctFunctionSys implements Serializable {
    /**
     * Database Column Remarks:
     *   关联标识
     */
    private Long relaId;

    /**
     * Database Column Remarks:
     *   角色标识
     */
    private Long functionId;

    /**
     * Database Column Remarks:
     *   角色组标识
     */
    private Long sysId;

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

    public Long getRelaId() {
        return relaId;
    }

    public void setRelaId(Long relaId) {
        this.relaId = relaId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
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
        sb.append(", functionId=").append(functionId);
        sb.append(", sysId=").append(sysId);
        sb.append(", status=").append(status);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}