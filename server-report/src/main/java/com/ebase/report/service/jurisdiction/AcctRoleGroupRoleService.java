package com.ebase.report.service.jurisdiction;



import com.ebase.report.vo.jurisdiction.AcctRoleGroupRoleVO;

/**
 * @Auther: zhaoyuhang
 */
public interface AcctRoleGroupRoleService {

    Integer delAcctRoleGroupRole(AcctRoleGroupRoleVO jsonRequest);

    Integer addAcctRoleGroupRole(AcctRoleGroupRoleVO jsonRequest);
}
