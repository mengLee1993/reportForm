package com.ebase.report.common;


// 主题来源
public enum SubjectSourceEnum {


    share("share","分享"),
    personal("personal","自己创建");

    private String code;

    private String name;

    SubjectSourceEnum(String code,String name){
        this.code = code;
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
}
