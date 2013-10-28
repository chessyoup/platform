package com.chessyoup.gae.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chessyoup.domain.Session;
import com.chessyoup.persistence.PersistenceLayer;
import com.chessyoup.services.SessionService;
import com.chessyoup.services.exceptions.BadRequestException;
import com.chessyoup.services.exceptions.NotFoundException;
import com.chessyoup.services.exceptions.PersistenceLayerException;

@Component
public class GAESessionService implements SessionService {
	
	@Autowired
	private PersistenceLayer persistenceLayer;
	
	@Override
	public Session getSessionById(String sessionId) throws NotFoundException,
			PersistenceLayerException {
		
		Session session = persistenceLayer.getSession(sessionId);
		
		if( session != null ){
			return session;
		}
		else{
			throw new NotFoundException();
		}		
	}

	@Override
	public String createSession(String teacher,String student) throws NotFoundException,
			BadRequestException, PersistenceLayerException {
		return this.persistenceLayer.createSession(teacher,student).getId();				
	}

	@Override
	public void updateSession(Session session) throws NotFoundException,
			BadRequestException, PersistenceLayerException {
						
	}
}
