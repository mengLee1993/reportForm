package com.ebase.report.model.dynamic;

import com.ebase.report.common.FilterTypeEnum;

import java.util.Map;

public class FilterAreaValue {

//    private FilterTypeEnum filterTypeEnum ;  //过滤类型

    private Integer isChecked;  //0 否    1 是

//    private Integer isCHeckeds;



    private Map<FilterTypeEnum,String> fieldVal; //value值     多个值 是 ## 分割

    public Map<FilterTypeEnum, String> getFieldVal() {
        return fieldVal;
    }

    public void setFieldVal(Map<FilterTypeEnum, String> fieldVal) {
        this.fieldVal = fieldVal;
    }


//    public Integer getIsCHeckeds() {
//        return isCHeckeds;
//    }
//
//    public void setIsCHeckeds(Integer isCHeckeds) {
//        this.isCHeckeds = isCHeckeds;
//    }


    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }
}
