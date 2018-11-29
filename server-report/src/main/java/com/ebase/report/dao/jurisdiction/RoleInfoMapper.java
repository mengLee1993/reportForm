package com.ebase.report.dao.jurisdiction;

import com.ebase.report.model.jurisdiction.RoleInfo;
import com.ebase.report.vo.jurisdiction.RoleInfoVO;

import java.util.List;

public interface RoleInfoMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(RoleInfo record);

    int insertSelective(RoleInfo record);

    RoleInfo selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(RoleInfo record);

    int updateByPrimaryKey(RoleInfo record);

    int updateSysId(RoleInfo record);

    List<RoleInfo> find(RoleInfo roleInfo);

    List<RoleInfo> findTwo(RoleInfo roleInfo);

    List<RoleInfo> findAll(RoleInfo roleInfo);

    List<RoleInfo> orgQuoteRoleInfo(RoleInfo roleInfo);

    List<RoleInfo> findAllLike(RoleInfo roleInfo);

    List<RoleInfo> findRoleDetailed(RoleInfo roleInfo);

    List<RoleInfo> roleInfoTree(RoleInfo roleInfo);

    List<RoleInfo> roleRoleAcctInfo(RoleInfo roleInfo);

    List<RoleInfo> roleGroupTree(RoleInfo roleInfo);

    RoleInfo findGroupId(RoleInfo roleInfo);


    RoleInfo selectListByRoleId(Long roleId);

    List<RoleInfo> sqlmysql(RoleInfo roleInfo);

    //验证是否名称重复
    List<RoleInfo> verificationIsTtitle(RoleInfo roleInfo);

    List<RoleInfo> roleGroupParentTree(RoleInfo roleInfo);

    List<RoleInfo> verificationDeleteRoelInfo(RoleInfo roleInfo);

    List<RoleInfo> verQuoteRoleTitle(RoleInfo roleInfo);

    List<RoleInfo> verQuoteRoleIds(RoleInfo roleInfo);

    RoleInfo selectInitializationRole(RoleInfo roleInfo);

    //分页+查询
    List<RoleInfo> queryForList(RoleInfo vo);

    RoleInfo selectByAcctId(Long acctId);

    //数据总条数查询
    Integer queryForCount(RoleInfo roleInfo);

    RoleInfo selectByAcctCode(String roleName);

    RoleInfo selectByAcctName(String x);
}