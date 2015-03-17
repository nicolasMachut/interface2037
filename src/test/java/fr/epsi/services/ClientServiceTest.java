package fr.epsi.services;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
import org.junit.Test;

public class ClientServiceTest extends JerseyTest {
	
	@Override
    protected Application configure() {
       return new ResourceConfig(ClientService.class);
    }
	
	@Ignore
	@Test
	public void poserQuestionVerifierCodeStatusSuccesEtLocation () throws Exception {
		
		ClientService clientService = new ClientService();
		Response reponse = clientService.creerQuestion("maquestion");
		
		assertEquals(201, reponse.getStatus());
		assertEquals("[http://localhost:8282/client/reponse/1]", reponse.getMetadata().get("location").toString());
		
		reponse = clientService.creerQuestion("maquestion");
		assertEquals("[http://localhost:8282/client/reponse/2]", reponse.getMetadata().get("location").toString());
	}
}
