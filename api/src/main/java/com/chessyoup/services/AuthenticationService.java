package com.chessyoup.services;

import com.chessyoup.domain.LoginRequest;
import com.chessyoup.domain.LoginResponse;
import com.chessyoup.services.exceptions.AuthenticationException;
import com.chessyoup.services.exceptions.PersistenceLayerException;
import com.chessyoup.util.TokenGeneratorException;

public interface AuthenticationService {

	/**
	 * 
	 * @param loginRequest
	 * @return - the login response , or null for failure
	 * @throws TokenGeneratorException
	 */
	public LoginResponse studentSignin(LoginRequest loginRequest)
			throws AuthenticationException, TokenGeneratorException,
			PersistenceLayerException;

	/**
	 * 
	 * @param loginRequest
	 * @return - the login response , or null for failure
	 */
	public LoginResponse teacherSignin(LoginRequest loginRequest)
			throws AuthenticationException, TokenGeneratorException,
			PersistenceLayerException;
}
