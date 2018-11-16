package com.ebase.report.model.jurisdiction;

import com.ebase.report.common.IsDelete;
import com.ebase.report.common.Status;

import java.util.Date;
import java.util.List;

public class FunctionManage {
    private Long functionId;                //功能Id

    private String functionCode;            //功能编码

    private String functionTitle;           //功能名称

    private String functionDesc;            //功能描述

    private String idFullPath;              //标识全路径

    private String titleFullPath;           //名称全路径

    private Byte isLastLevel;               //是否末级

    private Long parentApplicationId;       //父级Id

    private Status status;                    //状态 0停用 1使用

    private String createdBy;               //创建人

    private Date createdTime;               //创建时间

    private String updatedBy;               //修改人

    private Date updatedTime;               //修改时间

    private String parentApplicationName;   //父级名称

    private String functionType;            //功能类型

    private List<String> allIds;            //功能集合Id

    private Long roleId;                    //角色ID

    private String functionRoleStatus;      //角色功能关联，角色是否使用

    private String opt;                     //数据状态

    private String orgId;

    private IsDelete isDelete;

    private String orgName;                 //机构名称

    private String functionPath;

    private String orgIdAll;                //机构idall

    private List<String> orgIdAlls;         //机构idall

    private Long acctType;                  //用户类型  0 超级管理员  1 管理员  2 用户

    private Long acctId;                    //用户ID

    private Long sysId;                    //系统ID

    private Integer relaStatus;             //1是不可编辑    0是可以编辑

    private Integer type;                   //1是引用  0是创建

    public Integer getRelaStatus() {
        return relaStatus;
    }

    public void setRelaStatus(Integer relaStatus) {
        this.relaStatus = relaStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public Long getAcctType() {
        return acctType;
    }

    public void setAcctType(Long acctType) {
        this.acctType = acctType;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgIdAll() {
        return orgIdAll;
    }

    public void setOrgIdAll(String orgIdAll) {
        this.orgIdAll = orgIdAll;
    }

    public List<String> getOrgIdAlls() {
        return orgIdAlls;
    }

    public void setOrgIdAlls(List<String> orgIdAlls) {
        this.orgIdAlls = orgIdAlls;
    }

    public String getFunctionPath() {
        return functionPath;
    }

    public void setFunctionPath(String functionPath) {
        this.functionPath = functionPath;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIsDelete() {
        if (isDelete != null)
            return isDelete.getCode();
        return "";
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = IsDelete.getIsDelete(isDelete);
    }

    public void setIsDelete(IsDelete isDelete) {
        this.isDelete = isDelete;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getFunctionRoleStatus() {
        return functionRoleStatus;
    }

    public void setFunctionRoleStatus(String functionRoleStatus) {
        this.functionRoleStatus = functionRoleStatus;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<String> getAllIds() {
        return allIds;
    }

    public void setAllIds(List<String> allIds) {
        this.allIds = allIds;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public String getParentApplicationName() {
        return parentApplicationName;
    }

    public void setParentApplicationName(String parentApplicationName) {
        this.parentApplicationName = parentApplicationName;
    }

    private List<FunctionManage> children;

    public List<FunctionManage> getChildren() {
        return children;
    }

    public void setChildren(List<FunctionManage> children) {
        this.children = children;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode == null ? null : functionCode.trim();
    }

    public String getFunctionTitle() {
        return functionTitle;
    }

    public void setFunctionTitle(String functionTitle) {
        this.functionTitle = functionTitle == null ? null : functionTitle.trim();
    }

    public String getFunctionDesc() {
        return functionDesc;
    }

    public void setFunctionDesc(String functionDesc) {
        this.functionDesc = functionDesc == null ? null : functionDesc.trim();
    }

    public String getIdFullPath() {
        return idFullPath;
    }

    public void setIdFullPath(String idFullPath) {
        this.idFullPath = idFullPath == null ? null : idFullPath.trim();
    }

    public String getTitleFullPath() {
        return titleFullPath;
    }

    public void setTitleFullPath(String titleFullPath) {
        this.titleFullPath = titleFullPath == null ? null : titleFullPath.trim();
    }

    public Byte getIsLastLevel() {
        return isLastLevel;
    }

    public void setIsLastLevel(Byte isLastLevel) {
        this.isLastLevel = isLastLevel;
    }

    public Long getParentApplicationId() {
        return parentApplicationId;
    }

    public void setParentApplicationId(Long parentApplicationId) {
        this.parentApplicationId = parentApplicationId;
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