package com.chessyoup.services.exceptions;

public class NotFoundException extends Exception {
		
	private static final long serialVersionUID = 6544184806747928443L;
	
	public NotFoundException(String string) {
		super(string);
	}

	public NotFoundException() {		
	}

}
