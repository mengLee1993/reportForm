package com.ebase.report.dao.jurisdiction;

import java.util.List;

import com.ebase.report.model.jurisdiction.OrgInfo;
import com.ebase.report.model.jurisdiction.SysInfo;
import org.apache.ibatis.annotations.Param;





public interface OrgInfoMapper {
	
	//根据parentId添加组织
	Long insertOrgInfo(OrgInfo record);

	String getOrgInfoId(String id);
	
	int deleteOrgInfo(@Param("cascadeDeletionOrgInfo") List<String> cascadeDeletionOrgInfo);
	
	List<String> getOrgInfoIdAll(OrgInfo orgInfo);
	
	List<OrgInfo> getChildOrgInfo(OrgInfo child);
	
	List<OrgInfo> selectListOrgInfoAll(OrgInfo orgInfo);
	
	List<OrgInfo> queryChildOrgInfo(OrgInfo orgInfo);

	List<OrgInfo> getPwerTreeOrgInfo(OrgInfo orgInfo);

	List<OrgInfo> getPwerTreeRoleInfo(OrgInfo orgInfo);

	List<OrgInfo> getPwerTreeAcctInfo(OrgInfo orgInfo);

	
	//根据组织id修改组织
	int updateOrgInfo(OrgInfo record);
	
	List<OrgInfo> getMaterielOrginfo(@Param("acctInfoId") String acctInfoId);

	List<OrgInfo> selectSysQuoteOrgInof(SysInfo sysInfo);

	List<OrgInfo> selectRoleQuoteOrg(OrgInfo orgInfo);

	//根据当前parentId查询组织
	OrgInfo getOrgInfo(OrgInfo orgInfo);
	
	//根据组织名称查询出组织id
	String getOrgInfoName(@Param("orgName") String orgName);

	OrgInfo selectOrgInfo(OrgInfo record);

	List<OrgInfo> selectListOrgInfo(OrgInfo reqBody);

	List<OrgInfo> selectRoleYesQuote(OrgInfo reqBody);

	Integer selectParityOrgName(OrgInfo orgInfo);
	
	
	
	
	

	
	OrgInfo selectOrgInfo1(OrgInfo record);

	OrgInfo selectOrgInfo2(OrgInfo record);

	//用户关联组织详情查询
	OrgInfo selectOrgInfoAcctInfo(OrgInfo record);


	
	



	


	

	

	

	

	

	



}
