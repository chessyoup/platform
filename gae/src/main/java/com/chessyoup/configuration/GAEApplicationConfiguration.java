package com.chessyoup.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chessyoup.util.TokenGenerator;

@Configuration
public class GAEApplicationConfiguration {
	
	@Bean
	public TokenGenerator getTokenGenerator(){
		return new TokenGenerator();
	}
	
}
