package com.ebase.report.service;

import com.ebase.report.cube.CubeTree;
import com.ebase.report.model.RptDataField;
import com.ebase.report.model.RptMeasures;
import com.ebase.report.model.RptPersonalAnalysis;
import com.ebase.report.model.dynamic.ReportDynamicParam;
import com.ebase.report.model.dynamic.ReportEchoBody;
import com.ebase.report.vo.ReportQueryParam;

import java.util.List;
import java.util.Map;

public interface ReportService {

    /**
     * 获得主题 和 数据源信息
     * @param
     * @return
     */
    List<ReportEchoBody> getThemeDataSource(Long createdBy);

    /**
     * 获得主题数据
     * @param personalSubjectId
     * @return
     */
    ReportEchoBody getReportEchoBody(Long personalSubjectId);

    /**
     * 保存自定义指标
     * @param reqBody
     * @return
     */
    Long addRptMeasures(RptMeasures reqBody);

    /**
     * 保存自定义报表
     * @param reqBody
     * @return
     */
    Long addCustomReport(RptPersonalAnalysis reqBody);

    /**
     * 查询自定义报表
     * @param personalAnalysisId
     * @return
     */
    RptPersonalAnalysis getCustomReport(Long personalAnalysisId);

    /**
     * 动态报表核心类
     * @param reportDynamicParam
     * @return
     */
    CubeTree reportCore(ReportDynamicParam reportDynamicParam);

    /**
     * 获得所有 能添加的指标指标
     * @param
     * @return
     */
    List<RptDataField> getRptDataField(Long personalSubjectId);

    /**
     * 查询 主题下所有的 已经添加的指标
     * @param personalSubjectId
     * @return
     */
    List<RptMeasures> getRptMeasuresSystem(Long personalSubjectId);

    /**
     * 根据主题ID获得单个主题对象
     * @param personalSubjectId
     * @return
     */
    List<ReportEchoBody> getThemeDataSourceByPersonalSubjectId(Long personalSubjectId);
}
