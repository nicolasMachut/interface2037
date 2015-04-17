package fr.epsi.services;

import java.net.URI;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import fr.epsi.entites.Question;
import fr.epsi.outils.Log;

@Path("/interface2037/client")
public class ClientService extends Service {
	
	private ObjectMapper mapper;
	
	public ClientService() {
		this.mapper = new ObjectMapper();
	}
	
	
	@POST
	@Path("/question")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
<<<<<<< HEAD
	public Response creerQuestion (@FormParam("questionText") String questionText) {
=======
	public Response creerQuestion (@PathParam("param") String questionStr) {
>>>>>>> 5ad58d9ba2694e37a8f0e4f4ac86d42bcf09d274
		
		Response reponse;
		
		Question question = new Question(questionText);
		
		try {
			int id =  question.enregistrer().getId();
			
			// Création de l'uri permettant d'accéder à la ressource correspondant à la reponse
			String adresseRessource = RACINE + "client/question/" + id;
			
			
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
	@Path("/question/{code}")
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
				if (question.estEnAttente() || question.estEnTraitement()) {
					// La question n'a pas encore été répondue
					Date expirationDateReponseNonRepondu = new Date(System.currentTimeMillis()+60000);
					response = Response
								.accepted(new URI(adresseRessource))
								.header("Access-Control-Allow-Origin", "*")
								.header("Access-Control-Expose-Headers", "Location")
								.expires(expirationDateReponseNonRepondu)
								.build();
				} else {
					// La question à été répondue, on l'envoie 
					Date expirationDate = new Date(System.currentTimeMillis()+9000000);
					
					response = Response
								.ok(mapper.writeValueAsString(question))
								.header("Access-Control-Allow-Origin", "*")
								.expires(expirationDate)
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
