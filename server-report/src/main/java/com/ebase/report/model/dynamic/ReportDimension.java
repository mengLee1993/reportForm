package com.ebase.report.model.dynamic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 多个维度
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDimension {

    private String fieldValue;

    private Byte isChecked = 1;  //1 选中 0 未选中



    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Byte getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Byte isChecked) {
        this.isChecked = isChecked;
    }
}
