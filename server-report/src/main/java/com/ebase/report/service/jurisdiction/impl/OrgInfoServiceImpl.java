package com.ebase.report.service.jurisdiction.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ebase.report.core.MathHelper;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.core.zk.StringHelper;
import com.ebase.report.vo.jurisdiction.SysInfoVO;
import com.ebase.report.model.jurisdiction.SysInfo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebase.report.vo.jurisdiction.OrgInfoVO;
import com.ebase.report.dao.jurisdiction.AcctInfoMapper;
import com.ebase.report.dao.jurisdiction.OrgInfoMapper;
import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.model.jurisdiction.OrgInfo;
import com.ebase.report.service.jurisdiction.OrgInfoService;
import org.springframework.util.CollectionUtils;


@Service
public class OrgInfoServiceImpl implements OrgInfoService{
		
	@Autowired
	private OrgInfoMapper orgInfoMapper;
	
	@Autowired
	private AcctInfoMapper acctInfoMapper;
	
	/**
	 * 组织机构信息添加
	 * 传入父类ParentId添加子类OrgCode
	 */
	@Override
	public synchronized Long addOrgInfo(OrgInfo orgInfo) {
		String orgInfoId = getOrgInfoId(orgInfo.getParentId());
		orgInfo.setId(orgInfoId);
		Long i = orgInfoMapper.insertOrgInfo(orgInfo);
		return i;
	}
	
	/**
	 * 查询指定长度的id最大值
	 * 如果存在加一，不存在就将父节点的parentId后面拼上101
	 * 最高节点parentId为0
	 * @return
	 */
	public String getOrgInfoId(String orgCode) {
		StringBuilder result = new StringBuilder(orgCode);
		String orgInfoId = orgInfoMapper.getOrgInfoId(orgCode);
		if (StringHelper.isBlank(orgInfoId))
			orgInfoId = result.append("101").toString();
		else 
			orgInfoId = MathHelper.add(orgInfoId);
		return orgInfoId;
	}
	
	/**
	 * 组织机构信息刪除
	 * 判断该组织及其子类组织是否有用户绑定，
	 * 有：不准删除并抛出异常提示      没有：可以删除
	 */
	@Override
	public Integer removeOrgInfo(OrgInfo orgInfo) {
		//需要删除的组织id集合，如果有返回需要删除的组织，如果没有返回空	
    	List<String> cascadeDeletionOrgInfo = cascadeDeletionOrgInfo(orgInfo);
    	cascadeDeletionOrgInfo.add(orgInfo.getId());
    	//到用户表查询是否有用户绑定组织
    	int a = acctInfoMapper.getAcctOrgid(cascadeDeletionOrgInfo);
    	//批量删除组织
    	int i = 0;
    	System.out.println((a==0));
    	if(a == 0) {
    		i = orgInfoMapper.deleteOrgInfo(cascadeDeletionOrgInfo);
    	}
    	return i;
	}
	/**
	 * 根据父节点id级联删除下面所有的子id
	 * @param orgInfo
	 * @return
	 */
	public List<String> cascadeDeletionOrgInfo(OrgInfo orgInfo) {
		List<String> childorgInfoMapper = orgInfoMapper.getOrgInfoIdAll(orgInfo);
		return childorgInfoMapper;
	}
	
	/**
	 * 组织机构信息父查子
	 */
	@Override
	public List<OrgInfo> getListTreeOrgInfo(OrgInfo orgInfo) {
		//如果id有值就查子类，如果id无值就按id2查询父类id
		List<OrgInfo> childorgInfoMapper = null;
		if(orgInfo.getId() != "") {
            childorgInfoMapper = orgInfoMapper.getChildOrgInfo(orgInfo);
		}else {
			childorgInfoMapper = orgInfoMapper.selectListOrgInfoAll(orgInfo);
		}
		return childorgInfoMapper;
	}


	/**
	 * 组织机构信息递归查询
	 */
	@Override
	public OrgInfoVO getChildTreeOrgInfo(OrgInfoVO orgInfoVO) {
		OrgInfo orgInfo=BeanCopyUtil.copy(orgInfoVO, OrgInfo.class);
        List<OrgInfo> childorgInfoMapper = orgInfoMapper.queryChildOrgInfo(orgInfo);
        Map<String, OrgInfo> map = new HashMap<>();
        OrgInfo rootOrg = null;
        
        childorgInfoMapper.forEach(org -> map.put(org.getId(), org));
        
        for (OrgInfo org : childorgInfoMapper) {
        	OrgInfo parent = map.get(org.getParentId());
			if (parent != null) 
				parent.addChild(org);
			else 
				rootOrg = org;
		}	
	   return BeanCopyUtil.copy(rootOrg, OrgInfoVO.class);
	}
	
