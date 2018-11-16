package com.ebase.report.dao.jurisdiction;

import com.ebase.report.model.jurisdiction.FunctionManage;
import com.ebase.report.model.jurisdiction.SysInfo;

import java.util.List;

public interface SysInfoMapper {
    int deleteByPrimaryKey(Long sysId);

    int insert(SysInfo record);

    int insertSelective(SysInfo record);

    SysInfo selectByPrimaryKey(Long sysId);

    int updateByPrimaryKeySelective(SysInfo record);

    int updateByPrimaryKey(SysInfo record);

    List<SysInfo> select(SysInfo record);

    List<SysInfo> selectSysSuper(SysInfo record);

    List<SysInfo> selectSysInfoOrgCreate(SysInfo record);

    List<SysInfo> selectSysSubgrade(SysInfo record);

    List<SysInfo> selectSysEstablish(SysInfo record);

    List<SysInfo> selectSysOrg(SysInfo record);

    List<SysInfo> selectSysSuperYinyong(SysInfo record);

    List<SysInfo> selectSysInfoOrgSee(SysInfo record);

    List<SysInfo> selectSysOrgSeeAcct(SysInfo record);

    List<SysInfo> verSysInfo(SysInfo record);

    List<SysInfo> verInsertSysInfo(SysInfo record);

    List<SysInfo> selectSysInfoOrgId(FunctionManage functionManage);

    List<SysInfo> selectSysInfoSuper(FunctionManage functionManage);
}