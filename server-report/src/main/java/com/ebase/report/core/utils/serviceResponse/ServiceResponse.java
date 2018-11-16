package com.ebase.report.core.utils.serviceResponse;

import com.alibaba.fastjson.JSON;
import com.ebase.report.core.I18nResource;
import com.ebase.report.core.zk.StringHelper;

import java.io.Serializable;

/**
 * 微服务层统一返回对象
 * @author Kim
 *
 * @param <T>
 */
public class ServiceResponse<T> implements Serializable {
	private static final long serialVersionUID = -6495911812654393168L;
	public static final String SUCCESS_CODE = "0";
	public static final String FAIL_CODE="1";
	public static final String SUCCESS_CODE_TEMP = "1000";
	public static final String SUCCESS_MESSAGE = "OK";
	private String retCode = SUCCESS_CODE;
	private String retMessage = SUCCESS_MESSAGE;
	private boolean hasError = false;
	private Exception exception = null;
	private T retContent;
	
	public boolean isHasError() {
		return hasError;
	}

	public String getErrorMessage() {
		if (this.hasError)
			return this.exception.getMessage();
		return "";
	}

	public void setException(Exception exception) {
		if (exception != null)
			this.hasError = true;
		this.exception = exception;
	}

	public String getRetCode() {
		return this.retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
		
	}

	public void setResponseCode(String retCode){
		if(StringHelper.isNotEmpty(retCode)){
			this.setRetCode(retCode);
			if(!isSuccessful())
				this.retMessage = I18nResource.getMessage(this.retCode);
		}
	}

	
	public void setRetCode(String retCode,Object [] args){
		this.retCode = retCode;
		this.retMessage = I18nResource.getMessage(this.retCode,args);
	}

	public String getRetMessage() {
		return this.retMessage;
	}

	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}

	public T getRetContent() {
		return this.retContent;
	}

	public void setRetContent(T retContent) {
		this.retContent = retContent;
	}

	public boolean isSuccessful() {
		return "0".equals(getRetCode());
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
