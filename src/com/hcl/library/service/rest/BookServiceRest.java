package com.hcl.library.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	
	@GET
	@Path("/{bookId}")
	@Produces("application/json")
	public Response getBook(@PathParam("bookId") String id) {
		
		BookBO bookFound =BookService.getInstance().findByIsbn(id);
		return Response.status(200).entity(bookFound).build();
	}
	
	@GET
	@Path("/allbooks")
	@Produces("application/json")
	public Response getAllBooks() {
		
		List<BookBO> bookFound =BookService.getInstance().findByLanguage("ee");
		return Response.status(200).entity(bookFound).build();
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
