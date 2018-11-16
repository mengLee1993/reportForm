package com.ebase.report.core;

public enum ParamType {


    DELETE("delete",1),
    UPDATE("update",2),
    INSERT("insert",3);

    private String msg;

    private Integer code;


    ParamType(String mas, Integer code){
        this.msg = mas;
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}
