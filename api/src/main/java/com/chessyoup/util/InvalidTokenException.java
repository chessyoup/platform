package com.chessyoup.util;

public class InvalidTokenException extends Exception {

	/** Serial id. */
	private static final long serialVersionUID = 1L;

	/**
	 * Invalid token.
	 * 
	 * @param message
	 *            - error message
	 * @param cause
	 *            - root cause
	 */
	public InvalidTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Invalid token.
	 * 
	 * @param message
	 *            - error message
	 */
	public InvalidTokenException(String message) {
		super(message);
	}

}
