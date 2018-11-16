package com.ebase.report.core.json;

public final class JsonRequest<T> implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private JsonHeader reqHeader;
	private T reqBody;

	public JsonHeader getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(JsonHeader reqHeader) {
		this.reqHeader = reqHeader;
	}

	public T getReqBody() {
		return reqBody;
	}

	public void setReqBody(T reqBody) {
		this.reqBody = reqBody;
	}

}
