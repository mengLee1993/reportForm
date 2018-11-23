package com.ebase.report.vo.jurisdiction;

import javax.management.relation.RoleInfo;
import java.util.Date;
import java.util.List;

/**
 * @Auther: zhaoyuhang
 */
public class RoleInfoVO {

    private int startRow;

    private String reRoleId;

    private Long roleId;            //角色ID

    private Long appId;             //应用标识

    private Long entId;             //企业标识

    private String roleCode;        //角色编码

    private String roleTitle;       //角色名称

    private Byte roleState;         //角色状态

    private String roleDesc;        //角色描述

    private Byte isDelete;          //是否删除

    private String status;          //0:停用;1:启用

    private String createdBy;       //创建人

    private Date createdTime;       //创建时间

    private String updatedBy;       //修改人

    private Date updatedTime;       //修改时间

    private Byte roleType;          //角色类型

    private Byte type;          //角色类型

    private Date startTime;         //有效时间

    private Date endTime;           //失效时间

    private String opt;             //数据类型  (insert,update,delete)

    private String roleTypeTitle;   //角色类型名称

    private Long roleGroupId;

    private String orgId;

    private String[] orgIds;

    private String quoteOrgId;      //引用组织ID

    private String orgTitle;

    private String orRole;

    private String orgIdAll;

    private String roleGroupTitle;

    private String IsStatus;

    private String orAcct;

    private Long AcctId;

    private Long sysId;

    private Long copyId;

    public String getReRoleId() {
        return reRoleId;
    }

    public void setReRoleId(String reRoleId) {
        this.reRoleId = reRoleId;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    private Long[] roleIds;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

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
        return AcctId;
    }

    public void setAcctId(Long acctId) {
        AcctId = acctId;
    }

    public String getOrAcct() {
        return orAcct;
    }

    public void setOrAcct(String orAcct) {
        this.orAcct = orAcct;
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

    public String getOrgTitle() {
        return orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public List<RoleInfo> getChildren() {
        return children;
    }

    public void setChildren(List<RoleInfo> children) {
        this.children = children;
    }

    private int pageSize =10;

    private int pageNum = 1;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Long getRoleGroupId() {
        return roleGroupId;
    }

    public void setRoleGroupId(Long roleGroupId) {
        this.roleGroupId = roleGroupId;
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
        this.roleCode = roleCode;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
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
        this.roleDesc = roleDesc;
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
