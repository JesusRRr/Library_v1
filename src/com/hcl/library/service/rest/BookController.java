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
public class BookController {
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
		
		try {
		BookPO bookFound =BookService.getInstance().findById(id);
		
		if(bookFound==null) {
			return Response.status(404).entity("{\"code\":\"404\",\"message\":\"Book Not Found\"}").build();
		}
		
		return Response.status(200).entity(bookFound).build();
		
		}catch(Exception e) {
			return Response.status(500).entity("{\"code\":\"500\",\"message\":\"Unhandled exception in service\"}").build();
		}
		
		
	}
	
	@GET
	@Path("/allbooks")
	@Produces("application/json")
	public Response getAllBooks() {
		
		try {
			List<BookPO> bookFound =BookService.getInstance().findAll();
			return Response.status(200).entity(bookFound).build();
		}catch(Exception e) {
			return Response.status(500).entity("{\"code\":\"500\",\"message\":\"Unhandled exception in service\"}").build();
		}
		
	}
	@POST
	@Path("/works")
	@Consumes("application/json")
	@Produces("application/json")
	public Response isWorking(String name) {
		System.out.println(name);
		if(name.equals("error")) {
			return Response.status(500).entity("error").build();
		}else {
			return Response.status(201).entity("Hello " + name+" your app is working").build();
		}
		}
	
	@POST
	@Path("/newbook")
	@Produces("application/json")
	@Consumes("application/json")
	public Response addBook(BookBO libro) {
		System.out.println(libro);
		try {
			boolean isBookCreated =BookService.getInstance().createBook(libro);
			BookBO bookFound =BookService.getInstance().findByIsbn(libro.getIsbn());
			return Response.status(201).entity("{\"id\": "+bookFound.getId()+"}").build();
		}catch(InvalidFieldException e) {
			System.out.println(e.getMessage());
			return Response.status(400).entity("{\"code\":\"400\",\"message\":\" "+ e.getMessage()+"\"}").build();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return Response.status(500).entity("{\"code\":\"500\",\"message\":\" "+ e.getMessage()+"\"}").build();
			
		}
	}
}
