package com.hcl.library.service.rest;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.hcl.library.exceptions.CustomerHasActiveLoanException;
import com.hcl.library.service.LoanService;
import com.hcl.library.service.rest.request.Loan;

@Path("/loans")
public class LoanServiceRest {
	LoanService service = LoanService.getLoanService();
	

	@POST
	@Path("/newLoan")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createNewLoan(Loan loanRequest) {
		int id;
		try {
			id = service.createLoan(loanRequest);
		}catch(CustomerHasActiveLoanException e) {
			return Response.status(400).entity("{\"error\": \""+e.getMessage()+"\"}").build();
		}
		
		return Response.status(201).entity("{\"id\": \""+id+"\"}").build();
	}
	
	
	


}
