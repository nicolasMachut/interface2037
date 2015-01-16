package fr.epsi.services;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import fr.epsi.entites.Question;

public class ExpertService extends Service {
	
	// Une question en traitement depuis plus de 10 min doit passer a en attente
	

	/**
	 * <p>Fournie une question en attente au systeme expert au format JSON</p>
	 * <p>La méthode est synchronized pour empecher plusieurs systemes experts d'obtenir la mème question</p>
	 * @return
	 */
	@GET
	@Path("/getQuestion")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public synchronized Response getQuestion () {

		Response reponse;

		//Récupération de la première question en attente
		Question question = Question.getPremiereQuestionEnAttente();
		
		if (question != null) {
			Question.miseEnTraitementDeLaQuestion(question.getId());
			reponse = Response.accepted(question).build();
		} else {
			reponse = Response.noContent().build();
		}

		return reponse;
	}
	
	/**
	 * <p>Permet au system expert de répondre à une question</p>
	 * @param id
	 * @param reponse
	 * @return
	 * @throws SQLException 
	 */
	@POST
	@Path("/repondre/{id}/{reponse}")
	@Produces(FORMAT_REPONSE_PAR_DEFAUT)
	public Response repondre (@PathParam("id") int id, @PathParam("reponse") String reponseStr) throws SQLException {
		
		Question question = Question.trouverQuestionParId(id);
		// Il faut vérifier que la question est bien en "traitement", une fois répondu, il faut la passer en résolu
		
		Response reponse;
		
		if(question.repondre(reponseStr)) {
			reponse = Response.accepted().build();
			
		} else {
			
			reponse = Response.noContent().build();
		}
		
		return reponse;
	}
}
