package specs.client;
import java.sql.SQLException;

import javax.ws.rs.core.Response;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import fr.epsi.services.ClientService;
import fr.epsi.services.ExpertService;

@RunWith(ConcordionRunner.class)
public class ClientServiceFixture {
	
	private ClientService clientService;
	private ExpertService expertService;
	
	public ClientServiceFixture () {
		this.clientService = new ClientService();
		this.expertService = new ExpertService();
	}
	
	public int poserQuestionVerifierCodeStatusSucces (String question) throws Exception {

		Response reponse = clientService.creerQuestion(question);

		return reponse.getStatus();
	}
	
	public String poserQuestionVerifierLocation (String question) throws Exception {
		
		Response reponse = clientService.creerQuestion(question);
		
		return reponse.getMetadata().get("location").toString();
	}
	
	public int getQuestionExisteCodeStatusSucces (String idExpert) {
		
		Response reponse = expertService.getQuestion(idExpert);

		return reponse.getStatus();
	}
	
	public String getQuestionExisteSucces (String idExpert) {
		
		Response reponse = expertService.getQuestion(idExpert);

		return reponse.getEntity().toString();
	}
	
	public int getQuestionExistePasStatusSucces (String idExpert) {
		
		Response reponse = expertService.getQuestion(idExpert);

		return reponse.getStatus();
	}
	
	public String getQuestionExistePasSucces (String idExpert) {
		
		Response reponse = expertService.getQuestion(idExpert);

		return reponse.getEntity().toString();
	}
	
	public int repondreQuestionCodeSucces (String idExpert, String reponseStr, int idQuestion) throws SQLException {
		
		Response reponse = expertService.repondre(idExpert, idQuestion, reponseStr);
		
		return reponse.getStatus();
	}
}
