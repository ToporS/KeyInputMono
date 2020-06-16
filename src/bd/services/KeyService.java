package bd.services;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import bd.domains.Key;

import utils.HiberUtil;


public class KeyService {
	
	private Session session ; 
	private Transaction transaction = null;
	private SessionFactory sessionFactory = HiberUtil.getSessionFactory();	
	private Query query;
	
	public KeyService(){}

	public String getLastAccount() 
	{
		try 
		{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			query = session.createQuery("SELECT key.numkey FROM Key key order by key.numkey desc");
			@SuppressWarnings("unchecked")
			String keyL = query.setFirstResult(0).setMaxResults(1).uniqueResult().toString();			
			return keyL;
		}
		catch (HibernateException e) {
			  transaction.rollback();
			  e.printStackTrace();
			  return null;
			  } finally {
				  transaction.commit();
			  session.close();
			  }	
	}

	public String checkClientKey(String previousKey) 
	{
		try 
		{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			query = session.createQuery("SELECT key.operator FROM Key key where key.datakey = :datakey");
			query.setParameter("datakey", previousKey);
			@SuppressWarnings("unchecked")
			String keyL = (String) query.uniqueResult();			
			return keyL;
		}
		catch (HibernateException e) {
			  transaction.rollback();
			  e.printStackTrace();
			  return "Error";
			  } finally {
				  transaction.commit();
			  session.close();
			  }	
	}

	public boolean storeClientKey(Key newKey) 
	{
		try 
		{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(newKey);
			return true;
		}
		catch (HibernateException e) {
			  transaction.rollback();
			  e.printStackTrace();
			  return false;
			  } finally {
				  transaction.commit();
			  session.close();
			  }	
	}
}