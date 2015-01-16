package fr.epsi.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("client")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientService extends Service {
	
	@POST
	@Path("/question/{param}")
	public Response creerQuestion (@PathParam("param") String questionStr) {
		Response reponse = null;
		
		return reponse;
	}

	
}
