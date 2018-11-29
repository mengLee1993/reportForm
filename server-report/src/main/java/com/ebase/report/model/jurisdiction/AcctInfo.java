package com.ebase.report.model.jurisdiction;

import com.ebase.report.common.IsDelete;
import com.ebase.report.common.Status;
import com.ebase.report.vo.jurisdiction.OrgInfoVO;

import java.util.Date;
import java.util.List;

public class AcctInfo {
    private Long acctId;        //账户标识

    private String reAcctId;

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

    private String existence; //  增 删 改 状态

    private String orgId;  //组织类型

    private String orgTitle;

    private Long aCompanyId;

    private String companyId;   //公司ID

    private Long roleId;//角色ID   !!

    private String reRoleId;


    private String RoleTitle;//角色名称

    private String oInfoId;//组织id
    private String oInfoName;//组织名字

    private List<RoleInfo> roleArr;//角色集合

    private OrgInfo orgInfo =new OrgInfo();//组织集合

    private String orgName;

    //一对多 关联角色
    private List<RoleInfo> roleInfo;

    //一对多 中间明细
    private List<AcctRoleReal> arr;

    private  List<OrgInfoVO> OrgArr;

    private Long acctType;          // 0超级管理员  1组织管理员  2 用户  3系统管理员

    private Long purchaseType;      //1执行采购员2寻源采购员

    private Long sysId;

    private int pageSize =10;
    //
    private int pageNum = 1;

    /**
     * 起始行
     */
    private int startRow;

    private String sysTitle;

    private Byte type;


    public String getReAcctId() {
        return reAcctId;
    }

    public void setReAcctId(String reAcctId) {
        this.reAcctId = reAcctId;
    }

    public String getSysTitle() {
        return sysTitle;
    }

    public void setSysTitle(String sysTitle) {
        this.sysTitle = sysTitle;
    }

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public String getExistence() {
        return existence;
    }

    public void setExistence(String existence) {
        this.existence = existence;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgTitle() {
        return orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public Long getaCompanyId() {
        return aCompanyId;
    }

    public void setaCompanyId(Long aCompanyId) {
        this.aCompanyId = aCompanyId;
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

    public String getoInfoId() {
        return oInfoId;
    }

    public void setoInfoId(String oInfoId) {
        this.oInfoId = oInfoId;
    }

    public String getoInfoName() {
        return oInfoName;
    }

    public void setoInfoName(String oInfoName) {
        this.oInfoName = oInfoName;
    }

    public List<RoleInfo> getRoleArr() {
        return roleArr;
    }

    public void setRoleArr(List<RoleInfo> roleArr) {
        this.roleArr = roleArr;
    }

    public OrgInfo getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(OrgInfo orgInfo) {
        this.orgInfo = orgInfo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<RoleInfo> getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(List<RoleInfo> roleInfo) {
        this.roleInfo = roleInfo;
    }

    public List<AcctRoleReal> getArr() {
        return arr;
    }

    public void setArr(List<AcctRoleReal> arr) {
        this.arr = arr;
    }

    public List<OrgInfoVO> getOrgArr() {
        return OrgArr;
    }

    public void setOrgArr(List<OrgInfoVO> orgArr) {
        OrgArr = orgArr;
    }

    public Long getAcctType() {
        return acctType;
    }

    public void setAcctType(Long acctType) {
        this.acctType = acctType;
    }

    public Long getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Long purchaseType) {
        this.purchaseType = purchaseType;
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

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public String getReRoleId() {
        return reRoleId;
    }

    public void setReRoleId(String reRoleId) {
        this.reRoleId = reRoleId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}