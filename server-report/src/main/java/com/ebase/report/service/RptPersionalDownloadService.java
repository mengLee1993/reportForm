package com.ebase.report.service;

import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.vo.RptPersionalDownloadVO;

import java.util.List;
import java.util.Set;

/**
 * dal Interface:RptPersionalDownload
 * @author 
 * @date 2018-11-1
 */
public interface RptPersionalDownloadService {


	/**
	 * 条件查询
	 * 
	 * @param record
	 * @return List
	 */
    public PageDTO<RptPersionalDownloadVO> findSelective(RptPersionalDownloadVO record);

	/**
	 * 查询一条
	 * 
	 * @param key
	 * @return VO
	 */
    public RptPersionalDownloadVO getByPrimaryKey(Long key);

	/**
	 * 增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insert(RptPersionalDownloadVO record);
    
    /**
	 * 非空增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insertSelective(RptPersionalDownloadVO record);
	
	/**
	 * 根据主键更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKey(RptPersionalDownloadVO record);
    
    /**
	 * 根据主键非空更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKeySelective(RptPersionalDownloadVO record);

	/**
	 * 根据主键删除
	 * 
	 * @param key
	 * @return Integer
	 */
    public Integer deleteByPrimaryKey(Long key);

	/**
	 * 根据path 从服务器读取文件
	 * @param filePath
	 * @return
	 */
	Boolean downloadReportFile(String filePath);
}