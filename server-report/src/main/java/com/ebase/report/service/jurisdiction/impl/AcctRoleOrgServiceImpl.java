package com.ebase.report.service.jurisdiction.impl;

import com.ebase.report.core.ParamType;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.dao.jurisdiction.AcctRoleOrgMapper;
import com.ebase.report.model.jurisdiction.AcctRoleOrg;
import com.ebase.report.service.jurisdiction.AcctRoleOrgService;
import com.ebase.report.vo.jurisdiction.AcctRoleOrgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * dal Interface:AcctRoleOrg
 * @author 
 * @date 2018-10-10
 */
@Service
@Transactional
public class AcctRoleOrgServiceImpl implements AcctRoleOrgService {

    @Autowired
    private AcctRoleOrgMapper acctRoleOrgMapper;

	@Override
    public List<AcctRoleOrgVO> selectAll() {
        //List<AcctRoleOrg> AcctRoleOrgs = AcctRoleOrgMapper.selectAll();
        //List<AcctRoleOrgVO> AcctRoleOrgVOs = BeanCopyUtil.copyList(AcctRoleOrgs, AcctRoleOrgVO.class);
        //return AcctRoleOrgVOs;
		return null;
    }

	@Override
    public List<AcctRoleOrgVO> select(AcctRoleOrgVO record){
		
		AcctRoleOrg model = BeanCopyUtil.copy(record, AcctRoleOrg.class);
		List<AcctRoleOrg> acctRoleOrg = acctRoleOrgMapper.select(model);
		List<AcctRoleOrgVO> acctRoleOrgVOs = BeanCopyUtil.copyList(acctRoleOrg, AcctRoleOrgVO.class);
		return acctRoleOrgVOs;
    }

	@Override
    public AcctRoleOrgVO selectByPrimaryKey(Long key){
    	AcctRoleOrg acctRoleOrg = acctRoleOrgMapper.selectByPrimaryKey(key);
        return BeanCopyUtil.copy(acctRoleOrg, AcctRoleOrgVO.class);
    }

	@Override
    public Integer insert(AcctRoleOrgVO record){
    	AcctRoleOrg acctRoleOrg = BeanCopyUtil.copy(record, AcctRoleOrg.class);
        return acctRoleOrgMapper.insert(acctRoleOrg);

    }


	@Override
	public Integer saveOrgInfo(AcctRoleOrgVO record){
		AcctRoleOrg acctRoleOrg = BeanCopyUtil.copy(record, AcctRoleOrg.class);
		Integer num=acctRoleOrgMapper.deleteAcctSysOrg(acctRoleOrg.getOrgId());
		//一个组织引用多个角色
		for(int i=0;i<acctRoleOrg.getRoleIds().length;i++){
			acctRoleOrg.setRoleId(acctRoleOrg.getRoleIds()[i]);
			acctRoleOrg.setStatus(Byte.parseByte("1"));
			acctRoleOrgMapper.insertSelective(acctRoleOrg);
		}
		return num;
	}

	@Override
    public Integer insertSelective(AcctRoleOrgVO record){
        AcctRoleOrg acctRoleOrg = BeanCopyUtil.copy(record, AcctRoleOrg.class);
		Integer num=1;
        if(ParamType.DELETE.getMsg().equals(record.getOpt())){
			num=acctRoleOrgMapper.deleteAcctOrg(acctRoleOrg);
		}

		//一个角色引用多个组织
		for(int i=0;i<acctRoleOrg.getOrgIds().length;i++){
			acctRoleOrg.setOrgId(acctRoleOrg.getOrgIds()[i]);
			acctRoleOrg.setStatus(Byte.parseByte("1"));
			num=acctRoleOrgMapper.insertSelective(acctRoleOrg);
		}
        return num;
    }
    
    @Override
    public Integer updateByPrimaryKey(AcctRoleOrgVO record){
    	AcctRoleOrg acctRoleOrg = BeanCopyUtil.copy(record, AcctRoleOrg.class);
        return acctRoleOrgMapper.updateByPrimaryKey(acctRoleOrg);
    }

	@Override
    public Integer updateByPrimaryKeySelective(AcctRoleOrgVO record){
        AcctRoleOrg acctRoleOrg = BeanCopyUtil.copy(record, AcctRoleOrg.class);
        return acctRoleOrgMapper.updateByPrimaryKeySelective(acctRoleOrg);
    }

	@Override
    public Integer deleteByPrimaryKey(Long key){
        return acctRoleOrgMapper.deleteByPrimaryKey(key);
    }
    
    /**
	 *  IN
	 *	<foreach collection="keys" open="(" close=")" item="key" separator=",">
	 *		{key}
	 *	</foreach>
	 */
    @Override
    public Integer deleteByPrimaryKeys(Set<Long> keys){
    	//return acctRoleOrgMapper.deleteByPrimaryKeys(keys);
        return null;
    }
    
    @Override
	public Integer keep(List<AcctRoleOrgVO> acctRoleOrgVOS) {
		int result = 0;
//		Set<Long> keys = new HashSet<>();
		for (AcctRoleOrgVO acctRoleOrgVO : acctRoleOrgVOS) {
			String opt = acctRoleOrgVO.getOpt();
			if (ParamType.DELETE.getMsg().equals(opt)) {
//				keys.add(AcctRoleOrgVO.getId());
				int i = deleteByPrimaryKey(acctRoleOrgVO.getRelaId());
				if (i > 0) {
					result++;
				}
			} else if (ParamType.UPDATE.getMsg().equals(opt)) {
				int i = updateByPrimaryKeySelective(acctRoleOrgVO);
				if (i > 0) {
					result++;
				}
			} else if (ParamType.INSERT.getMsg().equals(opt)) {
				int i = insertSelective(acctRoleOrgVO);
				if (i > 0) {
					result++;
				}
			}
		}
//		if(!keys.isEmpty())
//			result = result + deleteByPrimaryKeys(keys);
		return result;
	}
}