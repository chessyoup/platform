package com.chessyoup.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Student")
public class Student {
	
	private String username;
	
	public Student(String string) {
		this.username = string;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
