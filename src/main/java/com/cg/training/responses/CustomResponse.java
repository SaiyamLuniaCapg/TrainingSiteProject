package com.cg.training.responses;

public class CustomResponse {
	private String message;
	private int statusCode;
	public CustomResponse() {}
	public CustomResponse(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
	}
	public String getmessage() {
		return message;
	}
	public void setmessage(String message) {
		this.message = message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
