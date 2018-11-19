package com.ebase.report.service.jurisdiction;

import java.util.List;
import java.util.Set;

import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.vo.jurisdiction.AcctFunctionSysVO;

/**
 * dal Interface:AcctFunctionSys
 * @author 
 * @date 2018-10-10
 */
public interface AcctFunctionSysService {

	/**
	 * 查询全部
	 * 
	 * @param
	 * @return List
	 */
    public List<AcctFunctionSysVO> selectAll();

	/**
	 * 条件查询
	 * 
	 * @param record
	 * @return List
	 */
    public PageInfo<AcctFunctionSysVO> select(AcctFunctionSysVO record);

	/**
	 * 查询一条
	 * 
	 * @param key
	 * @return VO
	 */
    public AcctFunctionSysVO selectByPrimaryKey(Long key);

	/**
	 * 增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insert(AcctFunctionSysVO record);
    
    /**
	 * 非空增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insertSelective(AcctFunctionSysVO record);
	
	/**
	 * 根据主键更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKey(AcctFunctionSysVO record);
    
    /**
	 * 根据主键非空更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKeySelective(AcctFunctionSysVO record);

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
     * @param acctFunctionSysVOs
     * @return
     */
    public Integer keep(List<AcctFunctionSysVO> acctFunctionSysVOs);

}