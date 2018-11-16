package com.ebase.report.core.json;

import com.ebase.report.core.I18nResource;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class JsonResponse<T> implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public static final String PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String SUCCESS = "0000000";
	
	public static final String SYS_EXCEPTION = "500";
	public static final String SYS_ERROR_MSG = "系统异常";
	
	private String retCode = SUCCESS;
	private String retDesc;
	private String timestamp;
	private T rspBody;

	public JsonResponse() {
		this(null);
	}

	public JsonResponse(T value) {
		this(SUCCESS, "操作成功!", value);
	}

	public JsonResponse(String retCode, String retDesc, T rspBody) {
		this(retCode, retDesc, rspBody, new SimpleDateFormat(PATTERN).format(new Date()));
	}

	public JsonResponse(String retCode, String retDesc) {
		this(retCode, retDesc, null);
	}

	public JsonResponse(String retCode, String retDesc, T rspBody, String timestamp) {
		this.retCode = retCode;
		this.retDesc = retDesc;
		this.rspBody = rspBody;
		this.timestamp = timestamp;
	}

	public void setTimestamp(Date date) {
		this.timestamp = date == null ? this.timestamp : new SimpleDateFormat(PATTERN).format(date);
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		if (SYS_EXCEPTION.equals(retCode))
			this.retDesc = SYS_ERROR_MSG;
		else if (!SUCCESS.equals(retCode))
			this.retDesc = I18nResource.getMessage(retCode);
		this.retCode = retCode;
	}

	public String getRetDesc() {
		return retDesc;
	}

	public void setRetDesc(String retDesc) {
		this.retDesc = retDesc;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public T getRspBody() {
		return rspBody;
	}

	public void setRspBody(T rspBody) {
		this.rspBody = rspBody;
	}

}
