package specs.client;
import javax.ws.rs.core.Response;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import fr.epsi.services.ClientService;

@RunWith(ConcordionRunner.class)
public class ClientServiceFixture {
	
	private ClientService clientService;
	
	public ClientServiceFixture () {
		this.clientService = new ClientService();
	}
	
	public int poserQuestionVerifierCodeStatusSucces (String question) {

		Response reponse = clientService.creerQuestion(question);

		return reponse.getStatus();
	}
	
	public String poserQuestionVerifierLocation (String question) {
		
		Response reponse = clientService.creerQuestion(question);
		
		return reponse.getMetadata().get("location").toString();
	}

}
