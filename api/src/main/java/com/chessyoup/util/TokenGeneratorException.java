package com.chessyoup.util;

public class TokenGeneratorException extends Exception {

	/** serial id. */
	private static final long serialVersionUID = 1L;

	/**
	 * Token generator exception.
	 * 
	 * @param message
	 *            - error message
	 * @param cause
	 *            - root cause
	 */
	public TokenGeneratorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Token generator exception.
	 * 
	 * @param message
	 *            - error message
	 */
	public TokenGeneratorException(String message) {
		super(message);
	}

	/**
	 * Token generator exception.
	 * 
	 * @param cause
	 *            - root cause
	 */
	public TokenGeneratorException(Throwable cause) {
		super(cause);
	}

}
