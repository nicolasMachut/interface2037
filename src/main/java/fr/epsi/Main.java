package fr.epsi;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.hibernate.Session;

import fr.epsi.entites.Question;
import fr.epsi.outils.HibernateUtil;

public class Main {

    public static void main(String[] args) throws Exception{
    	
    	Question question = new Question ("yoooooooo");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(question);
        session.getTransaction().commit();
         
        //terminate session factory, otherwise program won't end
        HibernateUtil.getSessionFactory().close();
    	
        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            port = "8282";
        }

        final Server server = new Server(Integer.valueOf(port));
        final WebAppContext root = new WebAppContext();

        root.setContextPath("/");
        root.setParentLoaderPriority(true);

        final String webappDirLocation = "src/main/webapp/";
        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);

        server.setHandler(root);

        server.start();
        server.join();
    }
}
