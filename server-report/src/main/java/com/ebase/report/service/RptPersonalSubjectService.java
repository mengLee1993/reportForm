package com.ebase.report.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ebase.report.vo.RptPersonalSubjectVO;

/**
 * dal Interface:RptPersonalSubject
 * @author 
 * @date 2018-10-25
 */
public interface RptPersonalSubjectService {

	/**
	 * 查询全部
	 * 
	 * @param
	 * @return List
	 */
    public List<RptPersonalSubjectVO> findAll();

	/**
	 * 条件查询
	 * 
	 * @param record
	 * @return List
	 */
    public List<RptPersonalSubjectVO> findSelective(RptPersonalSubjectVO record);

	/**
	 * 查询一条
	 * 
	 * @param key
	 * @return VO
	 */
    public RptPersonalSubjectVO getByPrimaryKey(Long key);

	/**
	 * 增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insert(RptPersonalSubjectVO record);
    
    /**
	 * 非空增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insertSelective(RptPersonalSubjectVO record);
	
	/**
	 * 根据主键更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKey(RptPersonalSubjectVO record);
    
    /**
	 * 根据主键非空更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKeySelective(RptPersonalSubjectVO record);

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

    Integer getSubjectByTypeId(Map<String, Object> tmp);

    /**
     * 批量 保存、修改、删除
     * @param meMaterielTypeVOs
     * @return
     */
    //public Integer keep(List<RptPersonalSubjectVO> rptPersonalSubjectVOs);

}