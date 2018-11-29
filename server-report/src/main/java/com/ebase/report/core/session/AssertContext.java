package com.ebase.report.core.session;


import java.util.List;

/**
 * @Auther: wangyu
 */
public class AssertContext {

    private static ThreadLocal<AcctSession> allContext = new ThreadLocal<AcctSession>();

    public static void init(AcctSession acctSession) {
        allContext.set(acctSession);
    }

    public final static void destroy() {
        allContext.remove();
    }

    /**
     *  弃用
     *
     */
    @Deprecated
    public static Long getAcctId(){
        Long acctId = null;
        if(allContext.get() != null){
            acctId = allContext.get().getAcctId();
        }
        return acctId;
    }

    /**
     *  获得当前用户等了id
     */
    public static String getReAcctId(){
        String reAcctId = null;
        if(allContext.get() != null){
            reAcctId = allContext.get().getReAcctId();
        }
        return reAcctId;
    }

    /**
     *弃用
     * @return
     */
    @Deprecated
    public static Long getRoleId(){
        Long roleId = null;
        if(allContext.get() != null){
            roleId = allContext.get().getRoleId();
        }
        return roleId;
    }

    /**
     *  re.id
     */
    public static List<String> getReRoleId(){
        List<String> reAcctId = null;
        if(allContext.get() != null){
            reAcctId = allContext.get().getReRoleId();
        }
        return reAcctId;
    }

    /**
     * 获得当前登陆账号名称
     * @return
     */
    public static String getAcctName(){
        String acctTitle = null;
        if(allContext.get() != null){
            acctTitle = allContext.get().getAcctTitle();
        }
        return acctTitle;
    }

    /**
     * 获得token
     * @return
     */
    public static String getToken(){
        String token = null;
        if(allContext.get() != null){
            token = allContext.get().getToken();
        }
        return token;
    }



    /**
     * 获得组织ID
     * @return
     */
    public static String getOrgId(){
        String orgId= null;
        if(allContext.get() != null){
            orgId = allContext.get().getOrgId();
        }
        return orgId;
    }

    /**
     * 获取用户类型
     * @return
     */
    public static Long getAcctType(){
        Long acctType= null;
        if(allContext.get() != null){
            acctType = allContext.get().getAcctType();
        }
        return acctType;
    }
}
