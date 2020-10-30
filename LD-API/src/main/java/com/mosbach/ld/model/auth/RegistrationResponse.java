package com.mosbach.ld.model.auth;

public class RegistrationResponse {

	private boolean success;
	private String error;
	
	public RegistrationResponse() {
		super();
	}
	
	public RegistrationResponse(boolean success, String error) {
		super();
		this.setSuccess(success);
		this.error = error;
	}
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
