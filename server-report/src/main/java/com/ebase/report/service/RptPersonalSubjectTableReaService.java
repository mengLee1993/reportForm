package com.ebase.report.service;

import com.ebase.report.vo.RptPersonalSubjectTableReaVO;

import java.util.List;
import java.util.Set;

/**
 * dal Interface:RptPersonalSubjectTableRea
 * @author 
 * @date 2018-10-25
 */
public interface RptPersonalSubjectTableReaService {

	/**
	 * 查询全部
	 * 
	 * @param
	 * @return List
	 */
    public List<RptPersonalSubjectTableReaVO> findAll();

	/**
	 * 条件查询
	 * 
	 * @param record
	 * @return List
	 */
    public List<RptPersonalSubjectTableReaVO> findSelective(RptPersonalSubjectTableReaVO record);

	/**
	 * 查询一条
	 * 
	 * @param key
	 * @return VO
	 */
    public RptPersonalSubjectTableReaVO getByPrimaryKey(Long key);

	/**
	 * 增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insert(RptPersonalSubjectTableReaVO record);
    
    /**
	 * 非空增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insertSelective(RptPersonalSubjectTableReaVO record);
	
	/**
	 * 根据主键更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKey(RptPersonalSubjectTableReaVO record);
    
    /**
	 * 根据主键非空更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKeySelective(RptPersonalSubjectTableReaVO record);

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
    //public Integer keep(List<RptPersonalSubjectTableReaVO> rptPersonalSubjectTableReaVOs);

}