package com.ebase.report.model.jurisdiction;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 */
public class SysInfo implements Serializable {
    /**
     * Database Column Remarks:
     *   角色标识
     */
    private Long sysId;

    /**
     * Database Column Remarks:
     *   角色编码
     */
    private String sysCode;

    /**
     * Database Column Remarks:
     *   角色名称
     */
    private String sysTitle;

    /**
     * Database Column Remarks:
     *   角色描述
     */
    private String sysDesc;

    /**
     * Database Column Remarks:
     *   是否删除
     */
    private Byte isDelete;

    /**
     * Database Column Remarks:
     *   0:停用;1:启用
     */
    private Byte status;

    /**
     * 状态
     * 0创建  1 引用
     */
    private Byte relaStatus;

    /**
     * Database Column Remarks:
     *   创建人
     */
    private String createdBy;

    /**
     * Database Column Remarks:
     *   创建时间
     */
    private Date createdTime;

    /**
     * Database Column Remarks:
     *   修改人
     */
    private String updatedBy;

    /**
     * Database Column Remarks:
     *   修改时间
     */
    private Date updatedTime;

    /**
     * Database Column Remarks:
     *   组织标识
     */
    private String orgId;
    /**
     * 选择引用组织
     */
    private String quoteOrgId;

    private Long acctType;

    private Long acctId;

    private List<FunctionManage> functionManages;

    private Byte type;
    /**
     */
    private static final long serialVersionUID = 1L;

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getAcctType() {
        return acctType;
    }

    public void setAcctType(Long acctType) {
        this.acctType = acctType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getRelaStatus() {
        return relaStatus;
    }

    public void setRelaStatus(Byte relaStatus) {
        this.relaStatus = relaStatus;
    }

    public List<FunctionManage> getFunctionManages() {
        return functionManages;
    }

    public void setFunctionManages(List<FunctionManage> functionManages) {
        this.functionManages = functionManages;
    }

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode == null ? null : sysCode.trim();
    }

    public String getSysTitle() {
        return sysTitle;
    }

    public void setSysTitle(String sysTitle) {
        this.sysTitle = sysTitle == null ? null : sysTitle.trim();
    }

    public String getSysDesc() {
        return sysDesc;
    }

    public void setSysDesc(String sysDesc) {
        this.sysDesc = sysDesc == null ? null : sysDesc.trim();
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
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
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getQuoteOrgId() {
        return quoteOrgId;
    }

    public void setQuoteOrgId(String quoteOrgId) {
        this.quoteOrgId = quoteOrgId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sysId=").append(sysId);
        sb.append(", sysCode=").append(sysCode);
        sb.append(", sysTitle=").append(sysTitle);
        sb.append(", sysDesc=").append(sysDesc);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", status=").append(status);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", orgId=").append(orgId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}