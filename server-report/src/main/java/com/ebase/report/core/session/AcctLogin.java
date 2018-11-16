package com.ebase.report.core.session;

/**
 * @Auther: wangyu
 */
public class AcctLogin {

    //用户账号  //这个名 可能是 手机号 或是用户名
    private String acctId;

    //用户密码
    private String password;


//    private String sessionId;



    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getSessionId() {
//        return sessionId;
//    }
//
//    public void setSessionId(String sessionId) {
//        this.sessionId = sessionId;
//    }

}
