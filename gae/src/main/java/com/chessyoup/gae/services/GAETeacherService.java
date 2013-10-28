package com.chessyoup.gae.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chessyoup.domain.Teacher;
import com.chessyoup.persistence.PersistenceLayer;
import com.chessyoup.services.TeacherService;
import com.chessyoup.services.exceptions.NotFoundException;
import com.chessyoup.services.exceptions.PersistenceLayerException;

@Component
public class GAETeacherService implements TeacherService {
	
	@Autowired
	private PersistenceLayer persistenceLayer;
	
	@Override
	public Teacher getTeacher(String username) throws NotFoundException,
			PersistenceLayerException {
		
		if( username.equals("teacher1")){
			return new Teacher(username);
		}
		else{
			throw new NotFoundException("Teacher not found!");
		}
		
//		Teacher t = persistenceLayer.getTeacher(username); 
//		
//		if( t != null ){
//			return t;
//		}
//		else{
//			throw new NotFoundException("Teacher not found!");
//		}				 	
	}
}
