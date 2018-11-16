package com.ebase.report.service;


import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.model.RptAnalyseLog;
import com.ebase.report.vo.RptAnalyseLogVO;

import java.util.List;
import java.util.Set;

//
public interface RptAnalyseLogService {

    /**
     * 插入一条sql执行日志
     * @param rptAnalyseLog
     * @return
     */
    int addReportLog(RptAnalyseLog  rptAnalyseLog);

    /**
     * 当前操作人最新执行过的sql
     * @return
     */
    RptAnalyseLog getReportSql();



    /**
     * 条件查询
     *
     * @param record
     * @return List
     */
    public PageDTO<RptAnalyseLogVO> findSelective(RptAnalyseLogVO record);

    /**
     * 查询一条
     *
     * @param key
     * @return VO
     */
    public RptAnalyseLogVO getByPrimaryKey(Long key);

    /**
     * 增加
     *
     * @param record
     * @return Integer
     */
    public Integer insert(RptAnalyseLogVO record);

    /**
     * 非空增加
     *
     * @param record
     * @return Integer
     */
    public Integer insertSelective(RptAnalyseLogVO record);

    /**
     * 根据主键更新
     *
     * @param record
     * @return Integer
     */
    public Integer updateByPrimaryKey(RptAnalyseLogVO record);

    /**
     * 根据主键非空更新
     *
     * @param record
     * @return Integer
     */
    public Integer updateByPrimaryKeySelective(RptAnalyseLogVO record);

    /**
     * 根据主键删除
     *
     * @param key
     * @return Integer
     */
    public Integer deleteByPrimaryKey(Long key);

    /**
     * 获得操作日志列表
     * @param vo
     * @return
     */
//    PageDTO<RptAnalyseLogBody> listAnalyseLog(RptAnalyseLogBody vo);
}
