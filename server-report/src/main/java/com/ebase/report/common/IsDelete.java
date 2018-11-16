package com.ebase.report.common;

public enum IsDelete {


    NO("0","否"),
    YES("1","是");



    private String code;

    private String msg;
    IsDelete(String code, String msg){

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
    
    public static IsDelete getIsDelete(String code) {
    	for (IsDelete isDelete : IsDelete.values())
    		if (isDelete.getCode().equals(code))
    			return isDelete;
    	return null;
    }
    
}
