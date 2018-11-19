package com.ebase.report.service.jurisdiction;

import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.core.session.AcctLogin;
import com.ebase.report.core.session.AcctSession;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.model.jurisdiction.FunctionManage;
import com.ebase.report.vo.jurisdiction.AcctInfoVO;

import java.util.List;

/**
 * 后台用户
 * @Auther: wangyu
 */
public interface AcctService {

    /**
     * 用户注册
     * @param acctInfoVO
     * @return
     */
    Integer userRegister(AcctInfoVO acctInfoVO);

    /**
     * 根据账号名 获得 账号信息
     * @param acctName
     * @return
     */
    AcctInfoVO getAcct(String acctName);
    
    /**
     * 根据当前用户查询出，当前用户的全部功能
     * @param acctInfo
     * @return
     */
	List<FunctionManage> getUserFunctionAll(AcctInfo acctInfo);

    public PageDTO<AcctInfo> listShareReport(AcctInfo acctInfo);

    /**
     * 用户登陆
     * @param reqBody
     * @return
     */
    ServiceResponse<AcctSession> userLogin(AcctLogin reqBody);

    AcctInfo getAcctById(Long x);
}
