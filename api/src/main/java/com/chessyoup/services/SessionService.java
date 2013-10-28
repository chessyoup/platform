package com.chessyoup.services;

import com.chessyoup.domain.Session;
import com.chessyoup.services.exceptions.BadRequestException;
import com.chessyoup.services.exceptions.NotFoundException;
import com.chessyoup.services.exceptions.PersistenceLayerException;

public interface SessionService {
	
	/**
	 * Get some existing session using session id
	 * @param sessionId - the unique session id
	 * @return - the session domain object
	 * @throws NotFoundException - for invalid session id 
	 * @throws PersistenceLayerException - for no database access
	 */
	public Session getSessionById(String sessionId) throws NotFoundException,PersistenceLayerException;
	
	/**
	 * Create a new session for an teacher
	 * @param teacher 
	 * @return session unique Id
	 * @throws NotFoundException for unknown teacher
	 * @throws BadRequestException if teacher is bad
	 * @throws PersistenceLayerException for no database access
	 */
	public String createSession(String teacher,String student) throws  NotFoundException, BadRequestException , PersistenceLayerException ;
	
	/**
	 * Update the session
	 * @param session to be updated
	 * @throws NotFoundException for invalid input session data
	 * @throws BadRequestException for bad request
	 * @throws PersistenceLayerException for no database access
	 */
	public void updateSession(Session session) throws  NotFoundException, BadRequestException , PersistenceLayerException ;
	
}
