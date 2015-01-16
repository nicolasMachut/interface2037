package fr.epsi.services;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import fr.epsi.Main;
import fr.epsi.entites.Question;

public class ClientServiceTest extends JerseyTest {
	
	@Override
    protected Application configure() {
       return new ResourceConfig(ClientService.class);
    }
	
	public void poserQuestionTest () {
		final WebTarget target = target("client");
		final Response rep = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(Response.class, MediaType.APPLICATION_JSON));
	}
}
