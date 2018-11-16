package com.ebase.report.model.jurisdiction;

import java.util.Date;

public class AcctRoleReal {
    private Long relaId;     //关系标识

    private Long acctId;     //账户标识

    private Long roleId;     //角色标识

    private Byte status;       //关系状态

    private String createdBy;   //创建人

    private Date createdTime;   //创建时间

    private String updatedBy;     // 修改人

    private Date updatedTime;       //修改时间

    private String existence;

    private RoleInfo  role; //中间-》角色



    public String getExistence() {
        return existence;
    }

    public void setExistence(String existence) {
        this.existence = existence;
    }

    public Long getRelaId() {
        return relaId;
    }

    public void setRelaId(Long relaId) {
        this.relaId = relaId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}