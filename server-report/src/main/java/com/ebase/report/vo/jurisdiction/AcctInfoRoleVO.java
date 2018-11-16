package com.ebase.report.vo.jurisdiction;

import java.util.List;

/**
 * 分配账号 和角色 详情
 * @Auther: wangyu
 */
public class AcctInfoRoleVO {

    //账号id
    private Long acctId;

    //账号名称 描述
    private String acctTitle;

    //账号编码
    private String acctCode;

    // --- 集团信息
    //集团标识
    private Long aCompanyId;

    //集团名称
    private String companyName;

    //集团编码
    private String companyCode;

    //角色关系表
    private List<RoleInfoVO> roleInfoVOs;


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

    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    public Long getaCompanyId() {
        return aCompanyId;
    }

    public void setaCompanyId(Long aCompanyId) {
        this.aCompanyId = aCompanyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public List<RoleInfoVO> getRoleInfoVOs() {
        return roleInfoVOs;
    }

    public void setRoleInfoVOs(List<RoleInfoVO> roleInfoVOs) {
        this.roleInfoVOs = roleInfoVOs;
    }
}
