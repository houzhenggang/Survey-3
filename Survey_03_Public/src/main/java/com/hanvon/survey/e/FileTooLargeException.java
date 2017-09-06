package com.hanvon.survey.e;

public class FileTooLargeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileTooLargeException(String message) {
		super(message);
	}

}
