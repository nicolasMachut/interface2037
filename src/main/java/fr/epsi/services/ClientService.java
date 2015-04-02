package fr.epsi.services;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.server.spi.ResponseErrorMapper;

import fr.epsi.entites.Question;
import fr.epsi.outils.Log;


public class ClientService extends Service {
	
	private ObjectMapper mapper;
	
	public ClientService() {
		this.mapper = new ObjectMapper();
	}
	
	@POST
	@Path("/question/{param}")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response creerQuestion (@PathParam("param") String questionStr) {
		
		Response reponse;
		
		Question question = new Question(questionStr);
		
		try {
			int id =  question.enregistrer().getId();
			
			// Création de l'uri permettant d'accéder à la ressource correspondant à la reponse
			String adresseRessource = RACINE + "client/reponse/" + id;
			
			
			// Réponds un code 202 ACCEPTED avec un en-tête Location contenant l'URI de la ressource
			reponse = Response
						.accepted()
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Expose-Headers", "Location")
						.header("Location", new URI(adresseRessource))
						.build();
				
		} catch (Exception e) {
			
			Log.ecris("Problème lors de l'enregistrement de la question : " + e.getClass() + " - " + e.getMessage());
			reponse = this.reponseErreur();
		}
		             
		return reponse;
	}
	
	
	@GET
	@Path("/reponse/{code}")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response getReponse (@PathParam("code") int id) {
		
		Response response = null;
		
		Question question = Question.trouverQuestionParId(id);
		
		if (question == null) {
			response = Response
						.status(Response.Status.NOT_FOUND)
						.header("Access-Control-Allow-Origin", "*")
						.build();
			
		} else {
			try {
				String adresseRessource = RACINE + "client/reponse/" + id;
				if (question.estEnAttente()) {
					// La question n'a pas encore été répondue
					response = Response
								.accepted(new URI(adresseRessource))
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Expose-Headers", "Location")
								.build();
				} else {
					// La question à été répondue, on l'envoie 
					response = Response
								.ok(mapper.writeValueAsString(question))
								.header("Access-Control-Allow-Origin", "*")
								.build();
				}
			} catch (Exception e) {
				Log.ecris("Erreur lors de la récupération de la question " + id + " " + e.getClass() + " " + e.getMessage());
				response = this.reponseErreur();
			}
		}
		
		return response;
	}
}
