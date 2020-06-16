package bd.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import bd.domains.Operator;
import bd.domains.Polzov;

import utils.HiberUtil;

public class OperatorService {
	
	private Session session ; 
	private Transaction transaction = null;
	private SessionFactory sessionFactory = HiberUtil.getSessionFactory();
	

	public OperatorService(){}
		
	@SuppressWarnings("unchecked")
	public List<String> getOperList() 
	{
		try
		{
			session = sessionFactory.openSession();			
			transaction = session.beginTransaction();
			List<String> oList = session.createQuery("Select oper.name from Operator oper").list();
			transaction.commit();			
		return oList;
		}
		 catch (HibernateException e) {
			  transaction.rollback();
			  e.printStackTrace();
			  return null;
			  } finally {
			 session.close();
			  }	
	}

	public String chkOperator(Operator oper) 
	{
		try
		{
			session = sessionFactory.openSession();			
			transaction = session.beginTransaction();
			String oName = (String)session.createQuery("Select oper.name from Operator oper where oper.operid = :oid and oper.operpass = :opass")
					.setParameter("oid", oper.getOperid())
					.setParameter("opass", oper.getOperpass())
					.uniqueResult();
			transaction.commit();			
		return oName;
		}
		 catch (HibernateException e) {
			  transaction.rollback();
			  e.printStackTrace();
			  return null;
			  } finally {
			 session.close();
			  }
	}
	
	public Polzov getPrava(Polzov plz)
	{
		try
		{
			session = sessionFactory.openSession();			
			transaction = session.beginTransaction();
			Polzov oName = (Polzov)session.createQuery("from Polzov oper where oper.userid = :oid and oper.userpass = :opass")
					.setParameter("oid", plz.getUserid())
					.setParameter("opass", plz.getUserpass())
					.uniqueResult();
			transaction.commit();			
		return oName;
		}
		 catch (HibernateException e) {
			  transaction.rollback();
			  e.printStackTrace();
			  return null;
			  } finally {
				  session.close();
			  }
	}
	
	
}
