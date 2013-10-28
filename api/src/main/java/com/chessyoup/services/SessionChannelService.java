package com.chessyoup.services;

import javax.servlet.http.HttpServletRequest;

import com.chessyoup.services.exceptions.BadRequestException;
import com.chessyoup.services.exceptions.PersistenceLayerException;
import com.chessyoup.services.exceptions.SessionFullException;

public interface SessionChannelService {
	
	public String createSessionChannel(String sessionId,String username);
	
	public void onConnected(String username,String sessionId) throws BadRequestException,PersistenceLayerException,SessionFullException;
	
	public void onDisconnected(String username,String sessionId) throws BadRequestException,PersistenceLayerException,SessionFullException;
	
	public void onMessage(String sessionId,String from,String message) throws PersistenceLayerException,BadRequestException ;
	
	public String parceRequest(HttpServletRequest request);
}
