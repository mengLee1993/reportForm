package com.ebase.report.model.dynamic;

import com.ebase.report.common.MeasureTypeEnum;
import com.ebase.report.cube.Dimension;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 度量对象
 */
@JsonIgnoreProperties(value = {"key"},ignoreUnknown = true)
public class ReportMeasure extends Dimension {

    private String expressionChinese;

    private String expressionEnglish;

    //指标才有  第一级的时候用
    // !度量值
    private MeasureTypeEnum measureType = MeasureTypeEnum.COUNT;   //如果是指标 是什么指标

    private Byte isChecked = 1; //  0 否  1是

    //  key: 字段id， 字段code码
    private List<ReportMeasure> customIndexTmp = new ArrayList<>();  //自定义度量值时的变量

    private ReportMeasure reportMeasure; //引用自己

    public String getKey() {
        String  suffix = "_" + measureType.getCode();
        return demandType.getPrefix() + getFieldId() + suffix;
    }


    public MeasureTypeEnum getMeasureType() {
        return measureType;
    }

    public void setMeasureType(MeasureTypeEnum measureType) {
        this.measureType = measureType;
    }

    public List<ReportMeasure> getCustomIndexTmp() {
        return customIndexTmp;
    }

    public void setCustomIndexTmp(List<ReportMeasure> customIndexTmp) {
        this.customIndexTmp = customIndexTmp;
    }

    public Byte getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Byte isChecked) {
        this.isChecked = isChecked;
    }

    public String getExpressionChinese() {
        return expressionChinese;
    }

    public void setExpressionChinese(String expressionChinese) {
        this.expressionChinese = expressionChinese;
    }

    public String getExpressionEnglish() {
        return expressionEnglish;
    }

    public void setExpressionEnglish(String expressionEnglish) {
        this.expressionEnglish = expressionEnglish;
    }

    public ReportMeasure getReportMeasure() {
        return reportMeasure;
    }

    public void setReportMeasure(ReportMeasure reportMeasure) {
        this.reportMeasure = reportMeasure;
    }


}
