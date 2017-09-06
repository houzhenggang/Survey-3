package com.hanvon.survey.e;

public class UserLoginForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserLoginForbiddenException(String message) {
		super(message);
	}

}
