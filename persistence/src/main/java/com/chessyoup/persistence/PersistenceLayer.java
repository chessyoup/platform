package com.chessyoup.persistence;

import java.util.List;

import com.chessyoup.domain.Session;
import com.chessyoup.domain.Student;
import com.chessyoup.domain.Teacher;

public interface PersistenceLayer {

	public abstract Teacher getTeacher(String username);

	public abstract void saveTeacher(Teacher teacher);

	public abstract Student getStudent(String username);

	public abstract void saveStudent(Student teacher);
	
	public abstract Session createSession(String teacher,String student);
	
	public abstract void teacherConnected(String teacher,String session);
	
	public abstract void studentConnected(String student,String session);
	
	public abstract void teacherDisconnected(String teacher,String session);
	
	public abstract void studentDisconnected(String student,String session);
	
	public abstract void saveSession(Session session);

	public abstract Session getSession(String id);

	public abstract List<Session> getTeacherSessions(String username);

}