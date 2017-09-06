package com.hanvon.survey.e;

public class AdminNameExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AdminNameExistsException(String message) {
		super(message);
	}

}
