package com.hanvon.survey.e;

public class AdminLoginForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AdminLoginForbiddenException(String message) {
		super(message);
	}

}
