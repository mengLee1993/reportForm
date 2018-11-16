package com.ebase.report.common;

public enum FileTypeEnum {

    TEXT("text"),
    PDF("pdf"),
    WORD("word"),
    EXCEL("excel");


    private String name;

    FileTypeEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
