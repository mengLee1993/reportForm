package com.ebase.report.model.dynamic;

import com.ebase.report.common.MeasureTypeEnum;

import java.util.Objects;

public class CustomIndex {

    private String fieldId;

    private String fieldCode;  //SUM(age),COUNT(name)

    private MeasureTypeEnum measureTypeEnum = MeasureTypeEnum.COUNT; //指标类型

    private String measuresName;

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getMeasuresName() {
        return measuresName;
    }

    public void setMeasuresName(String measuresName) {
        this.measuresName = measuresName;
    }

    public MeasureTypeEnum getMeasureTypeEnum() {
        return measureTypeEnum;
    }

    public void setMeasureTypeEnum(MeasureTypeEnum measureTypeEnum) {
        this.measureTypeEnum = measureTypeEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomIndex that = (CustomIndex) o;
        return Objects.equals(fieldId, that.fieldId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldId);
    }
}
