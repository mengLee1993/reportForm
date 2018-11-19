package com.ebase.report.service.jurisdiction;

import java.util.List;

import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.vo.jurisdiction.OrgInfoVO;
import com.ebase.report.vo.jurisdiction.SysInfoVO;
import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.model.jurisdiction.OrgInfo;

/**
 * 
 * @author zhangx
 *
 */
public interface OrgInfoService {
	
	Long addOrgInfo(OrgInfo orgInfo);
	
	Integer removeOrgInfo(OrgInfo orgInfo);
	
	List<OrgInfo> getListTreeOrgInfo(OrgInfo orgInfo);

	OrgInfoVO getChildTreeOrgInfo(OrgInfoVO orgInfo);

	OrgInfo getPwerTreeOrgInfo(OrgInfo orgInfo);

    OrgInfo getPwerTreeRoleInfo(OrgInfo orgInfo);

    OrgInfo getPwerTreeAcctInfo(OrgInfo orgInfo);
	
	Integer saveOrgInfo(OrgInfo orgInfo);
	
	OrgInfo getOrgInfo(OrgInfo orgInfo);

	PageInfo<OrgInfoVO> getListOrgInfo(OrgInfoVO orgInfo);

	PageInfo<OrgInfoVO> selectRoleYesQuote(OrgInfoVO orgInfo);

	List<OrgInfo> getMaterielOrginfo(AcctInfo acctInfo);

	List<OrgInfoVO> selectSysQuoteOrgInof(SysInfoVO acctInfo);

	List<OrgInfoVO> selectRoleQuoteOrg(OrgInfoVO acctInfo);

	Boolean getParityOrgName(OrgInfo orgInfo);

	
	

	

	



	
	

}
