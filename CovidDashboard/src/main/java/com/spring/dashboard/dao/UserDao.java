package com.spring.dashboard.dao;

import java.util.ArrayList;
import java.util.List;






import org.hibernate.Query;
import org.hibernate.Session;

import com.spring.dashboard.model.User;
import com.spring.dashboard.util.HibernateUtil;



public class UserDao {
	
	
	
	public List<User> getAllApplicants() {
		
		List<User> list = new ArrayList<User>();
		 Session session = HibernateUtil.getSessionFactory().openSession();
	        
		 	
		 	String sql = " from Applicant a  ";
	        
	        
	        Query query = session.createQuery(sql);
	        
	      
	        list = query.list();
	        System.out.println(list.size());
	        session.close();
	        return list;
	}
	
	public User addUser(User applicant) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(applicant);
		
		session.getTransaction().commit();
		session.close();
		return applicant;
	}

	

	 
	 public User loginUser(String emailAddress,String password) {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        //session.beginTransaction();
	        String sql = " from User a where a.emailAddress=:emailAddress and a.password=:password ";
	        Query query = session.createQuery(sql);
	        query.setParameter("emailAddress", emailAddress);
	        query.setParameter("password", password);
	        List<User> list = query.list();
	        if (list.size() > 0) {
	        	User user=list.get(0);
	            session.close();
	            return user;
	        }
	        session.close();
	        return null;
	    }
	 
	 public User getUserByEmail(String emailAddress) {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        //session.beginTransaction();
	        String sql = " from User a where a.emailAddress=:emailAddress ";
	        Query query = session.createQuery(sql);
	        query.setParameter("emailAddress", emailAddress);
	       
	        List<User> list = query.list();
	        if (list.size() > 0) {
	        	User user=list.get(0);
	            session.close();
	            return user;
	        }
	        session.close();
	        return null;
	    }

}
