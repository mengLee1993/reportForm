package com.ebase.report.vo.jurisdiction;

import java.io.Serializable;
import java.util.Date;

/**
 */
public class AcctRoleOrgVO implements Serializable {
    /**
     * Database Column Remarks:
     *   关联标识
     */
    private Long relaId;

    /**
     * Database Column Remarks:
     *   角色标识
     */
    private String orgId;

    /**
     * Database Column Remarks:
     *   角色标识
     */
    private Long roleId;

    /**
     * Database Column Remarks:
     *   角色标识
     */
    private Long[] roleIds;

    /**
     * Database Column Remarks:
     *   组织标识
     */
    private String[] orgIds;

    /**
     * Database Column Remarks:
     *   关系状态  0创建  1引用
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

    private String opt;

    /**
     */
    private static final long serialVersionUID = 1L;

    public String[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String[] orgIds) {
        this.orgIds = orgIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
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
        sb.append(", roleId=").append(roleId);
        sb.append(", status=").append(status);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}