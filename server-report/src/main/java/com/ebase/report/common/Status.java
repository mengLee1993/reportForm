package com.ebase.report.common;

public enum Status {


    STOP("0","停用"),
    START("1","启用");



    private String code;

    private String msg;
    Status(String code, String msg){

        this.code = code;
        this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }
    
    public static Status getStatus(String code) {
    	for (Status status : Status.values())
    		if (status.getCode().equals(code))
    			return status;
    	return null;
    }
    
}
