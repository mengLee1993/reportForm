package com.ebase.report.dao.jurisdiction;

import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.model.jurisdiction.FunctionManage;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface FunctionManageMapper {
    int deleteByPrimaryKey(Long functionId);

    int insert(FunctionManage record);

    int insertSelective(FunctionManage record);

    FunctionManage selectByPrimaryKey(Long functionId);

    FunctionManage maxCode(@Param(value = "parentApplicationId") Long parentApplicationId);

    int updateByPrimaryKeySelective(FunctionManage record);

    int updateSysId(FunctionManage record);

    int updateByPrimaryKey(FunctionManage record);

    List<FunctionManage> findRole(FunctionManage functionManage);

    List<FunctionManage> findRoleThree(FunctionManage functionManage);

    List<FunctionManage> find(FunctionManage functionManage);

    List<FunctionManage> findThree(FunctionManage functionManage);

    List<FunctionManage> findPath(FunctionManage functionManage);

    List<FunctionManage> verificationDeleteFunction(FunctionManage functionManage);

    FunctionManage findParentApplicationId(FunctionManage functionManage);

    int updateFunctionIdAll(FunctionManage functionManage);

    List<FunctionManage> verificationFunIsTtitle(FunctionManage functionManage);

	List<FunctionManage> selsctUserFunctionAll(AcctInfo acctInfo);

}