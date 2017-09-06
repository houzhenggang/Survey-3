package com.hanvon.survey.e;

public class UserOperationForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserOperationForbiddenException(String message) {
		super(message);
	}

}
