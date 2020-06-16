/**
 * 
 */
package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import bd.domains.Inkas;
import bd.domains.Key;
import bd.domains.Operator;
import bd.domains.Polzov;


/**
 * @author �����
 *
 */
@SuppressWarnings("deprecation")
public class HiberUtil {
	private static final SessionFactory sessionFactory;
	static {
	try {
	
		Configuration cfg =  new AnnotationConfiguration()
		.addAnnotatedClass(Operator.class)
		.addAnnotatedClass(Key.class)
		.addAnnotatedClass(Inkas.class)
		.addAnnotatedClass(Polzov.class)
		.configure("hibernate.cfg.xml");		
		sessionFactory =cfg.buildSessionFactory();
	} catch (Throwable ex) {
	System.err.println("Initial SessionFactory creation failed." + ex);
	throw new ExceptionInInitializerError(ex);	}
	}
	 
	public static SessionFactory getSessionFactory() {
	return sessionFactory;
	}
}
