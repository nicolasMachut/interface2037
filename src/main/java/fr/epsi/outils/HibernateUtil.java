package fr.epsi.outils;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import fr.epsi.entites.Question;

public class HibernateUtil {
     
    private static SessionFactory sessionFactory;
 
    private static SessionFactory buildSessionFactory(Properties proprietes) {
    	
        try {
            Configuration configuration = new Configuration();
            
           // configuration.setProperties(proprietes);
            configuration.configure("hibernateMySQL.cfg.xml");
            //configuration.addAnnotatedClass(fr.epsi.entites.Question.class);
             
            ServiceRegistry registre = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
             
            SessionFactory sessionFactory = configuration.buildSessionFactory(registre);
             
            return sessionFactory;
        }
        catch (Throwable ex) {
        	
            System.err.println("Erreur d'initialisation de la factory : " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactoryHsqlDB() {
        if(sessionFactory == null) {
        	Properties proprietes = new Properties();
            proprietes.put("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
            proprietes.put("hibernate.connection.url", "jdbc:hsqldb:mem:test");
            proprietes.put("hibernate.connection.username", "sa");
            proprietes.put("hibernate.connection.password", "epsi812AJH");
            proprietes.put("hibernate.current_session_context_class", "thread");
            proprietes.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
            proprietes.put("hibernate.show_sql", "true");
            proprietes.put("hibernate.hbm2ddl.auto", "create-drop");
        	sessionFactory = buildSessionFactory(proprietes);
        }
        return sessionFactory;
    }
    
    public static SessionFactory getSessionFactoryMySql() {
        if(sessionFactory == null) {
        	Properties proprietes = new Properties();
            proprietes.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            proprietes.put("hibernate.connection.url", "jdbc:mysql://127.0.0.1:3306/webservice_restfull");
            proprietes.put("hibernate.connection.username", "root");
            proprietes.put("hibernate.connection.password", "epsi812AJH");
            proprietes.put("hibernate.current_session_context_class", "thread");
            proprietes.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            proprietes.put("hibernate.show_sql", "true");
        	sessionFactory = buildSessionFactory(proprietes);
        }
        return sessionFactory;
    }
}
