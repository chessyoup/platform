package com.chessyoup.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Teacher")
public class Teacher {
	
	private String username;

	public Teacher(String teacher) {
		this.username = teacher;
	}

	public Teacher() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}		
}
