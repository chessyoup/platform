package com.chessyoup.web;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.chessyoup.domain.Session;
import com.chessyoup.persistence.PersistenceLayer;
import com.chessyoup.services.SessionChannelService;

@Controller
public class WebController {
	
	private static final Logger LOG = Logger.getLogger(WebController.class.getName());
	
	@Autowired
	PersistenceLayer persistenceLayer;
	
	@Autowired
	SessionChannelService sessionChannelService;
	
	@RequestMapping(value = "/login/student" ,  method = RequestMethod.GET)	
	public ModelAndView viewLoginPage(ModelMap  model){
		model.addAttribute("name", "student");		
		return new ModelAndView("login");
	}
	
	
	@RequestMapping(value = "/login/teacher" ,  method = RequestMethod.GET)	
	public ModelAndView viewTeacherPage(ModelMap  model){
		model.addAttribute("name", "teacher");		
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/table" ,  method = RequestMethod.GET)	
	public ModelAndView viewTable(ModelMap  model){
				
		return new ModelAndView("table");
	}
	
	@RequestMapping(value = "/sessions" ,  method = RequestMethod.GET)	
	public ModelAndView viewSessionsPage( @RequestParam String user ,  ModelMap  model){
		LOG.log(Level.INFO,"viewSessionsPage  ");
		model.addAttribute("sessions", persistenceLayer.getTeacherSessions("teacher1"));
		model.addAttribute("user", user);
		return new ModelAndView("sessions");
	}	
	
	@RequestMapping(value = "/session/{id}" ,  method = RequestMethod.GET)	
	public ModelAndView viewSessionPage( HttpServletRequest request ,  @PathVariable String id ,@RequestParam String user , ModelMap  model , UriComponentsBuilder builder){		
		Session session = persistenceLayer.getSession(id);
		LOG.log(Level.INFO,"viewSessionPage :: "+session.toString());
				
		model.addAttribute("initiator", user.equals("teacher1") ? 0 : 1);
		model.addAttribute("room_key", id);
		model.addAttribute("room_link", builder.path("/session/{id}").buildAndExpand(id).toUri()+"&user="+user);
		model.addAttribute("token", sessionChannelService.createSessionChannel(id, user));
		model.addAttribute("me", user);
								
		return new ModelAndView("session");
	}	
}
