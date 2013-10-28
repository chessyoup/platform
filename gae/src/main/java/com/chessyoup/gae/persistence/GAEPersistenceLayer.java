package com.chessyoup.gae.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.chessyoup.domain.Session;
import com.chessyoup.domain.Student;
import com.chessyoup.domain.Teacher;
import com.chessyoup.persistence.PersistenceLayer;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@Component
public class GAEPersistenceLayer implements PersistenceLayer {

	private DatastoreService datastoreService;

	public GAEPersistenceLayer() {
		datastoreService = DatastoreServiceFactory.getDatastoreService();
	}

	/* (non-Javadoc)
	 * @see com.chessyoup.persistence.gae.PersistenceLayer#getTeacher(java.lang.String)
	 */
	@Override
	public Teacher getTeacher(String username) {
		
		if( username.equals("teacher1")){
			return new Teacher("teacher1");
		}
		else{
			return null;
		}
		
//		try {
//			datastoreService.get(KeyFactory.createKey("Teacher", username));
//			Teacher t = new Teacher();
//			t.setUsername(username);
//			return t;
//		} catch (EntityNotFoundException e) {			
//			return null;
//		}
	}

	/* (non-Javadoc)
	 * @see com.chessyoup.persistence.gae.PersistenceLayer#saveTeacher(com.chessyoup.domain.Teacher)
	 */
	@Override
	public void saveTeacher(Teacher teacher) {
		Entity e = new Entity("Teacher", teacher.getUsername());
		datastoreService.put(e);
	}

	/* (non-Javadoc)
	 * @see com.chessyoup.persistence.gae.PersistenceLayer#getStudent(java.lang.String)
	 */
	@Override
	public Student getStudent(String username) {
		
		if( username.equals("student1")){
			return new Student("student1");
		}
		else{
			return null;
		}
		
//		try {
//			datastoreService.get(KeyFactory.createKey("Student", username));
//			Student s = new Student(username);			
//			return s;
//		} catch (EntityNotFoundException e) {			
//			return null;
//		}
	}

	/* (non-Javadoc)
	 * @see com.chessyoup.persistence.gae.PersistenceLayer#saveStudent(com.chessyoup.domain.Student)
	 */
	@Override
	public void saveStudent(Student teacher) {
		Entity e = new Entity("Student", teacher.getUsername());
		datastoreService.put(e);
	}	
	
	@Override
	public Session createSession(String teacher,String student) {
		Session newSession = new Session();
		newSession.setId(UUID.randomUUID().toString());
		newSession.setTeacher(new Teacher(teacher));
		Entity e = new Entity("Session", newSession.getId());
		e.setProperty("teacher", teacher);
		e.setProperty("teacher_connected", false);
		e.setProperty("student", student);
		e.setProperty("student_connected", false);
		datastoreService.put(e);
		return newSession;
	}
	
	
	@Override
	public void saveSession(Session session) {
		Entity e = new Entity("Session", session.getId());
		e.setProperty("teacher", session.getTeacher() != null ? session
				.getTeacher().getUsername() : null);
		e.setProperty("student", session.getStudent() != null ? session
				.getStudent().getUsername() : null);
		datastoreService.put(e);
	}

	/* (non-Javadoc)
	 * @see com.chessyoup.persistence.gae.PersistenceLayer#getSession(java.lang.String)
	 */
	@Override
	public Session getSession(String id) {

		try {
			Entity sessionEntity = datastoreService.get(KeyFactory.createKey(
					"Session", id));
			Session session = new Session();
			session.setId(id);

			if (sessionEntity.getProperty("teacher") != null) {
				Teacher t = new Teacher();
				t.setUsername(sessionEntity.getProperty("teacher").toString());
				session.setTeacher(t);
			}

			if (sessionEntity.getProperty("student") != null) {
				Student s = new Student(sessionEntity.getProperty("student").toString());				
				session.setStudent(s);
			}
						
			session.setStudentConnected( Boolean.valueOf(sessionEntity.getProperty("student_connected").toString()));
			session.setTeacherConnected( Boolean.valueOf(sessionEntity.getProperty("teacher_connected").toString()));
			
			return session;
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.chessyoup.persistence.gae.PersistenceLayer#getTeacherSessions(java.lang.String)
	 */
	@Override
	public List<Session> getTeacherSessions(String username) {
		List<Session> sesssions = new ArrayList<Session>();
		
		Query q = new Query("Session");
		
//		if( username != null ){
//			q.addFilter("lastName", Query.FilterOperator.EQUAL, username);
//		}
		
		PreparedQuery pq = datastoreService.prepare(q);
		
		Iterator<Entity> it =   pq.asIterator();
		
		while(it.hasNext()){
			Entity e = it.next();		
			Session session = new Session();
			session.setId(e.getKey().getName());
			Teacher t = new Teacher(e.getProperty("teacher").toString());
			Student s = new Student(e.getProperty("student").toString());
			session.setTeacher(t);
			session.setStudent(s);
			session.setStudentConnected( Boolean.valueOf(e.getProperty("student_connected").toString()));
			session.setTeacherConnected( Boolean.valueOf(e.getProperty("teacher_connected").toString()));
			sesssions.add(session);
		}
				
		System.out.println(sesssions);
		return sesssions;
	}

	@Override
	public void teacherConnected(String teacher, String session) {
		
		try {
			Entity sessionEntity = datastoreService.get(KeyFactory.createKey("Session", session));
//			datastoreService.get(KeyFactory.createKey("Teacher", teacher));
			sessionEntity.setProperty("teacher_connected", true);
			datastoreService.put(sessionEntity);
		} catch (EntityNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void studentConnected(String student, String session) {
		try {
			Entity sessionEntity = datastoreService.get(KeyFactory.createKey("Session", session));
//			datastoreService.get(KeyFactory.createKey("Student", student));
			sessionEntity.setProperty("student_connected", true);
			datastoreService.put(sessionEntity);
		} catch (EntityNotFoundException e1) {
			e1.printStackTrace();
		}		
	}

	@Override
	public void teacherDisconnected(String teacher, String session) {
		try {
			Entity sessionEntity = datastoreService.get(KeyFactory.createKey("Session", session));
//			datastoreService.get(KeyFactory.createKey("Teacher", teacher));
			sessionEntity.setProperty("teacher_connected", false);
			datastoreService.put(sessionEntity);
		} catch (EntityNotFoundException e1) {
			e1.printStackTrace();
		}
		
	}

	@Override
	public void studentDisconnected(String student, String session) {
		try {
			Entity sessionEntity = datastoreService.get(KeyFactory.createKey("Session", session));
//			datastoreService.get(KeyFactory.createKey("Student", student));
			sessionEntity.setProperty("student_connected", false);
			datastoreService.put(sessionEntity);
		} catch (EntityNotFoundException e1) {
			e1.printStackTrace();
		}				
	}
}
