package com.chessyoup.resources;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chessyoup.domain.Session;
import com.chessyoup.services.SessionService;
import com.chessyoup.services.exceptions.NotFoundException;
import com.chessyoup.services.exceptions.PersistenceLayerException;

@Controller
@RequestMapping("/api/session")
public class SessionResource {
	
	private static final Logger LOG = Logger.getLogger(SessionResource.class.getName());
	
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Session> getSessionById(@PathVariable String id) {
		LOG.log(Level.INFO, "getSessionById :: "+id);
		
		HttpHeaders headers = new HttpHeaders();
		Session session;
		
		try {
			session = sessionService.getSessionById(id);
			return new ResponseEntity<Session>(session, headers, HttpStatus.OK);
		} catch (NotFoundException e) {
			LOG.log(Level.WARNING, "Session not fond!",e );
			return new ResponseEntity<Session>(null, headers, HttpStatus.NOT_FOUND);
		} catch (PersistenceLayerException e) {
			LOG.log(Level.WARNING, "Database error!",e );
			return new ResponseEntity<Session>(null, headers, HttpStatus.SERVICE_UNAVAILABLE);			
		}					
	}		
}
