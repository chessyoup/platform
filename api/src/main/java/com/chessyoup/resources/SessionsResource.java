package com.chessyoup.resources;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.chessyoup.domain.Session;
import com.chessyoup.services.SessionService;
import com.chessyoup.services.TeacherService;
import com.chessyoup.services.exceptions.BadRequestException;
import com.chessyoup.services.exceptions.NotFoundException;
import com.chessyoup.services.exceptions.PersistenceLayerException;

@Controller
@RequestMapping("/api/sessions")
public class SessionsResource {
	
	private static final Logger LOG = Logger.getLogger(SessionsResource.class.getName());
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private TeacherService teacherService;
	
	@RequestMapping(method = RequestMethod.POST , headers = "Accept=application/json")
	public ResponseEntity<String> createSession(@RequestBody Session session , UriComponentsBuilder builder) {
		HttpHeaders headers = new HttpHeaders();
		
		if( session.getTeacher() == null || session.getTeacher().getUsername() == null || session.getTeacher().getUsername().trim().length() == 0 ){
			return new ResponseEntity<String>(headers, HttpStatus.BAD_REQUEST);					
		}
		else{									
			try {
				teacherService.getTeacher(session.getTeacher().getUsername());
				String sessionId = this.sessionService.createSession(session.getTeacher().getUsername(),session.getStudent().getUsername());								
				headers.setLocation(builder.path("/session/{id}").buildAndExpand(sessionId).toUri());
				return new ResponseEntity<String>(headers, HttpStatus.CREATED);								
			} catch (NotFoundException e) {
				LOG.log(Level.WARNING,"Not found",e);
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);	
			} catch (BadRequestException e) {
				LOG.log(Level.WARNING,"Bad request",e);
				return new ResponseEntity<String>(headers, HttpStatus.BAD_REQUEST);
			} catch (PersistenceLayerException e) {
				LOG.log(Level.WARNING,"Database error!",e);
				return new ResponseEntity<String>(headers, HttpStatus.SERVICE_UNAVAILABLE);
			}
		}						
	}
}
