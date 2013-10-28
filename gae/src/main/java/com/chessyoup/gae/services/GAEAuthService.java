package com.chessyoup.gae.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chessyoup.domain.LoginRequest;
import com.chessyoup.domain.LoginResponse;
import com.chessyoup.persistence.PersistenceLayer;
import com.chessyoup.services.AuthenticationService;
import com.chessyoup.services.exceptions.AuthenticationException;
import com.chessyoup.services.exceptions.PersistenceLayerException;
import com.chessyoup.util.TokenGenerator;
import com.chessyoup.util.TokenGeneratorException;
import com.chessyoup.util.TokenType;

@Component
public class GAEAuthService implements AuthenticationService {

	@Autowired
	private TokenGenerator tokenGenerator;

	@Autowired
	private PersistenceLayer jpaLayer;

	@Override
	public LoginResponse studentSignin(LoginRequest loginRequest)
			throws AuthenticationException, TokenGeneratorException , PersistenceLayerException {

		if ("student1".equals(loginRequest.getUsername())) {

			if ("password1".equals(loginRequest.getPassword())) {
				return new LoginResponse(loginRequest.getUsername(),
						tokenGenerator.generateToken(
								loginRequest.getUsername(),
								TokenType.WEBSITE_TOKEN));
			} else {
				throw new AuthenticationException(
						"Invalid username or password!");
			}
		} else {
			throw new AuthenticationException("Invalid username or password!");
		}
	}

	@Override
	public LoginResponse teacherSignin(LoginRequest loginRequest)
			throws AuthenticationException, TokenGeneratorException , PersistenceLayerException {

		if ("teacher1".equals(loginRequest.getUsername())) {

			if ("password1".equals(loginRequest.getPassword())) {
				return new LoginResponse(loginRequest.getUsername(),
						tokenGenerator.generateToken(
								loginRequest.getUsername(),
								TokenType.WEBSITE_TOKEN));
			} else {
				throw new AuthenticationException(
						"Invalid username or password!");
			}
		} else {
			throw new AuthenticationException("Invalid username or password!");
		}
	}

}
