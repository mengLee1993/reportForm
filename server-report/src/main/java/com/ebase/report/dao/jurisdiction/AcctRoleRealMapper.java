package com.ebase.report.dao.jurisdiction;

import com.ebase.report.model.jurisdiction.AcctRoleReal;

import java.util.List;

public interface AcctRoleRealMapper {

    int deleteByPrimaryKey(Long acctId);

    int insert(AcctRoleReal record);


    int insertSelective(AcctRoleReal acctRoleReal);

    AcctRoleReal selectByPrimaryKey(Long relaId);

    int updateByPrimaryKeySelective(AcctRoleReal record);

    int updateByPrimaryKey(AcctRoleReal record);

    //添加用户角色中间表
    int insertAcctRole(AcctRoleReal acctRoleReal);

    //按用户id查找角色用户中间表中的所有用户所拥有的角色id
    List<AcctRoleReal> selectAcctRoleRealListByAcctId(Long acctId);

    //修改用户角色 钱 先删除 角色用户中间表中的用户角色关联
    int deleteAcctRole(AcctRoleReal acctRoleReal);

    //添加 用户角色
    void addSysAcct2Role(AcctRoleReal acctRoleReal);

    //删除 用户角色 中间表删除  -->zhaoyichen
    void deleteByPrimaryKey3(AcctRoleReal acctRoleReal);

    //删除 中间表删除
    void deleteByPrimaryKey2(Long acctId);
}