package com.ebase.report.service.jurisdiction;

import java.util.List;
import java.util.Set;

import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.vo.jurisdiction.SysInfoVO;

/**
 * dal Interface:SysInfo
 * @author 
 * @date 2018-10-10
 */
public interface SysInfoService {

	/**
	 * 查询全部
	 * 
	 * @param
	 * @return List
	 */
    public List<SysInfoVO> selectAll();

	/**
	 * 条件查询
	 * 
	 * @param record
	 * @return List
	 */
    public PageInfo<SysInfoVO> select(SysInfoVO record);

	/**
	 * 引用查询
	 *
	 * @param record
	 * @return List
	 */
	public List<SysInfoVO> selectSysInfoOrg(SysInfoVO record);


	/**
	 * 根据组织查询创建系统
	 *
	 * @param record
	 * @return List
	 */
	public List<SysInfoVO> selectSysEstablish(SysInfoVO record);


	/**
	 * 组织管理员创建的系统
	 *
	 * @param record
	 * @return List
	 */
	public List<SysInfoVO> selectSysInfoOrgCreate(SysInfoVO record);

	/**
	 * 查看查询
	 *
	 * @param record
	 * @return List
	 */
	public List<SysInfoVO> selectSysInfoOrgSee(SysInfoVO record);

	/**
	 * 查询一条
	 * 
	 * @param key
	 * @return VO
	 */
    public SysInfoVO selectByPrimaryKey(Long key);

	/**
	 * 增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insert(SysInfoVO record);
    
    /**
	 * 非空增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public SysInfoVO insertSelective(SysInfoVO record);
	
	/**
	 * 根据主键更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKey(SysInfoVO record);
    
    /**
	 * 根据主键非空更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public SysInfoVO updateByPrimaryKeySelective(SysInfoVO record);

	/**
	 * 根据主键删除
	 * 
	 * @param key
	 * @return Integer
	 */
    public SysInfoVO deleteByPrimaryKey(Long key);

	/**
	 * 删除验证是否被组织使用
	 *
	 * @param sysInfoVO
	 * @return Integer
	 */
	public String verSysInfo(SysInfoVO sysInfoVO);

	/**
	 * 删除验证是否被组织使用
	 *
	 * @param sysInfoVO
	 * @return Integer
	 */
	public String verInsertSysInfo(SysInfoVO sysInfoVO);
	
	/**
	 * 根据主键批量删除
	 * 
	 * @param keys
	 * @return Integer
	 */
    public Integer deleteByPrimaryKeys(Set<Long> keys);
    
    /**
     * 批量 保存、修改、删除
     * @param meMaterielTypeVOs
     * @return
     */
    public SysInfoVO keep(List<SysInfoVO> sysInfoVOs);

}