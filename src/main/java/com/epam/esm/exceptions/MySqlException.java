package com.epam.esm.exceptions;

public class MySqlException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MySqlException(String msg) {
		super(msg);
	}

}