	/**
	 * 组织机构信息修改
	 */
	@Override
	public Integer saveOrgInfo(OrgInfo OrgInfo) {
		int i = orgInfoMapper.updateOrgInfo(OrgInfo);
		return i;	
	}
	
	/**
	 * 组织机构信息查詢
	 */
	@Override
	public OrgInfo getOrgInfo(OrgInfo orgInfo) {
		OrgInfo selectOrgInfo = orgInfoMapper.selectOrgInfo(orgInfo);
		return selectOrgInfo;
	}
	
	/**
	  * 根据当前用户的组织id，查询出当前用户及当前用户一下的组织
	  * 物料综合查询用
	  * @return
	  */
	@Override
	public List<OrgInfo> getMaterielOrginfo(AcctInfo acctInfo) {
		//根据当前用户id查出当前用户绑定的组织
		String acctInfoId = acctInfoMapper.getAcctInfo(acctInfo.getAcctId());
		List<OrgInfo> listOrgInfo= orgInfoMapper.getMaterielOrginfo(acctInfoId);
		return listOrgInfo;
	}

	/**
	 * 根据当前用户的组织id，查询出当前用户及当前用户一下的组织
	 * 物料综合查询用
	 * @return
	 */
	@Override
	public List<OrgInfoVO> selectSysQuoteOrgInof(SysInfoVO acctInfo) {
		SysInfo sysInfo= BeanCopyUtil.copy(acctInfo, SysInfo.class);
		List<OrgInfo> listOrgInfo= orgInfoMapper.selectSysQuoteOrgInof(sysInfo);
		return BeanCopyUtil.copyList(listOrgInfo, OrgInfoVO.class);
	}

	/**
	 * 角色引用多个组织 角色已引用组织
	 * @return
	 */
	@Override
	public List<OrgInfoVO> selectRoleQuoteOrg(OrgInfoVO acctInfo) {
		OrgInfo orgInfo=BeanCopyUtil.copy(acctInfo, OrgInfo.class);
		List<OrgInfo> listOrgInfo= orgInfoMapper.selectRoleQuoteOrg(orgInfo);
		//转换
		List<OrgInfoVO> roleInfoVo= BeanCopyUtil.copyList(listOrgInfo, OrgInfoVO.class);
		return roleInfoVo;
	}
	
	/**
	 * 组织名称唯一性校验
	 */
	@Override
	public Boolean getParityOrgName(OrgInfo orgInfo) {
		Integer i = orgInfoMapper.selectParityOrgName(orgInfo);
		//返回0说明当前组织名称没有被使用
		if(i == 0) {
			return true;
		//不等于0说明当前用户已存在
		}else {
			return false;
		}
	}
	
	
	

	/**
	 * 组织机构信息查詢分页
	 */
	@Override
	public PageInfo<OrgInfoVO> getListOrgInfo(OrgInfoVO orgInfoVO) {
		OrgInfoVO reqBody = new OrgInfoVO();

		OrgInfo orgInfo = new OrgInfo();
		BeanCopyUtil.copy(orgInfoVO, orgInfo);
		//可能还有更多参数
		PageHelper.startPage(orgInfoVO.getPageNum(), orgInfoVO.getPageSize());
		List<OrgInfo> list = orgInfoMapper.selectListOrgInfo(orgInfo);
		PageInfo<OrgInfo> pageInfo = new PageInfo<>(list);

		//转换
		List<OrgInfoVO> roleInfoVo= BeanCopyUtil.copyList(list, OrgInfoVO.class);
		PageInfo<OrgInfoVO> pageVo = new PageInfo(roleInfoVo);
		pageVo.setTotal(pageInfo.getTotal());
		pageVo.setPages(pageInfo.getPages());
		pageVo.setPageNum(pageInfo.getPageNum());
		pageVo.setPageSize(pageInfo.getPageSize());
		return pageVo;

	}

