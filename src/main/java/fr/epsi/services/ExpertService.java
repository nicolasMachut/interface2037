package fr.epsi.services;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import fr.epsi.entites.Question;

@Path("/interface2037/expert")
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
		Question question = Question.getQuestionSansReponse(idExpert);

		if (question == null)  {
			question = Question.getPremiereQuestionEnAttente();
		}


		if (question != null) {
			question.mettreEnTraitement(idExpert);
	
			// Question trouvée
			reponse = Response
					.accepted(question)
					.header("Access-Control-Allow-Origin", "*")
					.header("Allow-Control-Allow-Methods","POST,GET,OPTIONS,PUT")
					.header("Access-Control-Allow-Headers", "Content-Type, Authorization")
					.build();
		} else {
			// Aucune question en attente
			reponse = Response
					.noContent()
					.header("Access-Control-Allow-Origin", "*")
					.build();
		}

		return reponse;
	}
//	OPTIONS
	
	@OPTIONS
	@Path("/question/{idExpert}/{id}/{reponse}")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response repondreOptions (@PathParam("idExpert") String idExpert, @PathParam("id") int idQuestion, @PathParam("reponse") String reponseStr) throws SQLException {
		Response reponse;
		reponse = Response
		.ok()
		.header("Access-Control-Allow-Origin", "*")
		.header("Access-Control-Request-Method", "*")
		.build();
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
	@Path("/question/{idExpert}/{id}/{reponse}")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response repondre (@PathParam("idExpert") String idExpert, @PathParam("id") int idQuestion, @PathParam("reponse") String reponseStr) throws SQLException {
		return repondre(idExpert, idQuestion, Question.OK, reponseStr);
	}

	/**
	 * <p>Permet au systeme expert de signifier qu'il n'a pas de réponse à donner à la question passé en paramètre</p>
	 * @param idExpert
	 * @param id
	 * @return
	 */
	@PUT
	@Path("/question/{idExpert}/{id}")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response repondre (@PathParam("idExpert") String idExpert, @PathParam("id") int id) {
		return repondre(idExpert, id, Question.ERREUR, "");
	}

	
	public Response repondre (String idExpert, int idQuestion, String etat, String reponseStr) {

		Question question = Question.trouverQuestionParId(idQuestion);
		Response reponse;

		if (question != null) {

			// On vérifie que la question est bien en "TRAITEMENT" et que c'est le bon system expert qui fournit la réponse
			if (question.estEnTraitement() && question.getExpert().equals(idExpert)) {
				question.repondre(reponseStr, etat);
				reponse = Response
						.ok()
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Request-Method", "*")
						.build();
			} else {
				reponse = Response.status(Response.Status.FORBIDDEN).header("Access-Control-Allow-Origin", "*").build();				
			}

		} else {
			// La question n'existe pas
			reponse = Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").build();
		}

		return reponse;
	}
}
