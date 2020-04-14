package com.hcl.library.service.rest;


import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.hcl.library.exceptions.CustomerHasActiveLoanException;
import com.hcl.library.service.LoanService;
import com.hcl.library.service.rest.request.Loan;

@Path("/loans")
public class LoanServiceRest {
	LoanService service = LoanService.getLoanService();

	
	public Response createNewLoan(Loan loanRequest) {
		int id;
		try {
			id = service.createLoan(loanRequest);
		}catch(CustomerHasActiveLoanException e) {
			e.printStackTrace();
			return Response.status(400).entity(e).build();
		}
		
		return Response.status(201).entity(id).build();
	}


}
