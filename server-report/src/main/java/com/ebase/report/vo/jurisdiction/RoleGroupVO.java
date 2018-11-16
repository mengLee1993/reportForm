package com.ebase.report.vo.jurisdiction;

import javax.management.relation.RoleInfo;
import java.util.Date;
import java.util.List;

/**
 * @Auther: zhaoyuhang
 */
public class RoleGroupVO {

    private Long roleGroupId;

    private String roleGroupCode;

    private String roleGroupTitle;

    private String roleGroupDesc;

    private Byte isDelete;

    private String status;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private int pageSize =10;

    private int pageNum = 1;

    private String opt;

    private String orRole;

    private List<RoleInfo> children;

    private String orgId;

    private Long roleId;

    private String orgTitle;

    private Long parentApplicationId;       //父级Id

    private String orgIdAll;

    private Long sysId;

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public String getOrgIdAll() {
        return orgIdAll;
    }

    public void setOrgIdAll(String orgIdAll) {
        this.orgIdAll = orgIdAll;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRoleGroupCode() {
        return roleGroupCode;
    }

    public void setRoleGroupCode(String roleGroupCode) {
        this.roleGroupCode = roleGroupCode;
    }

    public List<RoleInfo> getChildren() {
        return children;
    }

    public void setChildren(List<RoleInfo> children) {
        this.children = children;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
        this.roleGroupTitle = roleGroupTitle;
    }

    public String getRoleGroupDesc() {
        return roleGroupDesc;
    }

    public void setRoleGroupDesc(String roleGroupDesc) {
        this.roleGroupDesc = roleGroupDesc;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
