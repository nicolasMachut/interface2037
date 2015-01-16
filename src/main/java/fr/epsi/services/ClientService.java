package fr.epsi.services;

import java.net.URI;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.epsi.entites.Question;
import fr.epsi.outils.Log;

@Path("client")
public class ClientService extends Service {
	
	@POST
	@Path("/question/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response creerQuestion (@PathParam("param") String questionStr) {
		Response reponse;
		String reponseStr;
		
		// Création de la question à partir du parametre
		Question question = new Question(questionStr);
		
		try {
			// Enregistrement de la question en bdd et récupération de l'id
			int id =  question.enregistrer().getId();
			
			// Création de l'uri permettant d'accéder à la ressource correspondant à la reponse
			reponseStr = RACINE + "reponse/" + id;
			
			reponse = Response.created(new URI(reponseStr)).build();
			
		} catch (Exception e) {
			
			// Dans le cas d'une erreur lors de l'enregistrement de la question
			// Renvoie un code HTTP 500 Internal Server Error
			reponseStr = "Une erreur interne au serveur est survenu lors de l'enregistrement de la question, veuillez reessayer plus tard.";
			
			Log.ecris("Problème lors de l'enregistrement de la question : " + e.getClass() + " - " + e.getMessage());
			reponse = Response.serverError().build();
		}
		
		return reponse;
	}
	
	
	@GET
	@Path("/reponse/{code}")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response getReponse (@PathParam("code") int id) {
		
		Response response;
		
		Question question = Question.trouverQuestionParId(id);
		
		if (question == null) { //La question n'existe pas
			response = Response.noContent().build();
			
		} else {
			
			if (question.estEnAttente()) {
				response = Response.noContent().build();
			} else {
				response = Response.accepted(question).build();
			}
		}
		
		return response;
	}
}
