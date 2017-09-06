package com.hanvon.survey.e;

public class BagEmptyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BagEmptyException(String message){
		super(message);
	}
	
}
