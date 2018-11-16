package com.ebase.report.common;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.Map;

/**
 * 度量类型
 */
public enum MeasureTypeEnum {


    SUM("SUM","总数"),
    COUNT("COUNT","求和"),
    AVG("AVG","平均值"),
    MAX("MAX","最大"),
    MIN("MIN","最小"),
    CUSTOM("CUSTOM","自定义");

    private String code;
    private String name;

    MeasureTypeEnum(String code, String name){
        this.code = code.toUpperCase();
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(DemandType type){
        if(type == null){
            return false;
        }

        return this.getCode().equals(type.getCode());
    }

    public String getMeasureType(){
        return this.getCode();
    }


    private static Map<String,MeasureTypeEnum> tmp = new HashMap<>();

    public static MeasureTypeEnum getMeasureTypeEnumByCode(String code){
        code = code.toUpperCase();
        if(tmp.get(code) == null){
            for(MeasureTypeEnum measureTypeEnum:MeasureTypeEnum.values()){
                tmp.put(measureTypeEnum.getCode(),measureTypeEnum);
            }
        }
        return tmp.get(code);
    }
}
