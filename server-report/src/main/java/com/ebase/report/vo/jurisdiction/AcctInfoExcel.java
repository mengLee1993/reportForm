package com.ebase.report.vo.jurisdiction;


import com.ebase.report.core.utils.ExcelAttribute;

/**
 * 用于导出excel
 * @Auther: wangyu
 */
public class AcctInfoExcel {

    @ExcelAttribute(name = "行号")
    private Integer line; //行号

    //账号 编码
    @ExcelAttribute(name = "账号")
    private String acctCode;

    //账号名称
    @ExcelAttribute(name = "描述")
    private String acctTitle;

    //集团名称
    @ExcelAttribute(name = "集团名称")
    private String companyName;

    //集团编码
    @ExcelAttribute(name = "集团编码")
    private String companyCode;

    //电子邮件
    @ExcelAttribute(name = "电子邮件")
    private String email;

    //手机号
    @ExcelAttribute(name = "手机号")
    private String mobilePhone;

    //生效时间
    @ExcelAttribute(name = "生效时间")
    private String startTimeView;

    //失效时间
    @ExcelAttribute(name = "失效时间")
    private String entTimeView;

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    public String getAcctTitle() {
        return acctTitle;
    }

    public void setAcctTitle(String acctTitle) {
        this.acctTitle = acctTitle;
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

    public String getStartTimeView() {
        return startTimeView;
    }

    public void setStartTimeView(String startTimeView) {
        this.startTimeView = startTimeView;
    }

    public String getEntTimeView() {
        return entTimeView;
    }

    public void setEntTimeView(String entTimeView) {
        this.entTimeView = entTimeView;
    }
}
