package com.hcl.library.service.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.hcl.library.model.bo.BookBO;
import com.hcl.library.service.BookService;

@Path("/books")
public class BookServiceRest {
	@GET
	@Path("/")
	public Response index() {
		String output = "Books";
		
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/addBook")
	@Produces("application/json")
	@Consumes("application/json")
	public Response addBook(BookBO libro) {
		System.out.println(libro);
		BookService.getInstance().createBook(libro);
		return Response.status(200).entity(1).build();
	}
}
