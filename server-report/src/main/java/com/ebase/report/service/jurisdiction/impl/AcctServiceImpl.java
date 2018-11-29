package com.ebase.report.service.jurisdiction.impl;

import com.ebase.report.common.IsDelete;
import com.ebase.report.common.Status;
import com.ebase.report.core.Base64Util;
import com.ebase.report.core.CacheService;
import com.ebase.report.core.MD5Util;
import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.core.session.Acct;
import com.ebase.report.core.session.AcctLogin;
import com.ebase.report.core.session.AcctSession;
import com.ebase.report.core.session.CacheKeyConstant;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.dao.jurisdiction.AcctInfoMapper;
import com.ebase.report.dao.jurisdiction.FunctionManageMapper;
import com.ebase.report.dao.jurisdiction.RoleInfoMapper;
import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.model.jurisdiction.FunctionManage;
import com.ebase.report.model.jurisdiction.RoleInfo;
import com.ebase.report.service.jurisdiction.AcctService;
import com.ebase.report.vo.jurisdiction.AcctInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wangyu
 */
@Service
public class AcctServiceImpl implements AcctService {

    private static Logger LOG = LoggerFactory.getLogger(AcctServiceImpl.class);


    private static String VERSION = "1";


    private static Integer TIME_EXPIRE = 60 * 30; //30分钟

    @Autowired
    private AcctInfoMapper acctInfoMapper;
    
    @Autowired
    private FunctionManageMapper functionManageMapper;

    //角色id
    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Override
    public Integer userRegister(AcctInfoVO acctInfoVO) {

        //加密一下
        acctInfoVO.setAcctPassword(MD5Util.encrypByMd5(acctInfoVO.getAcctPassword()));

        //保存用户表的信息 和 客户表
        AcctInfo acctInfo = BeanCopyUtil.copy(acctInfoVO,AcctInfo.class);

        int count =  acctInfoMapper.insertSelective(acctInfo);

        return count;
    }

    @Override
    public AcctInfoVO getAcct(String acctName) {
        AcctInfo acctInfo = acctInfoMapper.selectByLogin(acctName);


        return BeanCopyUtil.copy(acctInfo,AcctInfoVO.class);
    }
    
    /**
     * 根据当前用户查询出，当前用户的全部功能
     */
	@Override
	public List<FunctionManage> getUserFunctionAll(AcctInfo acctInfo) {
		List<FunctionManage> functionManageAll = functionManageMapper.selsctUserFunctionAll(acctInfo);
		return functionManageAll;
	}

    @Override
    public PageDTO<AcctInfo> listShareReport(AcctInfo acctInfo) {

        PageDTO<AcctInfo> pageDTO = new PageDTO<>(acctInfo.getPageNum(),acctInfo.getPageSize());

        Integer count = acctInfoMapper.selectShareReportCount(acctInfo);

        pageDTO.setTotal(count);

        acctInfo.setStartRow(pageDTO.getStartRow());
        List<AcctInfo> list = acctInfoMapper.selectShareReport(acctInfo);
        for(int i=0;i<list.size();i++){
            list.get(i).setReAcctId(String.valueOf(list.get(i).getAcctId()));
        }
        pageDTO.setResultData(list);
        return pageDTO;
    }



    /**
     * 用户登录
     * @param acctLogin
     * @return
     */
    @Override
    public ServiceResponse<AcctSession> userLogin(AcctLogin acctLogin) {

        ServiceResponse<AcctSession> response = new ServiceResponse<AcctSession>();
        response.setRetCode("9999999");
        try{
            //校验用户信息
            if(StringUtils.isEmpty(acctLogin.getAcctId())){
                response.setRetMessage("用户名不能为空");
                return response;
            }
            if(StringUtils.isEmpty(acctLogin.getPassword())){
                response.setRetMessage("密码不能为空");
                return response;
            }
            //根据账号名查询账号对象
            AcctInfo acctInfo = acctInfoMapper.selectByLogin(acctLogin.getAcctId());

            //关联查询角色数据


            if(acctInfo == null){
                response.setRetMessage("当前账号不存在");
                return response;
            }

            //用户状态
            if(Status.STOP.getCode().equals(acctInfo.getStatus())){
                response.setRetMessage("当前账号已失效");
                return response;
            }else if(IsDelete.YES.getCode().equals(acctInfo.getIsDelete())){ //是否删除
                response.setRetMessage("当前用户已删除");
                return response;
            }

            //验证两次密码是否一致
            String password = MD5Util.encrypByMd5(acctLogin.getPassword());
            if(acctInfo.getAcctPassword().equals(password)){
                //登录成功
                Acct acct = BeanCopyUtil.copy(acctInfo,Acct.class);

                AcctSession acctSession = loginSuccess(acct,acctLogin);

                response.setRetContent(acctSession);
            }else{
                response.setRetMessage("密码输入错误!");
                return response;
            }

            response.setRetCode(ServiceResponse.SUCCESS_CODE);
        }catch (Exception e){
            LOG.error("error = {}",e);
            e.printStackTrace();
        }
        return response;

    }

    @Override
    public AcctInfo getAcctById(Long x) {

        return acctInfoMapper.selectByPrimaryKey(x);
    }


    private AcctSession loginSuccess(Acct acct, AcctLogin acctLogin) {
        AcctSession copy = BeanCopyUtil.copy(acct, AcctSession.class);
        copy.setReAcctId(String.valueOf(copy.getAcctId()));
        //根据用户id 查询角色对象
        RoleInfo roleInfo = roleInfoMapper.selectByAcctId(copy.getAcctId());

        //用户不一定都有角色
        ArrayList<String> objects = new ArrayList<>();
        if(roleInfo != null){
            copy.setRoleId(roleInfo.getRoleId());
            objects.add(roleInfo.getRoleId().toString());
            copy.setReRoleId(objects);
        }


        return copy;

    }
}
