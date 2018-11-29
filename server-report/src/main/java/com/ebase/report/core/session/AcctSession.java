package com.ebase.report.core.session;

import java.util.Date;
import java.util.List;

/**
 * 管理员session
 * @Auther: wangyu
 */
public class AcctSession {

    private Long acctId;        //账户标识

    private String reAcctId; //

    private Long roleId;    //角色id   // 报表查询弃用

    private List<String> reRoleId; // 一个账号多个角色id   用这个

    private String token;

    private Long entId;         //企业标识

    private String loginSource; //登录来源

    private String acctTitle;     //账户名称

    private String acctPassword;      //账户密码

    private String acctCode;           //账户编码

    private String name;            //姓名

    private String dept;            //部门

    private String mobilePhone;     //手机号

    private String email;           //电子邮件

    private Date registerTime;      //注册时间

    private Date lastLoginTime;     //最后登录时间

    private Byte isDelete;             //是否删除

    private String updatedBy;       //修改人

    private Date updatedTime;       //修改时间

    private Byte status;            //状态

    private Date startTime;            //生效时间

    private Date entTime;           //失效时间

    private Long aCompanyId;

    private String oInfoId;//组织id

    private String orgId;   //组织ID

    private String oInfoName;//组织名字

    private Long acctType;

    private List<String> permissions;

//    private String companyId;   //公司ID


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public Long getEntId() {
        return entId;
    }

    public void setEntId(Long entId) {
        this.entId = entId;
    }

    public String getLoginSource() {
        return loginSource;
    }

    public void setLoginSource(String loginSource) {
        this.loginSource = loginSource;
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

    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEntTime() {
        return entTime;
    }

    public void setEntTime(Date entTime) {
        this.entTime = entTime;
    }

    public Long getaCompanyId() {
        return aCompanyId;
    }

    public void setaCompanyId(Long aCompanyId) {
        this.aCompanyId = aCompanyId;
    }

    public String getoInfoId() {
        return oInfoId;
    }

    public void setoInfoId(String oInfoId) {
        this.oInfoId = oInfoId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getoInfoName() {
        return oInfoName;
    }

    public void setoInfoName(String oInfoName) {
        this.oInfoName = oInfoName;
    }

    public Long getAcctType() {
        return acctType;
    }

    public void setAcctType(Long acctType) {
        this.acctType = acctType;
    }



    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getReAcctId() {
        return reAcctId;
    }

    public void setReAcctId(String reAcctId) {
        this.reAcctId = reAcctId;
    }

    public List<String> getReRoleId() {
        return reRoleId;
    }

    public void setReRoleId(List<String> reRoleId) {
        this.reRoleId = reRoleId;
    }
}
