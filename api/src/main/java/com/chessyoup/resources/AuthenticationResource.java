package com.chessyoup.resources;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chessyoup.domain.LoginRequest;
import com.chessyoup.domain.LoginResponse;
import com.chessyoup.services.AuthenticationService;
import com.chessyoup.services.SessionService;

@Controller
@RequestMapping("/api/authentication")
public class AuthenticationResource {
	
	private static final Logger LOG = Logger.getLogger(AuthenticationResource.class.getName());
	
	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	private SessionService sessionService ;
	
	
	@RequestMapping(value = "/student" ,  method = RequestMethod.POST )	
	public @ResponseBody LoginResponse studentSignin(@RequestBody LoginRequest request , HttpServletResponse response){
		
		try {
			return this.authService.studentSignin(request);
		} catch (Exception e) {
			LOG.log(Level.WARNING , e.getMessage() , e);			
			response.setStatus(401);			
			return null;
		}				
	}
	
	@RequestMapping(value = "/teacher" ,  method = RequestMethod.POST)	
	public @ResponseBody LoginResponse teacherSignin(@RequestBody LoginRequest request , HttpServletResponse response){
		
		try {
			LoginResponse lr = this.authService.teacherSignin(request);
			sessionService.createSession(request.getUsername(), "student1");
			return lr;
		} catch (Exception e) {
			LOG.log(Level.WARNING , e.getMessage() , e);			
			response.setStatus(401);			
			return null;
		}			
	}
}
