package com.ebase.report.dao.jurisdiction;


import com.ebase.report.model.jurisdiction.AcctRoleGroupRole;

public interface AcctRoleGroupRoleMapper {
    int deleteByPrimaryKey(Long relaId);

    int insert(AcctRoleGroupRole record);

    int insertSelective(AcctRoleGroupRole record);

    AcctRoleGroupRole selectByPrimaryKey(Long relaId);

    int updateByPrimaryKeySelective(AcctRoleGroupRole record);

    int updateByPrimaryKey(AcctRoleGroupRole record);

    int deleteRoleGroupId(Long roleGroupId);

    int deleteRoleId(Long roleId);
}