package com.ebase.report.service.jurisdiction;

import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.vo.jurisdiction.*;

import java.util.List;

/**
 * @Auther: wangyu
 */
public interface SysBasicsAcctService {

    AcctInfoVO LoginAcct(AcctInfoVO acctInfoVO);

    PageInfo<AcctInfoVO> listSysAcct(JsonRequest<AcctInfoVO> jsonRequest);

    List<AcctInfoExcel> sysAcctListExcel(AcctInfoVO acctInfoVO);

    JsonResponse keepSysAcct(JsonRequest<List<AcctRoleRealVO>> jsonRequest);

    JsonResponse addSysAcct(JsonRequest<AcctInfoVO> jsonRequest);

    AcctInfoRoleVO getSysAcct(AcctInfoVO reqBody) ;

    JsonResponse getSysAcctInfo(JsonRequest<AcctInfoVO> jsonRequest);

    JsonResponse sysAcctDiscontinuation(JsonRequest<AcctInfoVO> jsonRequest);

    JsonResponse sysAcctDeleteById(JsonRequest<AcctInfoVO> jsonRequest);

    JsonResponse sysAcctAddUser(JsonRequest<AcctToRoleInfoVO> jsonRequest);

    //当前用户下的角色管理 查询
    List<AcctInfoVO> listSysAcct2Role(JsonRequest<AcctInfoVO> jsonRequest);

    //当前用户下的角色管理 添加 + 删除
//    JsonResponse keepSysAcct2Role(JsonRequest<AcctInfoVO>jsonRequest);


    //当前用户下的角色管理 中间表添加
    JsonResponse addSysAcct2Role(JsonRequest<AcctRoleRealVO> jsonRequest);

    //当前用户下的角色管理 中间表删除
    JsonResponse deleteSysAcct2Role2(JsonRequest<AcctRoleRealVO> jsonRequest);

    //当前用户下的角色管理 全删除
    JsonResponse deleteSysAcct2Role(JsonRequest<AcctInfoVO> jsonRequest);

    List<AcctInfoVO> selectRoleIdAcctInfo(AcctInfoVO acctInfoVO);


//    //供应商注册添加用户
//    AcctInfoVO insertNoseAcctInfo(AcctInfoVO acctInfoVO);

    //根据用户查询角色和组织信息
    AcctInfoVO getAcctInfo(AcctInfoVO acctInfoVO);

    //用户添加验证信息
    String verAcctInfo(AcctInfoVO acctInfoVO);

//    //供应商自建修改用户
//    void updateNoseAcctInfo(AcctInfoVO acctInfoVO);
}
