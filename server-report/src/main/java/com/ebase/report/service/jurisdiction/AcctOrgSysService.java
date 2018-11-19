package com.ebase.report.service.jurisdiction;

import java.util.List;
import java.util.Set;
import com.ebase.report.vo.jurisdiction.AcctOrgSysVO;

/**
 * dal Interface:AcctOrgSys
 * @author 
 * @date 2018-10-10
 */
public interface AcctOrgSysService {

	/**
	 * 查询全部
	 * 
	 * @param
	 * @return List
	 */
    public List<AcctOrgSysVO> selectAll();

	/**
	 * 条件查询
	 * 
	 * @param record
	 * @return List
	 */
    public List<AcctOrgSysVO> select(AcctOrgSysVO record);

	/**
	 * 查询一条
	 * 
	 * @param key
	 * @return VO
	 */
    public AcctOrgSysVO selectByPrimaryKey(Long key);

	/**
	 * 增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insert(AcctOrgSysVO record);
    
    /**
	 * 非空增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insertSelective(AcctOrgSysVO record);

	/**
	 * 非空增加
	 *
	 * @param record
	 * @return Integer
	 */
	public Integer saveOrgInfo(AcctOrgSysVO record);
	
	/**
	 * 根据主键更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKey(AcctOrgSysVO record);
    
    /**
	 * 根据主键非空更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKeySelective(AcctOrgSysVO record);

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
    public Integer keep(List<AcctOrgSysVO> acctOrgSysVOs);

}