package com.mosbach.ld.model.common;

public class DefaultActionResponse {

	private boolean success;
	private String error;
	
	public DefaultActionResponse() {
		super();
	}
	public DefaultActionResponse(boolean success, String error) {
		super();
		this.success = success;
		this.error = error;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}
