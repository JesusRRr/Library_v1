package com.hcl.library.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.hcl.library.exceptions.InvalidFieldException;
import com.hcl.library.model.bo.BookBO;
import com.hcl.library.model.po.BookPO;
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
	public Response getBook(@PathParam("bookId") int id) {
		
		BookPO bookFound =BookService.getInstance().findById(id);
		return Response.status(200).entity(bookFound).build();
	}
	
	@GET
	@Path("/allbooks")
	@Produces("application/json")
	public Response getAllBooks() {
		
		List<BookPO> bookFound =BookService.getInstance().findAll();
		return Response.status(200).entity(bookFound).build();
	}
	
	@POST
	@Path("/newbook")
	@Produces("application/json")
	@Consumes("application/json")
	public Response addBook(BookBO libro) {
		System.out.println(libro);
		try {
			BookService.getInstance().createBook(libro);
			BookBO bookFound =BookService.getInstance().findByIsbn(libro.getIsbn());
			return Response.status(201).entity(bookFound.getId()).build();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return Response.status(400).entity(e).build();
		}
	}
}
