package fr.epsi.services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.epsi.Main;

public class Service {

	//Format de réponse par défaut
	protected final String FORMAT_REPONSE_PAR_DEFAUT = MediaType.APPLICATION_JSON;
	
	//Racine du webservice
<<<<<<< HEAD
	protected final String RACINE = "http://192.148.43.129:"+Main.PORT_SQUID+"/interface2037/";
=======
	protected final String RACINE = "http://localhost:"+Main.PORT+"/interface2037/";
>>>>>>> 5ad58d9ba2694e37a8f0e4f4ac86d42bcf09d274
	
	public Response reponseErreur () {
		return  Response.serverError().header("Access-Control-Allow-Origin", "*").build();
	}
	
	public Response autoriserPut () {
		Response reponse;
		reponse = Response
		.ok()
		.header("Access-Control-Allow-Origin", "*")
		.header("Access-Control-Request-Method", "*")
		.header("Access-Control-Allow-Methods", "PUT")
		.build();
		return reponse;
	}
	
}
