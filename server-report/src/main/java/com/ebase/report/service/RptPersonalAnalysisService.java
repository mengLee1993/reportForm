package com.ebase.report.service;

import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.model.AnalysisShareBody;
import com.ebase.report.model.RptPersonalAnalysis;
import com.ebase.report.model.dynamic.ReportEchoBody;
import com.ebase.report.model.dynamic.ReportShareBody;
import com.ebase.report.model.dynamic.ReportTable;
import com.ebase.report.vo.RptPersonalAnalysisVO;

import java.util.List;
import java.util.Set;

public interface RptPersonalAnalysisService {

    /**
     * 根据报表ID 获得主题对象
     * @param personalAnalysisId
     * @return
     */
    List<ReportEchoBody> getPersonalSubject(Long personalAnalysisId);


    /**
     * 条件查询
     *
     * @param record
     * @return List
     */
    public PageDTO<RptPersonalAnalysisVO> findSelective(RptPersonalAnalysisVO record);

    /**
     * 查询一条
     *
     * @param key
     * @return VO
     */
    public RptPersonalAnalysisVO getByPrimaryKey(Long key);

    /**
     * 增加
     *
     * @param record
     * @return Integer
     */
    public Integer insert(RptPersonalAnalysisVO record);

    /**
     * 非空增加
     *
     * @param record
     * @return Integer
     */
    public Integer insertSelective(RptPersonalAnalysisVO record);

    /**
     * 根据主键更新
     *
     * @param record
     * @return Integer
     */
    public Integer updateByPrimaryKey(RptPersonalAnalysisVO record);

    /**
     * 根据主键非空更新
     *
     * @param record
     * @return Integer
     */
    public Integer updateByPrimaryKeySelective(RptPersonalAnalysisVO record);

    /**
     * 根据主键删除
     *
     * @param key
     * @return Integer
     */
    public Integer deleteByPrimaryKey(RptPersonalAnalysisVO vo);


    List<RptPersonalAnalysisVO>  getByUserId(String userId);

    /**
     * 个人主题数据 - 我的数据报表
     * @param reqBody
     * @return
     */
    PageDTO<ReportTable> listReportForm(ReportTable reqBody);

    /**
     * 获得所有的 账号
     * @param reportShareBody
     * @return
     */
    Boolean addShareReport(ReportShareBody reportShareBody);

    /**
     * 根据报表名称获得报表数量
     * @param reportName
     * @return
     */
    Integer getReportByName(String reportName, String acctId);

    /**
     * 查看分享列表
     * @param analysisShareBody
     * @return
     */
    PageDTO<AnalysisShareBody> listAnalysisShareBody(AnalysisShareBody analysisShareBody);

    RptPersonalAnalysis getByUserId(String loginId, Long sysId);

    RptPersonalAnalysis getByRoleId(String roleId, Long sysId);

    List<String> getUserIdById(Long sysId);

    List<String> getRoleIdById(Long sysId);
}
