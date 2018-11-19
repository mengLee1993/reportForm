package com.ebase.report.service.jurisdiction.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.ebase.report.core.ParamType;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.service.jurisdiction.SysInfoService;
import com.ebase.report.vo.jurisdiction.SysInfoVO;
import com.ebase.report.common.IsDelete;
import com.ebase.report.dao.jurisdiction.AcctOrgSysMapper;
import com.ebase.report.dao.jurisdiction.FunctionManageMapper;
import com.ebase.report.dao.jurisdiction.RoleInfoMapper;
import com.ebase.report.dao.jurisdiction.SysInfoMapper;
import com.ebase.report.model.jurisdiction.AcctOrgSys;
import com.ebase.report.model.jurisdiction.FunctionManage;
import com.ebase.report.model.jurisdiction.RoleInfo;
import com.ebase.report.model.jurisdiction.SysInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * dal Interface:SysInfo
 * @author 
 * @date 2018-10-10
 */
@Service
@Transactional
public class SysInfoServiceImpl implements SysInfoService {

    @Autowired
    private SysInfoMapper sysInfoMapper;

	@Autowired
	private AcctOrgSysMapper acctOrgSysMapper;

	@Autowired
	private RoleInfoMapper roleInfoMapper;

	@Autowired
	private FunctionManageMapper functionManageMapper;

	@Override
    public List<SysInfoVO> selectAll() {
        //List<SysInfo> sysInfos = sysInfoMapper.selectAll();
        //List<SysInfoVO> sysInfoVOs = BeanCopyUtil.copyList(sysInfos, SysInfoVO.class);
        //return sysInfoVOs;
		return null;
    }

	@Override
    public PageInfo<SysInfoVO> select(SysInfoVO record){
		
		SysInfo model = BeanCopyUtil.copy(record, SysInfo.class);
		List<SysInfo> sysInfos = sysInfoMapper.select(model);
		List<SysInfoVO> sysInfoVOs = BeanCopyUtil.copyList(sysInfos, SysInfoVO.class);
		PageInfo<SysInfoVO> pageInfo=new PageInfo<SysInfoVO>();
		return pageInfo;
    }

	@Override
	public List<SysInfoVO> selectSysEstablish(SysInfoVO sysInfoVO){
		SysInfo model = BeanCopyUtil.copy(sysInfoVO, SysInfo.class);
		List<SysInfo> sysInfo=new ArrayList<>();
		sysInfo = sysInfoMapper.selectSysEstablish(model);
		return BeanCopyUtil.copyList(sysInfo, SysInfoVO.class);
	}

	@Override
	public List<SysInfoVO> selectSysInfoOrg(SysInfoVO sysInfoVO){
		SysInfo model = BeanCopyUtil.copy(sysInfoVO, SysInfo.class);
		List<SysInfo> sysInfo=new ArrayList<>();
		if(sysInfoVO.getOrgId().equals(sysInfoVO.getQuoteOrgId())){
		    //如果系统是当前系统引用
            //那么查询出的系统是子级组织的创建系统
            sysInfo = sysInfoMapper.selectSysSubgrade(model);
        }else{
            if(model.getAcctType()==0 || model.getAcctType().equals(0)){
            	//超级管理员查询出这个系统所有引用的
                sysInfo = sysInfoMapper.selectSysSuperYinyong(model);
            }else{
                sysInfo = sysInfoMapper.selectSysOrg(model);
            }
        }
		return BeanCopyUtil.copyList(sysInfo, SysInfoVO.class);
	}


	@Override
	public List<SysInfoVO> selectSysInfoOrgCreate(SysInfoVO sysInfoVO){
		SysInfo model = BeanCopyUtil.copy(sysInfoVO, SysInfo.class);
		List<SysInfo> sysInfo=new ArrayList<>();
		if(model.getAcctType()==0 || model.getAcctType().equals(0)){
			sysInfo = sysInfoMapper.selectSysSuper(model);
		}else {
			sysInfo = sysInfoMapper.selectSysInfoOrgCreate(model);
		}

		return BeanCopyUtil.copyList(sysInfo, SysInfoVO.class);
	}

	@Override
	public List<SysInfoVO> selectSysInfoOrgSee(SysInfoVO sysInfoVO){
		SysInfo model = BeanCopyUtil.copy(sysInfoVO, SysInfo.class);
		List<SysInfo> sysInfo=new ArrayList<>();
		if(model.getAcctType()==0 || model.getAcctType().equals(0)){
			sysInfo = sysInfoMapper.selectSysSuper(model);
		}else if (model.getAcctType()==3 || model.getAcctType().equals(3)){
			sysInfo = sysInfoMapper.selectSysOrgSeeAcct(model);
		}else{
			sysInfo = sysInfoMapper.selectSysInfoOrgSee(model);
		}


		return BeanCopyUtil.copyList(sysInfo, SysInfoVO.class);
	}

