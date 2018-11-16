package com.ebase.report.model;

import com.ebase.report.model.dynamic.CustomIndex;

import java.util.List;
import java.util.Set;

//自定义指标时候的变量
public class RptMeasuresBody {

    private String expressionChinese;

    private String expressionEnglish;

    private Set<CustomIndex> customIndexTmp; // 多个字段

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

    public Set<CustomIndex> getCustomIndexTmp() {
        return customIndexTmp;
    }

    public void setCustomIndexTmp(Set<CustomIndex> customIndexTmp) {
        this.customIndexTmp = customIndexTmp;
    }
}
