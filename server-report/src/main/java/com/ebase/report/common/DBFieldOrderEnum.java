package com.ebase.report.common;


/**
 * 字段 顺序枚举
 */
public enum DBFieldOrderEnum {

    NONE("NONE","NONE","NONE"), //不做排序

    POSITIVE("Y","正序","asc"), //正顺序

    REVERSE("N","倒序","desc"); //倒顺序

    private String code;

    private String name;

    private String order;

    DBFieldOrderEnum(String code,String name,String order){
        this.code = code;
        this.name = name;
        this.order = order;
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
