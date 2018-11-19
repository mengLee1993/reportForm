package com.ebase.report.service.jurisdiction;

import com.ebase.report.vo.jurisdiction.AcctRoleOrgVO;

import java.util.List;
import java.util.Set;

/**
 * dal Interface:AcctOrgSys
 * @author 
 * @date 2018-10-10
 */
public interface AcctRoleOrgService {

	/**
	 * 查询全部
	 * 
	 * @param
	 * @return List
	 */
    public List<AcctRoleOrgVO> selectAll();

	/**
	 * 条件查询
	 * 
	 * @param record
	 * @return List
	 */
    public List<AcctRoleOrgVO> select(AcctRoleOrgVO record);

	/**
	 * 查询一条
	 * 
	 * @param key
	 * @return VO
	 */
    public AcctRoleOrgVO selectByPrimaryKey(Long key);

	/**
	 * 增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insert(AcctRoleOrgVO record);
    
    /**
	 * 非空增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insertSelective(AcctRoleOrgVO record);

	/**
	 * 非空增加
	 *
	 * @param record
	 * @return Integer
	 */
	public Integer saveOrgInfo(AcctRoleOrgVO record);
	
	/**
	 * 根据主键更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKey(AcctRoleOrgVO record);
    
    /**
	 * 根据主键非空更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKeySelective(AcctRoleOrgVO record);

	/**
	 * 根据主键删除
	 * 
	 * @param key
	 * @return Integer
	 */
    public Integer deleteByPrimaryKey(Long key);
	
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
    public Integer keep(List<AcctRoleOrgVO> acctRoleOrgVOS);

}