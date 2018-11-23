package com.ebase.report.model.dynamic;


import com.ebase.report.cube.charts.ChartOption;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 数据源对象
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDatasource {

    //数据源名称
    private String datasourceName;

    //数据库类型
    private String databaseType;

    //主题id
    private Long personalSubjectId;
    /**
     * 主题类型
     */
    private String subjectType;

    //多个数据表 如果主题类型 是数据  就 一个 数据表
    private List<ReportTable> reportTables;

//    //主题对象
//    private RptPersonalSubject rptPersonalSubject;

    private ReportDynamicParam reportDynamicParam;  //前台传递参数

    // 图形参数配置
    private ChartOption chartOptions;


    public String getDatasourceName() {
        return datasourceName;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public ReportDynamicParam getReportDynamicParam() {
        return reportDynamicParam;
    }

    public void setReportDynamicParam(ReportDynamicParam reportDynamicParam) {
        this.reportDynamicParam = reportDynamicParam;
    }

    public List<ReportTable> getReportTables() {
        return reportTables;
    }

    public void setReportTables(List<ReportTable> reportTables) {
        this.reportTables = reportTables;
    }

    public Long getPersonalSubjectId() {
        return personalSubjectId;
    }

    public void setPersonalSubjectId(Long personalSubjectId) {
        this.personalSubjectId = personalSubjectId;
    }

    public ChartOption getChartOptions() {
        return chartOptions;
    }

    public void setChartOptions(ChartOption chartOptions) {
        this.chartOptions = chartOptions;
    }
}
