package fr.epsi.services;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import fr.epsi.entites.Question;
import fr.epsi.outils.Log;
import fr.epsi.outils.Message;

@Path("client")
public class ClientService extends Service {
	
	private ObjectMapper mapper;
	
	public ClientService() {
		this.mapper = new ObjectMapper();
	}
	
	@POST
	@Path("/question/{param}")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response creerQuestion (@PathParam("param") String questionStr) throws Exception {
		
		Response reponse;
		
		Question question = new Question(questionStr);
		
		try {
			int id =  question.enregistrer().getId();
			
			// Création de l'uri permettant d'accéder à la ressource correspondant à la reponse
			String adresseRessource = RACINE + "client/reponse/" + id;
			
			// Réponds un code 201 CREATED avec un en-tête Location contenant l'URI de la ressource
			reponse = Response
						.created(new URI(adresseRessource))
 						.entity(mapper.writeValueAsString(new Message("Votre question à bien été enregistrée")))
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Headers", "*")
						.build();
				
		} catch (Exception e) {
			
			Log.ecris("Problème lors de l'enregistrement de la question : " + e.getClass() + " - " + e.getMessage());
			reponse = Response
						.serverError()
						.entity(mapper.writeValueAsString(new Message("Un problème est survenu sur le serveur, merci de revenir plus tard !!")))
						.header("Access-Control-Allow-Origin", "*")
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
						.header("Access-Control-Allow-Origin", "*")
						.build();
			
		} else {
			
			if (question.estEnAttente()) {
				response = Response
							.accepted()
							.entity("Aucune réponse n'a pour le moment été apporté.")
							.header("Access-Control-Allow-Origin", "*")
							.build();
			} else {
				response = Response
							.ok(    )
							.entity("Votre question à été répondu par un de nos experts en question ! ")
							.header("Access-Control-Allow-Origin", "*")
							.build();
			}
		}
		
		return response;
	}
}
