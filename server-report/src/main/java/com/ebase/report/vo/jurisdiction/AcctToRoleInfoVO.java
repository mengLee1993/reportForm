package com.ebase.report.vo.jurisdiction;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class AcctToRoleInfoVO {

    //id
    private Long acctId;

    private Long relaId;

    private Byte status;

    private String createdBy;

    private Date createdTime;

    private Long sysId;






    //账号名称 描述
    private String acctTitle;

    //账号密码
    private String acctPassword;

    //新密码
    private String newAcctPassword;






    //注册时间
    @JsonFormat(timezone = "yyyy-MM-dd")
    private Date registerTime;



    //最后登录时间
    @JsonFormat(timezone = "yyyy-MM-dd")
    private Date lastLoginTime;



    //修改人
    private String updatedBy;

    //修改时间
    @JsonFormat(timezone = "yyyy-MM-dd")
    private Date updatedTime;

    //生效时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date startTime;
    private String startTimeView;

    //失效时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date entTime;

    private String entTimeView;

    private String opt; //保存还是 添加

    private int pageSize =10;

    private int pageNum = 1;

    private int  Existence;//状态 0：存在角色  1：不存在角色

    private Long roleId;//角色ID



    private String RoleTitle;//角色名称

    private long rInfoID;//组织id

    private String rInfoName;//组织名字


    private String email;

    private String mobilePhone;

    private String name;

    private String oInfoId;

    private String oInfoName;

    private List<Long> roleIds;

    private Long acctType;

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

    public AcctToRoleInfoVO() {
    }


    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public String getAcctTitle() {
        return acctTitle;
    }

    public void setAcctTitle(String acctTitle) {
        this.acctTitle = acctTitle;
    }

    public String getAcctPassword() {
        return acctPassword;
    }

    public void setAcctPassword(String acctPassword) {
        this.acctPassword = acctPassword;
    }

    public String getNewAcctPassword() {
        return newAcctPassword;
    }

    public void setNewAcctPassword(String newAcctPassword) {
        this.newAcctPassword = newAcctPassword;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeView() {
        return startTimeView;
    }

    public void setStartTimeView(String startTimeView) {
        this.startTimeView = startTimeView;
    }

    public Date getEntTime() {
        return entTime;
    }

    public void setEntTime(Date entTime) {
        this.entTime = entTime;
    }

    public String getEntTimeView() {
        return entTimeView;
    }

    public void setEntTimeView(String entTimeView) {
        this.entTimeView = entTimeView;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
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

    public int getExistence() {
        return Existence;
    }

    public void setExistence(int existence) {
        Existence = existence;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleTitle() {
        return RoleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        RoleTitle = roleTitle;
    }

    public long getrInfoID() {
        return rInfoID;
    }

    public void setrInfoID(long rInfoID) {
        this.rInfoID = rInfoID;
    }

    public String getrInfoName() {
        return rInfoName;
    }

    public void setrInfoName(String rInfoName) {
        this.rInfoName = rInfoName;
    }


    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public Long getRelaId() {
        return relaId;
    }

    public void setRelaId(Long relaId) {
        this.relaId = relaId;
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
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getoInfoName() {
        return oInfoName;
    }

    public void setoInfoName(String oInfoName) {
        this.oInfoName = oInfoName;
    }

    public String getoInfoId() {
        return oInfoId;
    }

    public void setoInfoId(String oInfoId) {
        this.oInfoId = oInfoId;
    }
}
