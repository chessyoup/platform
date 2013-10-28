package com.chessyoup.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;

public class TokenGenerator {
	
	private static final int NUMBER_OF_TOKEN_SEGMENTS = 4;
	private static final String PARTS_SEPARATOR = ":";
	private static final String UTF_8 = "UTF-8";
	private static final long RANGE_MIN = -1200000L; // 20 minutes
	private static final long RANGE_MAX = 1200000L;
	
	/**
	 * Generate the token from the data provided by client app.
	 * 
	 * @param data
	 *            - string data
	 * @param tokenType
	 * @return token
	 * @throws TokenGeneratorException
	 */
	public String generateToken(String data, TokenType tokenType)
			throws TokenGeneratorException {
		if (data == null || data.isEmpty()) {
			throw new IllegalArgumentException(
					"Data provided could not be null or empty.");
		}

		byte[] token = null;
		long random = 0L;
		if (TokenType.CRITICAL_OPERATION_TOKEN != tokenType) {
			random = getRandomNumber();
		}
		long expirationMillis = System.currentTimeMillis() + tokenType.getTtl()
				+ random;
		try {
			token = generateToken(data, expirationMillis, tokenType);
		} catch (UnsupportedEncodingException e) {
			throw new TokenGeneratorException("Encoding is not supported.", e);
		}
		String result = null;
		try {
			result = new String(Base64.encode(token), UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new TokenGeneratorException("Encoding is not supported.", e);
		}
		return result;
	}

	/**
	 * Validate a token.
	 * 
	 * @param token
	 * @throws InvalidTokenException
	 */
	public void validateToken(String token, boolean isCriticalOperation)
			throws InvalidTokenException {
		String originalToken = decodeToken(token);
		String[] parts = retrieveTokenParts(originalToken);
		verifyHash(originalToken, parts, isCriticalOperation);
	}

	/**
	 * Check if the token is valid for specified account.
	 * 
	 * @param token
	 *            - current token value
	 * @param accountId
	 *            - account id.
	 * @throws InvalidTokenException
	 *             - the token is invalid.
	 */
	public boolean isTokenValidForAccount(String token, String accountId)
			throws InvalidTokenException {
		String originalToken = decodeToken(token);
		String[] parts = retrieveTokenParts(originalToken);
		return accountId.equalsIgnoreCase(parts[1]);
	}

	/**
	 * Retrieve accountID from token, after verification.
	 * 
	 * @param token
	 *            - token
	 * @param isCriticalOperation
	 * @return accountId
	 * @throws InvalidTokenException
	 */
	public String retrieveAccountIdFromToken(String token,
			boolean isCriticalOperation) throws InvalidTokenException {
		String originalToken = decodeToken(token);
		String[] parts = retrieveTokenParts(originalToken);
		verifyHash(originalToken, parts, isCriticalOperation);
		return parts[1];
	}

	private void verifyHash(String originalToken, String[] parts,
			boolean isCriticalOperation) throws InvalidTokenException {
		try {
			TokenType tokenType = TokenType.valueOf(parts[0]);
			String verificationHash = new String(generateToken(parts[1],
					Long.valueOf(parts[2]), tokenType), UTF_8);
			if (isCriticalOperation && (TokenType.WEBSITE_TOKEN == tokenType)) {
				throw new InvalidTokenException(
						"Token provided is not critical");
			}
			if (!originalToken.equalsIgnoreCase(verificationHash)) {
				throw new InvalidTokenException("Token provided is invalid");
			}
		} catch (UnsupportedEncodingException e) {
			throw new InvalidTokenException("Token provided is invalid", e);
		}
	}

	private String[] retrieveTokenParts(String originalToken)
			throws InvalidTokenException {
		String[] parts = originalToken.split(PARTS_SEPARATOR);
		if (parts.length < NUMBER_OF_TOKEN_SEGMENTS) {
			throw new InvalidTokenException("Invalid token has been provided.");
		}

		if (System.currentTimeMillis() > Long.valueOf(parts[2])) {
			throw new InvalidTokenException("Expired token has been provided.");
		}
		return parts;
	}

	private String decodeToken(String token) throws InvalidTokenException {
		if (token == null || token.isEmpty()) {
			throw new IllegalArgumentException(
					"Token value provided could not be null or empty.");
		}
		byte[] original = Base64.decode(token);
		String originalToken = null;
		try {
			originalToken = new String(original, UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new InvalidTokenException("Token provided is invalid", e);
		}
		return originalToken;
	}

	private long getRandomNumber() {
		Random generator = new Random();
		// get the range, casting to long to avoid overflow problems
		long range = RANGE_MAX - RANGE_MIN + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * generator.nextDouble());
		return (long) (fraction + RANGE_MIN);
	}

	private byte[] generateToken(String data, long expirationMillis,
			TokenType tokenType) throws UnsupportedEncodingException {
		StringBuffer input = new StringBuffer(tokenType.name());
		input.append(PARTS_SEPARATOR).append(data);
		input.append(PARTS_SEPARATOR).append(expirationMillis);
		KeyParameter key = new KeyParameter(tokenType.getSecret().getBytes(
				UTF_8));
		HMac hmac = new HMac(new SHA1Digest());
		hmac.init(key);
		byte[] inputData = input.toString().getBytes(UTF_8);
		hmac.update(inputData, 0, inputData.length);
		byte[] outputData = new byte[hmac.getMacSize()];
		hmac.doFinal(outputData, 0);
		String output = new String(outputData, UTF_8);
		StringBuffer token = new StringBuffer(tokenType.name());
		token.append(PARTS_SEPARATOR).append(data);
		token.append(PARTS_SEPARATOR).append(expirationMillis)
				.append(PARTS_SEPARATOR).append(output);
		return token.toString().getBytes(UTF_8);

	}
}
