package com.epam.esm.response.entities;

public class MyResponseBody {

	private String message;
	private int code;

	public MyResponseBody(String message, int code) {
		this.message = message;
		this.code = code;
	}

	public MyResponseBody() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
