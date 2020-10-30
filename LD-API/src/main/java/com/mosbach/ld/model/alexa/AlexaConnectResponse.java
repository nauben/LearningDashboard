package com.mosbach.ld.model.alexa;

public class AlexaConnectResponse {

	private boolean success;
	private String error;
	public AlexaConnectResponse() {
		super();
	}
	public AlexaConnectResponse(boolean success, String error) {
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
