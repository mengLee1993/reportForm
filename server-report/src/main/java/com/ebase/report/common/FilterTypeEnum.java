package com.ebase.report.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 过滤类型
 */
public enum FilterTypeEnum {

    GT((byte)1,">"),
    LT((byte)2,"<"),
    EQ((byte)3,"="),
    NE((byte)4,"!="),
    RG((byte)5,"like");


    private Byte code;
    private String name;

    FilterTypeEnum(Byte code, String name){
        this.code = code;
        this.name = name;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 是否是范围
     * @return
     */
    public static Boolean isScope(FilterTypeEnum filterTypeEnum){
        if(filterTypeEnum != null){
            if(filterTypeEnum.equals(GT) || filterTypeEnum.equals(LT)){
                return true;
            }
        }
        return false;
    }

}
