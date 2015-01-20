package fr.epsi.services;

import javax.ws.rs.core.MediaType;

public class Service {

	//Format de réponse par défaut
	protected final String FORMAT_REPONSE_PAR_DEFAUT = MediaType.APPLICATION_JSON;
	
	//Racine du webservice
	protected final String RACINE = "http://localhost:8282/";
	
	public String toJson (String text) {
		return "Json";
	}
	
}