	@Override
    public SysInfoVO selectByPrimaryKey(Long key){
    	SysInfo sysInfo = sysInfoMapper.selectByPrimaryKey(key);
        return BeanCopyUtil.copy(sysInfo, SysInfoVO.class);
    }

	@Override
    public Integer insert(SysInfoVO record){
    	SysInfo sysInfo = BeanCopyUtil.copy(record, SysInfo.class);
        return sysInfoMapper.insert(sysInfo);

    }

	@Override
    public SysInfoVO insertSelective(SysInfoVO record){
        SysInfo sysInfo = BeanCopyUtil.copy(record, SysInfo.class);
		sysInfo.setCreatedTime(new Date());
		sysInfo.setIsDelete(Byte.parseByte("0"));
        Integer num=sysInfoMapper.insertSelective(sysInfo);
		AcctOrgSys acctOrgSys=new AcctOrgSys();
		acctOrgSys.setOrgId(sysInfo.getOrgId());
		acctOrgSys.setSysId(sysInfo.getSysId());
		acctOrgSys.setCreatedTime(new Date());
		acctOrgSys.setCreatedBy(sysInfo.getCreatedBy());
		acctOrgSys.setStatus(Byte.parseByte("0"));
		acctOrgSysMapper.insertSelective(acctOrgSys);
		record.setSysId(sysInfo.getSysId());
        return record;
    }
    
    @Override
    public Integer updateByPrimaryKey(SysInfoVO record){
    	SysInfo sysInfo = BeanCopyUtil.copy(record, SysInfo.class);

        return sysInfoMapper.updateByPrimaryKey(sysInfo);
    }

	@Override
    public SysInfoVO updateByPrimaryKeySelective(SysInfoVO record){
        SysInfo sysInfo = BeanCopyUtil.copy(record, SysInfo.class);
		sysInfo.setUpdatedTime(new Date());
		sysInfoMapper.updateByPrimaryKeySelective(sysInfo);
        return record;
    }


	@Override
	public String verSysInfo(SysInfoVO sysInfoVO){
		SysInfo sysInfo = BeanCopyUtil.copy(sysInfoVO, SysInfo.class);
		List<SysInfo> list=sysInfoMapper.verSysInfo(sysInfo);
		String body="";
		if(CollectionUtils.isEmpty(list)){
			body="是否删除";
		}else{
			body="功能与组织有关联，是否删除";
		}
		return body;
	}

	@Override
	public String verInsertSysInfo(SysInfoVO sysInfoVO){
		SysInfo sysInfo = BeanCopyUtil.copy(sysInfoVO, SysInfo.class);
		List<SysInfo> list=sysInfoMapper.verInsertSysInfo(sysInfo);
		String body="";
		if(CollectionUtils.isEmpty(list)){
			body="0";
		}else{
			body="1";
		}
		return body;
	}

	@Override
    public SysInfoVO deleteByPrimaryKey(Long key){
		//删除系统
		SysInfo sysInfo = new SysInfo();
		sysInfo.setSysId(key);
		sysInfo.setIsDelete(Byte.parseByte("1"));
		sysInfo.setUpdatedTime(new Date());
		sysInfoMapper.updateByPrimaryKeySelective(sysInfo);
		//删除角色
		RoleInfo roleInfo=new RoleInfo();
		roleInfo.setSysId(key);
		roleInfo.setIsDelete(IsDelete.YES.getCode());
		roleInfoMapper.updateSysId(roleInfo);
		//删除资源
		FunctionManage functionManage=new FunctionManage();
		functionManage.setSysId(key);
		functionManageMapper.updateSysId(functionManage);
		SysInfoVO sysInfoVO= BeanCopyUtil.copy(sysInfo, SysInfoVO.class);
		sysInfoVO.setOpt("delete");
        return sysInfoVO;
    }
    
    /**
	 *  IN
	 *	<foreach collection="keys" open="(" close=")" item="key" separator=",">
	 *		{key}
	 *	</foreach>
	 */
    @Override
    public Integer deleteByPrimaryKeys(Set<Long> keys){
    	//return sysInfoMapper.deleteByPrimaryKeys(keys);
        return null;
    }
    
    @Override
	public SysInfoVO keep(List<SysInfoVO> sysInfoVOs) {
		SysInfoVO sysInfo=new SysInfoVO();
//		Set<Long> keys = new HashSet<>();
		for (SysInfoVO sysInfoVO : sysInfoVOs) {
			String opt = sysInfoVO.getOpt();
			if (ParamType.DELETE.getMsg().equals(opt)) {
//				keys.add(sysInfoVO.getId());
				deleteByPrimaryKey(sysInfoVO.getSysId());
			} else if (ParamType.UPDATE.getMsg().equals(opt)) {
				updateByPrimaryKeySelective(sysInfoVO);
			} else if (ParamType.INSERT.getMsg().equals(opt)) {
				insertSelective(sysInfoVO);
			}
			sysInfo=sysInfoVO;
		}
//		if(!keys.isEmpty())
//			result = result + deleteByPrimaryKeys(keys);
		return sysInfo;
	}
}