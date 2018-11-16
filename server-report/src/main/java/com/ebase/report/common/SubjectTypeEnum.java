package com.ebase.report.common;


import com.ebase.report.core.db.DataBaseType;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 主题类型
 */
public enum SubjectTypeEnum {


    DATATABLE("datatable","数据表"), //数据
    SUBJECT("subject","主题表");  //主题

    private String code;

    private String msg;

    SubjectTypeEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



}
