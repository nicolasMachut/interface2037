package fr.epsi.services;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.FormParam;
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
<<<<<<< HEAD
=======
					.header("Allow-Control-Allow-Methods","POST,GET,OPTIONS,PUT")
>>>>>>> 5ad58d9ba2694e37a8f0e4f4ac86d42bcf09d274
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
<<<<<<< HEAD
	
	@OPTIONS
	@Path("/question")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response repondreOptions () {
		return this.autoriserPut();
	}
	
	@OPTIONS
	@Path("/question/impossible")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response repondreOptionsImpossible () {
		return this.autoriserPut();
	}
	
=======
//	OPTIONS
	
	@OPTIONS
	@Path("/question/{idExpert}/{id}/{reponse}")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response repondreOptions (@PathParam("idExpert") String idExpert, @PathParam("id") int idQuestion, @PathParam("reponse") String reponseStr){
		Response reponse;
		reponse = Response
		.ok()
		.header("Access-Control-Allow-Origin", "*")
		.header("Access-Control-Request-Method", "*")
		.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT")
		.build();
		return reponse;
	}

>>>>>>> 5ad58d9ba2694e37a8f0e4f4ac86d42bcf09d274

	/**
	 * <p>Permet au system expert de répondre à une question</p>
	 * <p>Méthode PUT car le systeme expert connait l'URI de la ressource</p>
	 * @param id
	 * @param reponse
	 * @return
	 * @throws SQLException 
	 */
	@PUT
	@Path("/question")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response repondre ( @FormParam("idExpert") String idExpert, @FormParam("idQuestion") int idQuestion, @FormParam("reponseText") String reponseStr) throws SQLException {
		return repondre(idExpert, idQuestion, Question.OK, reponseStr);
	}

	
<<<<<<< HEAD
	
=======
	@OPTIONS
	@Path("/question/{idExpert}/{id}")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response repondreOptionsNoRep (@PathParam("idExpert") String idExpert, @PathParam("id") int id){
		Response reponse;
		reponse = Response
		.ok()
		.header("Access-Control-Allow-Origin", "*")
		.header("Access-Control-Request-Method", "*")
		.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT")
		.build();
		return reponse;
	}
>>>>>>> 5ad58d9ba2694e37a8f0e4f4ac86d42bcf09d274
	/**
	 * <p>Permet au systeme expert de signifier qu'il n'a pas de réponse à donner à la question passé en paramètre</p>
	 * @param idExpert
	 * @param id
	 * @return
	 */
	@PUT
	@Path("/question/impossible")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response repondre (@FormParam("idExpert") String idExpert, @FormParam("idQuestion") int idQuestion) {
		return repondre(idExpert, idQuestion, Question.ERREUR, "");
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
<<<<<<< HEAD
=======
						.header("Access-Control-Allow-Methods", "PUT")
>>>>>>> 5ad58d9ba2694e37a8f0e4f4ac86d42bcf09d274
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
