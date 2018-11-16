package com.ebase.report.dao.jurisdiction;

import com.ebase.report.model.jurisdiction.AcctRoleOrg;

import java.util.List;

public interface AcctRoleOrgMapper {
    int deleteByPrimaryKey(Long relaId);

    int deleteAcctSysOrg(String orgId);

    int deleteAcctOrg(AcctRoleOrg acctRoleOrg);

    int insert(AcctRoleOrg record);

    int insertSelective(AcctRoleOrg record);

    AcctRoleOrg selectByPrimaryKey(Long relaId);

    int updateByPrimaryKeySelective(AcctRoleOrg record);

    int updateByPrimaryKey(AcctRoleOrg record);

    List<AcctRoleOrg> select(AcctRoleOrg record);
}