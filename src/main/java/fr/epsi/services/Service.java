package fr.epsi.services;

import javax.ws.rs.core.MediaType;

import fr.epsi.outils.SQL;

public class Service {
	
	protected SQL sql;

	//Code de succès
	protected final int OK = 200;
	protected final int CREATED = 201;
	protected final int ACCEPTED = 202;

	//Code d'erreur
	protected final int INTERNAL_SERVER_ERROR = 500;
	protected final int NOT_FOUND = 404;

	//Format de réponse par défaut
	protected final String FORMAT_REPONSE_PAR_DEFAUT = MediaType.APPLICATION_JSON;
	
	//Racine du webservice
	protected final String RACINE = "http://localhost:8181/Webservice/client/";
	
	
	public void setSql(SQL sql) {
		this.sql = sql;
	}
}
