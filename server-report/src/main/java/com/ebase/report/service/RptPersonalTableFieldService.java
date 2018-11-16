package com.ebase.report.service;

import com.ebase.report.vo.RptPersonalTableFieldVO;

import java.util.List;
import java.util.Set;

/**
 * dal Interface:RptPersonalTableField
 * @author 
 * @date 2018-10-25
 */
public interface RptPersonalTableFieldService {

	/**
	 * 查询全部
	 * 
	 * @param
	 * @return List
	 */
    public List<RptPersonalTableFieldVO> findAll();

	/**
	 * 条件查询
	 * 
	 * @param record
	 * @return List
	 */
    public List<RptPersonalTableFieldVO> findSelective(RptPersonalTableFieldVO record);

	/**
	 * 查询一条
	 * 
	 * @param key
	 * @return VO
	 */
    public RptPersonalTableFieldVO getByPrimaryKey(Long key);

	/**
	 * 增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insert(RptPersonalTableFieldVO record);
    
    /**
	 * 非空增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insertSelective(RptPersonalTableFieldVO record);
	
	/**
	 * 根据主键更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKey(RptPersonalTableFieldVO record);
    
    /**
	 * 根据主键非空更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKeySelective(RptPersonalTableFieldVO record);

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
  //  public Integer keep(List<RptPersonalTableFieldVO> rptPersonalTableFieldVOs);

}