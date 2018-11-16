package com.ebase.report.dao.jurisdiction;

import java.util.List;

import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.vo.jurisdiction.AcctInfoVO;
import org.apache.ibatis.annotations.Param;

public interface AcctInfoMapper {
    int deleteByPrimaryKey(Long acctId);

    int insert(AcctInfo record);

    int insertSelective(AcctInfo record);

    AcctInfo selectByPrimaryKey(Long acctId);

    int updateByPrimaryKeySelective(AcctInfo record);

    int updateByPrimaryKey(AcctInfo record);

    //查询用户列表
    List<AcctInfo> find(AcctInfoVO reqBody);

    //验证用户名是否重复
    List<AcctInfo> verAcctInfo(AcctInfo reqBody);

    //验证是否有多个管理员
    List<AcctInfo> verAcctInfoType(AcctInfo reqBody);

    //验证是否有多个系统管理员
    List<AcctInfo> verAcctInfoSysId(AcctInfo reqBody);

    //查询用户分页列表
    List<AcctInfo> findPage(AcctInfo reqBody);

    List<AcctInfo> findAcctRoleInfo(AcctInfo reqBody);

    //添加用户
    int insertAcctInfo(AcctInfo acctInfo);

    //修改用户信息
    int updateAcctInfo(AcctInfo acctInfo);

    //用户下角色管理 - 查询
    List<AcctInfo> listSysAcct2Role(AcctInfo acctInfo);

//    //用户下角色管理 - 添加
//    List<AcctInfo> addSysAcct2Role(AcctInfo acctInfo);

    //用户下角色管理 - 删除
    List<AcctInfo> deleteSysAcct2Role(AcctInfo acctInfo);
    
    //查询用户下是否绑定组织机构
	int getAcctOrgid(@Param("cascadeDeletionOrgInfo") List<String> cascadeDeletionOrgInfo);
	
	//根据用户id查询该用户绑定的组织
	String getAcctInfo(Long acctId);

    AcctInfo LoginAcct(AcctInfo acctInfo);

    //根据角色查询用户
    List<AcctInfo> selectRoleIdAcctInfo(AcctInfo acctInfo);

    List<AcctInfo> selectOrgIdAcctInfo(AcctInfo acctInfo);

    //根据账号名查询账号对象
    AcctInfo selectByLogin(String acctId);

    Integer selectShareReportCount(AcctInfo acctInfo);

    List<AcctInfo> selectShareReport(AcctInfo acctInfo);
}