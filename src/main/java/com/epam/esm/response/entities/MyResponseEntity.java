package com.epam.esm.response.entities;

public class MyResponseEntity {

	private int httpStatus;
	private MyResponseBody responseBody;

	public MyResponseEntity(int httpStatus, MyResponseBody responseBody) {
		this.httpStatus = httpStatus;
		this.responseBody = responseBody;
	}

	public MyResponseEntity() {
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public MyResponseBody getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(MyResponseBody responseBody) {
		this.responseBody = responseBody;
	}

}