	/**
	 * 角色未引用并且可以引用的组织
	 */
	@Override
	public PageInfo<OrgInfoVO> selectRoleYesQuote(OrgInfoVO orgInfoVO) {

		OrgInfo orgInfo = new OrgInfo();
		BeanCopyUtil.copy(orgInfoVO, orgInfo);
		PageHelper.startPage(orgInfoVO.getPageNum(), orgInfoVO.getPageSize());
		List<OrgInfo> list = orgInfoMapper.selectRoleYesQuote(orgInfo);
		PageInfo<OrgInfo> pageInfo = new PageInfo<>(list);
		//转换
		List<OrgInfoVO> roleInfoVo= BeanCopyUtil.copyList(list, OrgInfoVO.class);
		PageInfo<OrgInfoVO> pageVo = new PageInfo(roleInfoVo);
		pageVo.setTotal(pageInfo.getTotal());
		pageVo.setPages(pageInfo.getPages());
		pageVo.setPageNum(pageInfo.getPageNum());
		pageVo.setPageSize(pageInfo.getPageSize());
		return pageVo;
	}


	/**
	 * 组织机构信息递归查询
	 */
	@Override
	public OrgInfo getPwerTreeOrgInfo(OrgInfo orgInfo) {

		List<OrgInfo> childorgInfoMapper = orgInfoMapper.getPwerTreeOrgInfo(orgInfo);
		Map<String, OrgInfo> map = new HashMap<>();
		OrgInfo rootOrg = null;

		childorgInfoMapper.forEach(org -> map.put(org.getId(), org));

		for (OrgInfo org : childorgInfoMapper) {
			OrgInfo parent = map.get(org.getParentId());
			if (parent != null)
				parent.addChild(org);
			else
				rootOrg = org;
		}
		return rootOrg;
	}

    /**
     * 组织机构信息递归查询
     */
    @Override
    public OrgInfo getPwerTreeRoleInfo(OrgInfo orgInfo) {

        List<OrgInfo> childorgInfoMapper = orgInfoMapper.getPwerTreeRoleInfo(orgInfo);
        Map<String, OrgInfo> map = new HashMap<>();
        OrgInfo rootOrg = null;

        childorgInfoMapper.forEach(org -> map.put(org.getId(), org));

        for (OrgInfo org : childorgInfoMapper) {
            OrgInfo parent = map.get(org.getParentId());
            if(!CollectionUtils.isEmpty(org.getRoleInfos())) {
                for (int i = 0; i < org.getRoleInfos().size(); i++) {
                    OrgInfo o = new OrgInfo();
                    o.setType(0);
                    o.setRoleTitle(org.getRoleInfos().get(i).getRoleTitle());
                    //o.setOrgName(org.getRoleInfos().get(i).getRoleTitle());
                    o.setId(org.getRoleInfos().get(i).getRoleId().toString());
                    org.getChildren().add(o);
                }
            }
            org.setRoleInfos(null);
            if (parent != null) {
				parent.setRoleInfos(org.getRoleInfos());
				parent.addChild(org);
			}else {
				rootOrg = org;
			}
        }
        return rootOrg;
    }

    /**
     * 组织机构信息递归查询
     */
    @Override
    public OrgInfo getPwerTreeAcctInfo(OrgInfo orgInfo) {

        List<OrgInfo> childorgInfoMapper = orgInfoMapper.getPwerTreeAcctInfo(orgInfo);

        Map<String, OrgInfo> map = new HashMap<>();
        OrgInfo rootOrg = null;

        childorgInfoMapper.forEach(org -> map.put(org.getId(), org));

        for (OrgInfo org : childorgInfoMapper) {
            OrgInfo parent = map.get(org.getParentId());
            if(!CollectionUtils.isEmpty(org.getAcctInfos())) {
                for(int i=0;i<org.getAcctInfos().size();i++){
                    OrgInfo o=new OrgInfo();
                    o.setType(0);
                    o.setAcctTitle(org.getAcctInfos().get(i).getAcctTitle());
                    //o.setOrgName(org.getAcctInfos().get(i).getAcctTitle());
                    o.setId(org.getAcctInfos().get(i).getAcctId().toString());
                    org.getChildren().add(o);
                }
            }
            org.setAcctInfos(null);
            if (parent != null) {
                parent.addChild(org);
			}else {

				rootOrg = org;
			}

        }
        return rootOrg;
    }
}


