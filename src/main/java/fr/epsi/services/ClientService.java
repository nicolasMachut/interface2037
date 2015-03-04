package fr.epsi.services;

import java.net.URI;

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
		
		Question question = new Question(questionStr);
		
		try {
			int id =  question.enregistrer().getId();
			
			// Création de l'uri permettant d'accéder à la ressource correspondant à la reponse
			String adresseRessource = RACINE + "client/reponse/" + id;
			
			// Réponds un code 201 CREATED avec un en-tête Location contenant l'URI de la ressource
			reponse = Response
						.created(new URI(adresseRessource))
						.entity("Votre question à bien été enregistrée, vous pourrez bientôt consulter la réponse à cette adresse : " + adresseRessource)
						.build();
			
		} catch (Exception e) {
			
			Log.ecris("Problème lors de l'enregistrement de la question : " + e.getClass() + " - " + e.getMessage());
			reponse = Response
						.serverError()
						.entity("Un problème est survenu sur le serveur, merci de revenir plus tard !!")
						.build();
		}
		             
		return reponse;
	}
	
	
	@GET
	@Path("/reponse/{code}")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response getReponse (@PathParam("code") int id) {
		
		Response response;
		
		Question question = Question.trouverQuestionParId(id);
		if (question == null) {
			response = Response
						.status(Response.Status.NOT_FOUND)
						.entity("La ressource à laquelle vous essayez d'accéder n'existe pas.")
						.build();
			
		} else {
			try {
				System.out.println(question.toString());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			if (question.estEnAttente()) {
				response = Response
							.accepted(question)
							.entity("Aucune réponse n'a pour le moment été apporté.")
							.build();
			} else {
				response = Response
							.accepted(question)
							.entity("Votre question à été répondu par un de nos experts en question !")
							.build();
			}
		}
		
		return response;
	}
}
