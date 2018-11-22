package com.ebase.report.model.jurisdiction;

import com.ebase.report.common.IsDelete;
import com.ebase.report.common.Status;

import java.util.Date;
import java.util.List;

public class RoleInfo {

    private int pageNum = 1;

    private int pageSize = 10;

    private int startRow;

    private Long roleId;            //角色ID。

    private Long appId;             //应用标识

    private Long entId;             //企业标识

    private String roleCode;        //角色编码

    private String roleTitle;       //角色名称

    private Byte roleState;         //角色状态

    private String roleDesc;        //角色描述

    private IsDelete isDelete;          //是否删除

    private Status status;            //0:停用;1:启用

    private String createdBy;       //创建人

    private Date createdTime;       //创建时间

    private String updatedBy;       //修改人

    private Date updatedTime;       //修改时间

    private Byte roleType;          //角色类型

    private Date startTime;         //有效时间

    private Date endTime;           //失效时间

    private String opt;             //数据类型  (insert,update,delete)

    private String roleTypeTitle;   //角色类型名称

    private String orgId;             //组织id

    private String orgTitle;        //组织名称

    private String quoteOrgId;        //引用组织ID

    private Long roleGroupId;       //角色组id

    private String orRole;          //角色还是角色组   角色树状图

    private String orgIdAll;        //组织ID，可能多个

    private String[] orgIds;        //组织ID，可能多个

    private List<String> orgIdAlls;

    private String roleGroupTitle;

    private String IsStatus;

    private Long ParentApplicationId;

    private String parentApplicationName;

    private String permissions;//zhaotairan

    private String orAcct;      //角色与账户关联状态

    private Long acctId;

    private Long sysId;

    private Byte type;

    private Byte relaStatus;

    private Long copyId;


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    private Long[] roleIds;

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public String[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String[] orgIds) {
        this.orgIds = orgIds;
    }

    public Long getCopyId() {
        return copyId;
    }

    public void setCopyId(Long copyId) {
        this.copyId = copyId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getRelaStatus() {
        return relaStatus;
    }

    public void setRelaStatus(Byte relaStatus) {
        this.relaStatus = relaStatus;
    }

    public String getQuoteOrgId() {
        return quoteOrgId;
    }

    public void setQuoteOrgId(String quoteOrgId) {
        this.quoteOrgId = quoteOrgId;
    }

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

    public String getOrAcct() {
        return orAcct;
    }

    public void setOrAcct(String orAcct) {
        this.orAcct = orAcct;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getParentApplicationName() {
        return parentApplicationName;
    }

    public void setParentApplicationName(String parentApplicationName) {
        this.parentApplicationName = parentApplicationName;
    }

    public Long getParentApplicationId() {
        return ParentApplicationId;
    }

    public void setParentApplicationId(Long parentApplicationId) {
        ParentApplicationId = parentApplicationId;
    }

    public String getIsStatus() {
        return IsStatus;
    }

    public void setIsStatus(String isStatus) {
        IsStatus = isStatus;
    }

    public String getRoleGroupTitle() {
        return roleGroupTitle;
    }

    public void setRoleGroupTitle(String roleGroupTitle) {
        this.roleGroupTitle = roleGroupTitle;
    }

    public List<String> getOrgIdAlls() {
        return orgIdAlls;
    }

    public void setOrgIdAlls(List<String> orgIdAlls) {
        this.orgIdAlls = orgIdAlls;
    }

    public String getOrgIdAll() {
        return orgIdAll;
    }

    public void setOrgIdAll(String orgIdAll) {
        this.orgIdAll = orgIdAll;
    }

    public String getOrRole() {
        return orRole;
    }

    public void setOrRole(String orRole) {
        this.orRole = orRole;
    }

    private List<RoleInfo> children;

    public List<RoleInfo> getChildren() {
        return children;
    }

    public void setChildren(List<RoleInfo> children) {
        this.children = children;
    }

    public Long getRoleGroupId() {
        return roleGroupId;
    }

    public void setRoleGroupId(Long roleGroupId) {
        this.roleGroupId = roleGroupId;
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

    public String getRoleTypeTitle() {
        return roleTypeTitle;
    }

    public void setRoleTypeTitle(String roleTypeTitle) {
        this.roleTypeTitle = roleTypeTitle;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public Byte getRoleType() {
        return roleType;
    }

    public void setRoleType(Byte roleType) {
        this.roleType = roleType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getEntId() {
        return entId;
    }

    public void setEntId(Long entId) {
        this.entId = entId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle == null ? null : roleTitle.trim();
    }

    public Byte getRoleState() {
        return roleState;
    }

    public void setRoleState(Byte roleState) {
        this.roleState = roleState;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
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