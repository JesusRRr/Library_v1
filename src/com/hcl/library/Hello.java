package com.hcl.library;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.hcl.library.model.bo.BookBO;

@Path("/sayhello")
public class Hello {
	@GET
	@Path("/{name}")
	public Response sayHello(@PathParam("name") String msg) {
		String output = "Hello, " + msg + "!";
		BookBO libro = new BookBO();
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/addBook")
	@Produces("application/json")
	@Consumes("application/json")
	public Response sayHello(BookBO libro) {
		System.out.println(libro);
		return Response.status(200).entity(1).build();
	}
	
	

}
