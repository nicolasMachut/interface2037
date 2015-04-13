package specs;
import java.sql.SQLException;

import javax.ws.rs.core.Response;

import junit.framework.TestCase;

import org.concordion.integration.junit4.ConcordionRunner;
import org.hibernate.Session;
import org.junit.runner.RunWith;

import fr.epsi.outils.HibernateUtil;
import fr.epsi.services.ClientService;
import fr.epsi.services.ExpertService;

@RunWith(ConcordionRunner.class)
public class ServiceFixture extends TestCase {

	private ClientService clientService;
	private ExpertService expertService;
	
	public ServiceFixture() {
		this.clientService = new ClientService();
		this.expertService = new ExpertService();
	}

	public void clean () {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.createSQLQuery("truncate table question").executeUpdate();
		session.getTransaction().commit();
	}

	public Response poserQuestionVerifierSucces (String question) throws Exception {

		Response reponse = null;
		try {
			reponse = clientService.creerQuestion(question);
		} finally {
			clean();
		}		
		return reponse;
	}

	public Response getQuestionExpertExisteSucces (String idExpert) {

		Response reponse = null;
		try {
			reponse = clientService.creerQuestion("Quel temps fait-il à Bordeaux ?");
			reponse = expertService.getQuestion(idExpert);
		} finally {
			clean();
		}
		
		return reponse;
	}

	public int getQuestionExistePasStatusSucces (String idExpert) {

		Response reponse = null;
		try {
			reponse = expertService.getQuestion(idExpert);	
		} finally {
			clean();
		}

		return reponse.getStatus();
	}


	public Response repondreQuestionExpertSucces (String idExpert, String reponseStr) throws Exception {

		Response reponse = null;
		try {
			clientService.creerQuestion("C'est ma question");
			reponse = expertService.getQuestion(idExpert);
			reponse = expertService.repondre(idExpert, 1,reponseStr);
		} finally {
			clean();
		}
		
		return reponse;
	}
	
	public Response clientPoseQuestionPasDeReponse(String question)
	{
		Response res = null;
		
		try {
			res = clientService.creerQuestion(question);
			clientService.getReponse(1);
		} finally {
			clean();
		}
				
		return res;
	}
	
	public Response clientPoseQuestionReponse(String question) throws NumberFormatException, SQLException
	{
		Response res = null;
		
		try {
			res = clientService.creerQuestion(question);
			res = expertService.getQuestion("alex");
			res = expertService.repondre("alex",1, "reponse");
			res = clientService.getReponse(1);
		} finally {
			clean();
		}
				
		return res;
	}
	
	public Response systemeExpertNePeutPasRepondreAUneQuestion () {
		
		Response res = null;
		
		try {
			res = clientService.creerQuestion("C'est ma question");
			res = expertService.getQuestion("nicolas");
			res = expertService.repondre("nicolas", 1);
		} finally {
			clean();
		}
		
		return res;
	}
	
	public Response clientDemandeReponseQuestionEnEchec () {
		Response res = null;
		try {
			res = clientService.creerQuestion("Quelle heure est-il ?");
			res = expertService.getQuestion("nicolas");
			res = expertService.repondre("nicolas", 1);
			res = clientService.getReponse(1);
			
		} finally {
			clean();
		}
		return res;
	}
	
	
}