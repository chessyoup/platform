package com.chessyoup.resources;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chessyoup.services.SessionChannelService;
import com.chessyoup.services.exceptions.BadRequestException;
import com.chessyoup.services.exceptions.PersistenceLayerException;
import com.chessyoup.services.exceptions.SessionFullException;

@Controller
public class SessionChannelResource {
	
	private static final Logger LOG = Logger.getLogger(SessionChannelResource.class.getName());
	
	@Autowired
	private SessionChannelService channelService;
	
	@RequestMapping(value = "/_ah/channel/connected" , method = RequestMethod.POST )	
	public ResponseEntity<String> connected(HttpServletRequest request){
		String from = channelService.parceRequest(request);		
		LOG.log(Level.INFO, "connected ::"+from);
		String parts[] = from.split("/");
		String sessionId = parts[0];
		String user = parts[1];
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			this.channelService.onConnected(user,sessionId);
			return new ResponseEntity<String>(null,headers,HttpStatus.OK);
		} catch (BadRequestException e) {
			LOG.log(Level.WARNING, e.getMessage(),e);
			return new ResponseEntity<String>(null,headers,HttpStatus.BAD_REQUEST);
		} catch (PersistenceLayerException e) {
			LOG.log(Level.WARNING, e.getMessage(),e);
			return new ResponseEntity<String>(null,headers,HttpStatus.SERVICE_UNAVAILABLE);
		} catch (SessionFullException e) {
			LOG.log(Level.WARNING, "Session is full!",e);
			return new ResponseEntity<String>(null,headers,HttpStatus.CONFLICT);			
		}
	}
	
	@RequestMapping(value = "/_ah/channel/disconnected" , method = RequestMethod.POST )	
	public ResponseEntity<String> disconnected(HttpServletRequest request ){
		String from = channelService.parceRequest(request);
		LOG.log(Level.INFO, "disconnected ::"+from);
		String parts[] = from.split("/");
		String sessionId = parts[0];
		String user = parts[1];			
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			this.channelService.onDisconnected(user,sessionId);
			return new ResponseEntity<String>(null,headers,HttpStatus.OK);
		} catch (BadRequestException e) {
			LOG.log(Level.WARNING, e.getMessage(),e);
			return new ResponseEntity<String>(null,headers,HttpStatus.BAD_REQUEST);
		} catch (PersistenceLayerException e) {
			LOG.log(Level.WARNING, e.getMessage(),e);
			return new ResponseEntity<String>(null,headers,HttpStatus.SERVICE_UNAVAILABLE);
		} catch (SessionFullException e) {
			LOG.log(Level.WARNING, "Session is full!",e);
			return new ResponseEntity<String>(null,headers,HttpStatus.CONFLICT);			
		}
	}
	
	@RequestMapping(value = "/message" , method = RequestMethod.POST )	
	public ResponseEntity<String> onMessage(@RequestBody String message , @RequestParam String r , @RequestParam String u  ){
//		LOG.log(Level.INFO, "onMessage :: from "+u+" , session :"+r+" , message :"+message);
		HttpHeaders headers = new HttpHeaders();
		
		try {
			this.channelService.onMessage(r, u, message);
			return new ResponseEntity<String>(null,headers,HttpStatus.OK);
		} catch (PersistenceLayerException e) {
			LOG.log(Level.WARNING, e.getMessage(),e);
			return new ResponseEntity<String>(null,headers,HttpStatus.SERVICE_UNAVAILABLE);
		} catch (BadRequestException e) {
			LOG.log(Level.WARNING, e.getMessage(),e);
			return new ResponseEntity<String>(null,headers,HttpStatus.BAD_REQUEST);
		}								
	}
}
