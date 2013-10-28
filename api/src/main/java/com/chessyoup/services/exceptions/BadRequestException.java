package com.chessyoup.services.exceptions;

public class BadRequestException extends Exception {
	
	public BadRequestException(String string) {
		super(string);
	}

	public BadRequestException() {
	}
	
	private static final long serialVersionUID = 5011576153544861779L;

}
