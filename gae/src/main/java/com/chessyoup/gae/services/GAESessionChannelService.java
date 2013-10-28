package com.chessyoup.gae.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chessyoup.domain.Session;
import com.chessyoup.domain.Student;
import com.chessyoup.domain.Teacher;
import com.chessyoup.json.JSONException;
import com.chessyoup.json.JSONObject;
import com.chessyoup.persistence.PersistenceLayer;
import com.chessyoup.services.SessionChannelService;
import com.chessyoup.services.exceptions.BadRequestException;
import com.chessyoup.services.exceptions.PersistenceLayerException;
import com.chessyoup.services.exceptions.SessionFullException;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelPresence;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

@Component
public class GAESessionChannelService implements SessionChannelService {
	
	private static final Logger LOG = Logger.getLogger(GAESessionChannelService.class.getName());
	
	@Autowired
	private PersistenceLayer persistenceLayer;
		
	
	@Override
	public void onConnected(String username, String sessionId)
			throws BadRequestException, PersistenceLayerException,
			SessionFullException {
		
		LOG.log(Level.INFO,username + " is connected to "+sessionId);
		
		Student student = persistenceLayer.getStudent(username);
		Teacher teacher = persistenceLayer.getTeacher(username);		
		if( student != null ){									
			persistenceLayer.studentConnected(username, sessionId);
		}
		else if( teacher != null ){
			persistenceLayer.teacherConnected(username, sessionId);
		}
		else{
			throw new BadRequestException("Username ["+username+"] is not registered!");
		}									
	}

	@Override
	public void onDisconnected(String username, String sessionId)
			throws BadRequestException, PersistenceLayerException,
			SessionFullException {
		LOG.log(Level.INFO,username + " is disconnected from "+sessionId);
		
		Session session = persistenceLayer.getSession(sessionId);
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String otherUser = getOtherUser(session, username);
		
		if( isConnected(username,session) ){
			channelService.sendMessage(new ChannelMessage(sessionId + "/" + otherUser, "{\"type\":\"bye\"}"));
			LOG.log(Level.INFO,username + "Delivered bye message to "+otherUser);
			userDisconnected(username,sessionId);	
		}
		else{
			LOG.log(Level.INFO,username + "is allready discomnected!");
		}													
	}
	
	@Override
	public void onMessage(String sessionId, String from, String message) {
		ChannelService channelService = ChannelServiceFactory.getChannelService();		
		Session session = persistenceLayer.getSession(sessionId);
		String otherUser = getOtherUser(session, from);
		
		LOG.log(Level.INFO, from +"->"+message+"-<"+otherUser);
		
		if( isByeMessage(message) ){
			LOG.log(Level.INFO, "Bye message from "+from);
			userDisconnected(from,sessionId);
		}
		else{
			channelService.sendMessage(new ChannelMessage(sessionId + "/" + otherUser, message));
			LOG.log(Level.INFO, "Delivered message to user :"+otherUser);
		}									
	}
	
	@Override
	public String createSessionChannel(String sessionId, String username) {
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		return channelService.createChannel(sessionId + "/" + username);
	}

	@Override
	public String parceRequest(HttpServletRequest request) {
		ChannelService c = ChannelServiceFactory.getChannelService();
		try {
			ChannelPresence cp = c.parsePresence(request);
			return cp.clientId();
		} catch (IOException e1) {			
			e1.printStackTrace();
			return null;
		}
	}
	
	
	class Message{
	
		Message(String sessionId, String to, String message){
			this.to = to;
			this.sessionId = sessionId;
			this.message = message;
		}
		
		String sessionId;
		String to;
		String message;
		
		@Override
		public String toString() {
			return "Message [sessionId=" + sessionId + ", to=" + to
					+ ", message=" + message + "]";
		}
	}
	
	private boolean isConnected(String otherUser,Session session) {
		Student student = persistenceLayer.getStudent(otherUser);
		Teacher teacher = persistenceLayer.getTeacher(otherUser);
		
		if( student != null ){
			LOG.info(otherUser +" isConnected :"+session.isStudentConnected());
			return session.isStudentConnected();
		}
		else if( teacher != null ){
			LOG.info(otherUser +" isConnected :"+session.isTeacherConnected());
			return session.isTeacherConnected();
		}
				
		return false;
	}

	
	private void userDisconnected(String username,String sessionId) {
		Student student = persistenceLayer.getStudent(username);
		Teacher teacher = persistenceLayer.getTeacher(username);		
		
		if( student != null ){									
			persistenceLayer.studentDisconnected(username, sessionId);						
		}
		else if( teacher != null ){
			persistenceLayer.teacherConnected(username, sessionId);			
		}							
	}
	
	private boolean isByeMessage(String message) {
		try {
			JSONObject json = new JSONObject(message.trim());			
			return json.get("type").toString().trim().equals("bye"); 					
		} catch (JSONException e) {
			return false;
		}		
	}

	
	private String getOtherUser(Session session , String user){
		if( user.endsWith(session.getStudent().getUsername())){
			return session.getTeacher().getUsername();
		}
		else{
			return session.getStudent().getUsername();
		}
	}
}
