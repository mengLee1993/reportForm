package com.ebase.report.model.dynamic;

import com.ebase.report.common.SubjectSourceEnum;
import com.ebase.report.model.RptDataField;
import com.ebase.report.model.RptMeasures;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表回显 body
 */
public class ReportEchoBody {

    private Long personalSubjectId;

    //数据源名称
    private String datasourceName;

    //数据库类型
    private String databaseType;

    //主题类型
    private String subjectType;

    //主题名称
    private String subjectName;

    //系统来源
    private String subjectSourceEnum;

    //多个数据表 如果主题类型 是数据  就 一个 数据表
    private List<ReportTable> reportTables;

    //这个可能是维度
    private List<RptDataField> rptDataFields;

    //度量值
    private List<RptDataField> rptDataIndexs;

    //已经添加的指标
    private List<RptMeasures> rptMeasures;

    //指标是否勾选
    private Byte isChecked;

    private ReportDynamicParam reportDynamicParam;  //前台传递参数

    public String getDatasourceName() {
        return datasourceName;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
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

    public List<RptDataField> getRptDataFields() {
        return rptDataFields;
    }

    public void setRptDataFields(List<RptDataField> rptDataFields) {
        this.rptDataFields = rptDataFields;
    }

    public List<RptMeasures> getRptMeasures() {
        return rptMeasures;
    }

    public void setRptMeasures(List<RptMeasures> rptMeasures) {
        this.rptMeasures = rptMeasures;
    }

    public List<RptDataField> getRptDataIndexs() {
        return rptDataIndexs;
    }

    public void setRptDataIndexs(List<RptDataField> rptDataIndexs) {
        this.rptDataIndexs = rptDataIndexs;
    }

    public ReportDynamicParam getReportDynamicParam() {
        return reportDynamicParam;
    }

    public void setReportDynamicParam(ReportDynamicParam reportDynamicParam) {
        this.reportDynamicParam = reportDynamicParam;
    }

    public String getSubjectSourceEnum() {
        return subjectSourceEnum;
    }

    public void setSubjectSourceEnum(String subjectSourceEnum) {
        this.subjectSourceEnum = subjectSourceEnum;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Byte getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Byte isChecked) {
        this.isChecked = isChecked;
    }
}
