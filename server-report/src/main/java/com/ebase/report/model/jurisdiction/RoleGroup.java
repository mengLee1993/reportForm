package com.ebase.report.model.jurisdiction;

import com.ebase.report.common.IsDelete;
import com.ebase.report.common.Status;

import java.util.Date;
import java.util.List;

public class RoleGroup {
    private Long roleGroupId;

    private String roleGroupCode;

    private String roleGroupTitle;

    private String roleGroupDesc;

    private IsDelete isDelete;          //是否删除

    private Status status;            //0:停用;1:启用

    private String idFullPath;              //标识全路径

    private String titleFullPath;           //名称全路径

    private Long parentApplicationId;       //父级Id

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private String opt;

    private String orRole;

    private List<RoleInfo> children;

    private Long roleId;

    private List<String> orgIdAlls;         //组织ID

    private String orgId;

    private String orgIdAll;

    private String orgTitle;

    private Long acctId;

    private Long sysId;

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public String getIdFullPath() {
        return idFullPath;
    }

    public void setIdFullPath(String idFullPath) {
        this.idFullPath = idFullPath;
    }

    public String getTitleFullPath() {
        return titleFullPath;
    }

    public void setTitleFullPath(String titleFullPath) {
        this.titleFullPath = titleFullPath;
    }

    public Long getParentApplicationId() {
        return parentApplicationId;
    }

    public void setParentApplicationId(Long parentApplicationId) {
        this.parentApplicationId = parentApplicationId;
    }

    public String getOrgTitle() {
        return orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public String getOrgIdAll() {
        return orgIdAll;
    }

    public void setOrgIdAll(String orgIdAll) {
        this.orgIdAll = orgIdAll;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<String> getOrgIdAlls() {
        return orgIdAlls;
    }

    public void setOrgIdAlls(List<String> orgIdAlls) {
        this.orgIdAlls = orgIdAlls;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<RoleInfo> getChildren() {
        return children;
    }

    public void setChildren(List<RoleInfo> children) {
        this.children = children;
    }

    public String getOrRole() {
        return orRole;
    }

    public void setOrRole(String orRole) {
        this.orRole = orRole;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getRoleGroupCode() {
        return roleGroupCode;
    }

    public void setRoleGroupCode(String roleGroupCode) {
        this.roleGroupCode = roleGroupCode;
    }

    public Long getRoleGroupId() {
        return roleGroupId;
    }

    public void setRoleGroupId(Long roleGroupId) {
        this.roleGroupId = roleGroupId;
    }

    public String getRoleGroupTitle() {
        return roleGroupTitle;
    }

    public void setRoleGroupTitle(String roleGroupTitle) {
        this.roleGroupTitle = roleGroupTitle == null ? null : roleGroupTitle.trim();
    }

    public String getRoleGroupDesc() {
        return roleGroupDesc;
    }

    public void setRoleGroupDesc(String roleGroupDesc) {
        this.roleGroupDesc = roleGroupDesc == null ? null : roleGroupDesc.trim();
    }

    public String getIsDelete() {
        if (isDelete != null)
            return isDelete.getCode();
        return "";
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = IsDelete.getIsDelete(isDelete);
    }

    public String getStatus() {
        if (status != null)
            return status.getCode();
        return "";
    }

    public void setStatus(String status) {
        this.status = Status.getStatus(status);
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