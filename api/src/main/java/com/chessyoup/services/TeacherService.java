package com.chessyoup.services;

import com.chessyoup.domain.Teacher;
import com.chessyoup.services.exceptions.NotFoundException;
import com.chessyoup.services.exceptions.PersistenceLayerException;

public interface TeacherService {

	public Teacher getTeacher(String username) throws NotFoundException,PersistenceLayerException;

}
