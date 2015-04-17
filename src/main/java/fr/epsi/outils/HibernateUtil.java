package fr.epsi.outils;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	private static void buildSessionFactory () {
		
		String bdd = "webservice_restful";
		String mdp = "epsi812AJH";
		String user = "root";

		Properties proprietes = new Properties();
        proprietes.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
<<<<<<< HEAD
        proprietes.put("hibernate.connection.url", "jdbc:mysql://127.0.0.1:3306/"+bdd);
        proprietes.put("hibernate.connection.username", user);
        proprietes.put("hibernate.connection.password", mdp);
=======
        proprietes.put("hibernate.connection.url", "jdbc:mysql://127.0.0.1:3306/webservice_restfull");
        proprietes.put("hibernate.connection.username", "root");
        proprietes.put("hibernate.connection.password", "yourpasswordhere");
>>>>>>> 5ad58d9ba2694e37a8f0e4f4ac86d42bcf09d274
        proprietes.put("hibernate.current_session_context_class", "thread");
        proprietes.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        proprietes.put("hibernate.hbm2ddl.auto", "create-drop");

		try {
				
			Configuration configuration = new Configuration();

			configuration.setProperties(proprietes);
			configuration.addAnnotatedClass(fr.epsi.entites.Question.class);

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
