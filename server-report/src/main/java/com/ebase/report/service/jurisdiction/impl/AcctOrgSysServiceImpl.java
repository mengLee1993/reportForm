package com.ebase.report.service.jurisdiction.impl;

import java.util.List;
import java.util.Set;

import com.ebase.report.core.ParamType;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.service.jurisdiction.AcctOrgSysService;
import com.ebase.report.vo.jurisdiction.AcctOrgSysVO;
import com.ebase.report.dao.jurisdiction.AcctOrgSysMapper;
import com.ebase.report.model.jurisdiction.AcctOrgSys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * dal Interface:AcctOrgSys
 * @author 
 * @date 2018-10-10
 */
@Service
@Transactional
public class AcctOrgSysServiceImpl implements AcctOrgSysService {

    @Autowired
    private AcctOrgSysMapper acctOrgSysMapper;

	@Override
    public List<AcctOrgSysVO> selectAll() {
        //List<AcctOrgSys> acctOrgSyss = acctOrgSysMapper.selectAll();
        //List<AcctOrgSysVO> acctOrgSysVOs = BeanCopyUtil.copyList(acctOrgSyss, AcctOrgSysVO.class);
        //return acctOrgSysVOs;
		return null;
    }

	@Override
    public List<AcctOrgSysVO> select(AcctOrgSysVO record){
		
		AcctOrgSys model = BeanCopyUtil.copy(record, AcctOrgSys.class);
		List<AcctOrgSys> acctOrgSyss = acctOrgSysMapper.select(model);
		List<AcctOrgSysVO> acctOrgSysVOs = BeanCopyUtil.copyList(acctOrgSyss, AcctOrgSysVO.class);
		return acctOrgSysVOs;
    }

	@Override
    public AcctOrgSysVO selectByPrimaryKey(Long key){
    	AcctOrgSys acctOrgSys = acctOrgSysMapper.selectByPrimaryKey(key);
        return BeanCopyUtil.copy(acctOrgSys, AcctOrgSysVO.class);
    }

	@Override
    public Integer insert(AcctOrgSysVO record){
    	AcctOrgSys acctOrgSys = BeanCopyUtil.copy(record, AcctOrgSys.class);
        return acctOrgSysMapper.insert(acctOrgSys);

    }


	@Override
	public Integer saveOrgInfo(AcctOrgSysVO record){
		AcctOrgSys acctOrgSys = BeanCopyUtil.copy(record, AcctOrgSys.class);
		Integer num=acctOrgSysMapper.deleteAcctOrg(acctOrgSys.getSysId());
		for(int i=0;i<acctOrgSys.getOrgIds().length;i++){
			acctOrgSys.setOrgId(acctOrgSys.getOrgIds()[i]);
			acctOrgSys.setStatus(Byte.parseByte("1"));
			acctOrgSysMapper.insertSelective(acctOrgSys);
		}
		return num;
	}

	@Override
    public Integer insertSelective(AcctOrgSysVO record){
        AcctOrgSys acctOrgSys = BeanCopyUtil.copy(record, AcctOrgSys.class);
		Integer num=acctOrgSysMapper.deleteAcctSysOrg(acctOrgSys.getOrgId());
		for(int i=0;i<acctOrgSys.getSysIds().length;i++){
			acctOrgSys.setSysId(acctOrgSys.getSysIds()[i]);
			acctOrgSys.setStatus(Byte.parseByte("1"));
			acctOrgSysMapper.insertSelective(acctOrgSys);
		}
        return num;
    }
    
    @Override
    public Integer updateByPrimaryKey(AcctOrgSysVO record){
    	AcctOrgSys acctOrgSys = BeanCopyUtil.copy(record, AcctOrgSys.class);
        return acctOrgSysMapper.updateByPrimaryKey(acctOrgSys);
    }

	@Override
    public Integer updateByPrimaryKeySelective(AcctOrgSysVO record){
        AcctOrgSys acctOrgSys = BeanCopyUtil.copy(record, AcctOrgSys.class);
        return acctOrgSysMapper.updateByPrimaryKeySelective(acctOrgSys);
    }

	@Override
    public Integer deleteByPrimaryKey(Long key){
        return acctOrgSysMapper.deleteByPrimaryKey(key);
    }
    
    /**
	 *  IN
	 *	<foreach collection="keys" open="(" close=")" item="key" separator=",">
	 *		{key}
	 *	</foreach>
	 */
    @Override
    public Integer deleteByPrimaryKeys(Set<Long> keys){
    	//return acctOrgSysMapper.deleteByPrimaryKeys(keys);
        return null;
    }
    
    @Override
	public Integer keep(List<AcctOrgSysVO> acctOrgSysVOs) {
		int result = 0;
//		Set<Long> keys = new HashSet<>();
		for (AcctOrgSysVO acctOrgSysVO : acctOrgSysVOs) {
			String opt = acctOrgSysVO.getOpt();
			if (ParamType.DELETE.getMsg().equals(opt)) {
//				keys.add(acctOrgSysVO.getId());
				int i = deleteByPrimaryKey(acctOrgSysVO.getRelaId());
				if (i > 0) {
					result++;
				}
			} else if (ParamType.UPDATE.getMsg().equals(opt)) {
				int i = updateByPrimaryKeySelective(acctOrgSysVO);
				if (i > 0) {
					result++;
				}
			} else if (ParamType.INSERT.getMsg().equals(opt)) {
				int i = insertSelective(acctOrgSysVO);
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