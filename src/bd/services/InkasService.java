package bd.services;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import bd.domains.Inkas;

import utils.HiberUtil;


public class InkasService {
	private Session session ; 
	private Transaction transaction = null;
	private SessionFactory sessionFactory = HiberUtil.getSessionFactory();
	private Query query; 
	//private Inkas ink;
	
	public InkasService(){};

	public boolean storeInkas(Inkas ink) 
	{
		try 
		{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(ink);
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
