package com.hanvon.survey.e;

public class UserLoginNeededException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserLoginNeededException(String message){
		super(message);
	}
	
}
