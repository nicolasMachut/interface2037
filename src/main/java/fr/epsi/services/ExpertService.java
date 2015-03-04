package fr.epsi.services;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import fr.epsi.entites.Question;

@Path("expert")
public class ExpertService extends Service {
	
	/**
	 * <p>Fournie une question en attente au systeme expert au format JSON</p>
	 * <p>La méthode est synchronized pour empecher plusieurs systemes experts d'obtenir la mème question</p>
	 * <p>Méthode GET car le systeme expert demande la représentation d'une ressource</p>
	 * <p>Un petit doute içi car l'état du serveur est modifié par la requête, peut être un POST ?</p>
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@GET
	@Path("/question/{idExpert}")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public synchronized Response getQuestion (@PathParam("idExpert") String idExpert) {
		Response reponse;

		//Récupération de la première question en attente
		Question question = Question.getPremiereQuestionEnAttente();
		
		if (question != null) {
			question.mettreEnTraitement(idExpert);
			reponse = Response.ok(question).entity(question.toString()).build();
			
		} else {
			reponse = Response.status(Response.Status.NOT_FOUND).entity("Aucune question en attente.").build();
		}

		return reponse;
	}
	
	
	/**
	 * <p>Permet au system expert de répondre à une question</p>
	 * <p>Méthode PUT car le systeme expert connait l'URI de la ressource</p>
	 * @param id
	 * @param reponse
	 * @return
	 * @throws SQLException 
	 */
	@PUT
	@Path("/repondre/{idExpert}/{id}/{reponse}")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response repondre (@PathParam("idExpert") String idExpert, @PathParam("id") int id, @PathParam("reponse") String reponseStr) throws SQLException {
		
		Question question = Question.trouverQuestionParId(id);
		
		Response reponse;
		
		if (question != null) {
			
			// On vérifie que la question est bien en "TRAITEMENT" et que c'est le bon system expert qui fournit la réponse
			if (question.estEnTraitement() && question.getExpert().equals(idExpert)) {
				question.repondre(reponseStr);
				reponse = Response.ok().entity("Votre réponse à bien été enregistrée.").build();
				
			} else {
				reponse = Response.status(Response.Status.FORBIDDEN).encoding("Vous ne pouvez pas répondre à cette question.").build();				
			}
			
		} else {
			// La question n'existe pas
			reponse = Response.status(Response.Status.NOT_FOUND).entity("Vous ne pouvez pas répondre à une question qui existe pas, enfin ...").build();
		}
		
		return reponse;
	}
}
