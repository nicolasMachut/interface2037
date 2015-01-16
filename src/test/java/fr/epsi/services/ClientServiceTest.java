package fr.epsi.services;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import fr.epsi.entites.Question;

public class ClientServiceTest extends JerseyTest {
	
	@Override
    protected Application configure() {
        return new ResourceConfig(MyResource.class);
    }
	
	@Test
	public void poserQuestionTest () {
		
		
	}
	

}
