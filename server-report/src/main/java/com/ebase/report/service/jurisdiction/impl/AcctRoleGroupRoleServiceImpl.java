package com.ebase.report.service.jurisdiction.impl;


import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.vo.jurisdiction.AcctRoleGroupRoleVO;
import com.ebase.report.dao.jurisdiction.AcctRoleGroupRoleMapper;
import com.ebase.report.model.jurisdiction.AcctRoleGroupRole;
import com.ebase.report.service.jurisdiction.AcctRoleGroupRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: zhaoyuhang
 */
@Service
public class AcctRoleGroupRoleServiceImpl implements AcctRoleGroupRoleService {

    private static Logger LOG = LoggerFactory.getLogger(AcctRoleGroupRoleServiceImpl.class);

    @Autowired
    private AcctRoleGroupRoleMapper acctRoleGroupRoleMapper;

    @Override
    public Integer delAcctRoleGroupRole(AcctRoleGroupRoleVO jsonRequest) {

        AcctRoleGroupRole reqBody = new AcctRoleGroupRole();
        BeanCopyUtil.copy(jsonRequest, reqBody);

        //删除此角色下的功能关联
        Integer num=acctRoleGroupRoleMapper.deleteRoleGroupId(reqBody.getRoleGroupId());

        return num;
    }

    @Override
    public Integer addAcctRoleGroupRole(AcctRoleGroupRoleVO acctRoleGroupRoleVO) {
        AcctRoleGroupRole reqBody = new AcctRoleGroupRole();
        BeanCopyUtil.copy(acctRoleGroupRoleVO, reqBody);

        //先删除此角色下的功能关联
        Integer num=acctRoleGroupRoleMapper.deleteRoleGroupId(reqBody.getRoleGroupId());
        String[]  strs=reqBody.getRoleIds().split(",");
        for(int i=0;i<strs.length;i++) {
            reqBody.setRoleId(Long.parseLong(strs[i]));
            //添加角色与功能的关联
            acctRoleGroupRoleMapper.insertSelective(reqBody);
        }
        return num;
    }

}
