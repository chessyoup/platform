package com.chessyoup.util;
/**
 * Define types of tokens;
 * 
 * @author gabrielciuloaica
 * 
 */
public enum TokenType {

	/** Native client token. */
	NATIVE_CLIENT_TOKEN(3600000L, "WOOW_AUTH_SECRET_2012"),

	/** Website token */
	WEBSITE_TOKEN(7776000000L, "What is the distance to the Moon?"),

	/** Critical operation Token */
	CRITICAL_OPERATION_TOKEN(120000L, "The same as distance from Moon to here.");

	private final Long ttl;
	private final String secret;

	private TokenType(long ttl, String secret) {
		this.ttl = ttl;
		this.secret = secret;
	}

	public Long getTtl() {
		return ttl;
	}

	public String getSecret() {
		return secret;
	}

}
