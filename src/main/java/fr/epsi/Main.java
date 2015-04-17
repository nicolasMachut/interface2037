package fr.epsi;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * <p>Classe de démarrage du programme et du serveur jetty</p>
 * @author nicolas
 *
 */
public class Main {
	
	public static final String PORT_APPS = "8283";
	public static final String PORT_SQUID = "8282";
	
    public static void main(String[] args) throws Exception {
    	
        String port = System.getenv("PORT");
        
        if (port == null || port.isEmpty()) {
            port = PORT_APPS;
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
