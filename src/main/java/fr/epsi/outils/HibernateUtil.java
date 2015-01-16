package fr.epsi.outils;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import fr.epsi.entites.Question;

public class HibernateUtil {
     
    private static SessionFactory sessionFactory;
 
    private static SessionFactory buildSessionFactory() {
    	
        try {
            Configuration configuration = new Configuration();
            
            Properties proprietes = new Properties();
            proprietes.put("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
            proprietes.put("hibernate.connection.url", "jdbc:hsqldb:mem:test");
            proprietes.put("hibernate.connection.username", "sa");
            proprietes.put("hibernate.connection.password", "epsi812AJH");
            proprietes.put("hibernate.current_session_context_class", "thread");
            proprietes.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
            proprietes.put("hibernate.show_sql", "true");
            proprietes.put("hibernate.hbm2ddl.auto", "create");
            configuration.setProperties(proprietes);
            configuration.addAnnotatedClass(Question.class);
             
            ServiceRegistry registre = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
             
            SessionFactory sessionFactory = configuration.buildSessionFactory(registre);
             
            return sessionFactory;
        }
        catch (Throwable ex) {
        	
            System.err.println("Erreur d'initialisation de la factory : " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
}
