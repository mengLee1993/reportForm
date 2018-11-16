package com.ebase.report.core;
/**
 * <p> 异常类 </p> 
 *
 * @project 	core-api
 * @class 		BusinessException
 */
public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
     * 异常代码
     */
    private String errorCode;

    /**
     * i18n错误信息中的参数
     */
    protected Object[] errorArgs = null;

    public BusinessException(String exceptionCode) {
        this.errorCode = exceptionCode;
    }

    public BusinessException(String exceptionCode, Object[] errorArgs) {
        this.errorCode = exceptionCode;
        this.errorArgs = errorArgs;
    }

    public BusinessException(String exceptionCode, Object[] errorArgs, Throwable cause) {
        super(cause);
        this.errorCode = exceptionCode;
        this.errorArgs = errorArgs;
    }
    
    public String getErrorCode() {
        return this.errorCode;
    }

	public Object[] getErrorArgs() {
		return errorArgs;
	}
    
	@Override
	public String getMessage() {
		return I18nResource.getException(errorCode, errorArgs);
	}
}
