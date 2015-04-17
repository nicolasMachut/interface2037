package fr.epsi.services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.epsi.Main;

public class Service {

	//Format de réponse par défaut
	protected final String FORMAT_REPONSE_PAR_DEFAUT = MediaType.APPLICATION_JSON;
	
	//Racine du webservice
	protected final String RACINE = "http://localhost:"+Main.PORT_SQUID+"/interface2037/";
	
	public Response reponseErreur () {
		return  Response.serverError().header("Access-Control-Allow-Origin", "*").build();
	}
	
}
