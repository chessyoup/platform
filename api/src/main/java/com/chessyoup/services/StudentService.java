package com.chessyoup.services;

import com.chessyoup.services.exceptions.BadRequestException;
import com.chessyoup.services.exceptions.PersistenceLayerException;

public interface StudentService {
	
	public void register(String username) throws BadRequestException , PersistenceLayerException;
	
}
