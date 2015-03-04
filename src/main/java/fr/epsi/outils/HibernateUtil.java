package fr.epsi.outils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
     
    private static void buildSessionFactory () {
    	
        try {
            Configuration configuration = new Configuration();
            
            configuration.configure();
             
            ServiceRegistry registre = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
             
            sessionFactory = configuration.buildSessionFactory(registre);
             
        }
        catch (Throwable ex) {
        	
            System.err.println("Erreur d'initialisation de la factory : " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory () {
    	
    	if (sessionFactory == null) {
    		buildSessionFactory();
    	} 
    	
    	return sessionFactory;
    	
    }
}
