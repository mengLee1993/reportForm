package com.ebase.report.vo.jurisdiction;

import com.ebase.report.common.Status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author zhangx
 *		组织机构表
 */
public class OrgInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String id2;

	
	public String getId2() {
		return id2;
	}

	public void setId2(String id2) {
		this.id2 = id2;
	}
	/**
	 * 上级机构
	 */
	private String parentId;

	private Long roleId;

	private String orgId;

	private Long sysId;

	private Integer type;

	private String acctTitle;

	private String roleTitle;

	private List<com.ebase.report.vo.jurisdiction.RoleInfoVO> roleInfos;

	private List<com.ebase.report.vo.jurisdiction.AcctInfoVO> acctInfos;

	public String getAcctTitle() {
		return acctTitle;
	}

	public void setAcctTitle(String acctTitle) {
		this.acctTitle = acctTitle;
	}

	public String getRoleTitle() {
		return roleTitle;
	}

	public void setRoleTitle(String roleTitle) {
		this.roleTitle = roleTitle;
	}

	public List<com.ebase.report.vo.jurisdiction.RoleInfoVO> getRoleInfos() {
		return roleInfos;
	}

	public void setRoleInfos(List<com.ebase.report.vo.jurisdiction.RoleInfoVO> roleInfos) {
		this.roleInfos = roleInfos;
	}

	public List<com.ebase.report.vo.jurisdiction.AcctInfoVO> getAcctInfos() {
		return acctInfos;
	}

	public void setAcctInfos(List<com.ebase.report.vo.jurisdiction.AcctInfoVO> acctInfos) {
		this.acctInfos = acctInfos;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 机构代码
	 */
	private String orgCode;
	
	/**
	 * 机构名称
	 */
	private String orgName;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 0:停用;1:启用
	 */
	private Status status;
	
	/**
	 * 创建人
	 */
	private String createdBy;
	
	/**
	 * 创建时间
	 */
	private Date createdTime;
	
	/**
	 * 修改人
	 */
	private String updatedBy;
	
	/**
	 * 修改时间
	 */
	private Date updatedTime;

	private OrgInfoVO parent;

	private String[] orgIds;

	public String[] getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String[] orgIds) {
		this.orgIds = orgIds;
	}

	public OrgInfoVO getParent() {
		return parent;
	}

	public void setParent(OrgInfoVO parent) {
		this.parent = parent;
	}

	private List<OrgInfoVO> children = new ArrayList<OrgInfoVO>();
	
	public List<OrgInfoVO> getChildren() {
		return children;
	}

	public void setChildren(List<OrgInfoVO> children) {
		this.children = children;
	}

	private Integer pageSize = 10;
	
	private Integer pageNum =1;
	
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;	
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}



	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		if (this.status != null)
			return status.getCode();
		return "";
	}

	public void setStatus(String code) {
		this.status = Status.getStatus(code);
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	

}
