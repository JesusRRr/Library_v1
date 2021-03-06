package com.hcl.library;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.hcl.library.model.bo.BookBO;
import com.hcl.library.service.BookService;
	

	
@Path("/sayhello")
public class Hello {
	
	@GET
	@Path("/{name}")
	public Response sayHello(@PathParam("name") String msg) {
		String output = "Hello, " + msg + "!";
		
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/")
	public Response index() {
		String output = "Hello";
		
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/addBook")
	@Produces("application/json")
	@Consumes("application/json")
	public Response addBook(BookBO libro) {
		System.out.println(libro);
		BookService.getInstance().createBook(libro);
		BookBO bookFound =BookService.getInstance().findByIsbn(libro.getIsbn());
		return Response.status(200).entity(bookFound.getId()).build();
	}
	
	

}
