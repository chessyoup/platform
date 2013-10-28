package com.chessyoup.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Session")
public class Session {
		
	private String id;
	
	private Student student;
		
	private Teacher teacher;
	
	private boolean studentConnected;
	
	private boolean teacherConnected;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	
	public boolean isStudentConnected() {
		return studentConnected;
	}

	public void setStudentConnected(boolean studentConnected) {
		this.studentConnected = studentConnected;
	}

	public boolean isTeacherConnected() {
		return teacherConnected;
	}

	public void setTeacherConnected(boolean teacherConnected) {
		this.teacherConnected = teacherConnected;
	}
}
