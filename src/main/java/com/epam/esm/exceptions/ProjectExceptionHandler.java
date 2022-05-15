package com.epam.esm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.epam.esm.response.entities.MyNotFoundException;
import com.epam.esm.response.entities.MyResponseBody;
import com.epam.esm.response.entities.MyResponseEntity;

@ControllerAdvice
public class ProjectExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<MyResponseEntity> handleException(NumberFormatException e) {
		MyResponseBody responseBody = new MyResponseBody("Path variable is not a valid number", 99999);
		MyResponseEntity responseEntity = new MyResponseEntity(HttpStatus.BAD_REQUEST.value(), responseBody);
		return new ResponseEntity<>(responseEntity, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<MyResponseEntity> handleException(MyNotFoundException e) {
		MyResponseBody responseBody = new MyResponseBody(e.getMessage(), 99999);
		MyResponseEntity responseEntity = new MyResponseEntity(HttpStatus.NOT_FOUND.value(), responseBody);
		return new ResponseEntity<>(responseEntity, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<MyResponseEntity> handleException(AlreadyExistException e) {
		MyResponseBody responseBody = new MyResponseBody(e.getMessage(), 99999);
		MyResponseEntity responseEntity = new MyResponseEntity(HttpStatus.BAD_REQUEST.value(), responseBody);
		return new ResponseEntity<>(responseEntity, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<MyResponseEntity> handleException(MySqlException e) {
		MyResponseBody responseBody = new MyResponseBody(e.getMessage(), 99999);
		MyResponseEntity responseEntity = new MyResponseEntity(HttpStatus.BAD_REQUEST.value(), responseBody);
		return new ResponseEntity<>(responseEntity, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<MyResponseEntity> handleException(MyGenericException e) {
		MyResponseBody responseBody = new MyResponseBody(e.getMessage(), 99999);
		MyResponseEntity responseEntity = new MyResponseEntity(HttpStatus.BAD_REQUEST.value(), responseBody);
		return new ResponseEntity<>(responseEntity, HttpStatus.BAD_REQUEST);
	}

//	@ExceptionHandler
//	public ResponseEntity<MyResponseEntity> handleException(Exception e) {
//		MyResponseBody responseBody = new MyResponseBody(e.getMessage(), 99999);
//		MyResponseEntity responseEntity = new MyResponseEntity(HttpStatus.BAD_REQUEST.value(), responseBody);
//		return new ResponseEntity<>(responseEntity, HttpStatus.BAD_REQUEST);
//	}
}
