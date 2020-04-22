package com.hcl.library.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.hcl.library.exceptions.CustomerHasActiveLoanException;
import com.hcl.library.model.bo.LoanBO;
import com.hcl.library.service.LoanService;
import com.hcl.library.service.rest.request.Loan;
import com.hcl.library.service.rest.request.ReturnLoan;

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
		} catch (Exception e) {
			return Response.status(400).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
		}

		return Response.status(201).entity("{\"id\": \"" + id + "\"}").build();
	}

	@POST
	@Path("/returnloan")
	@Produces("application/json")
	@Consumes("application/json")
	public Response returnLoan(ReturnLoan loan) {
		try {
			service.returnLoan(loan);
		} catch (Exception e) {
			return Response.status(400).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
		}
		
		return Response.status(201).entity("{\"id\": \"" + loan.getIdLoan() + "\"}").build(); 
	}

	@GET
	@Path("/all-loans")
	@Produces("application/json")
	public Response retriveAllLoans() {
		List<LoanBO> loanFound = service.findAll();

		return Response.status(200).entity(loanFound).build();

	}

	@GET
	@Path("{id_loan}")
	@Produces("application/json")
	public Response getLoanDetails(@PathParam("id_loan") int id) {
		LoanBO loan;
		try {
			loan = service.getLoanDetails(id);
		} catch (Exception e) {
			return Response.status(400).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
		}

		return Response.status(200).entity(loan).build();
	}

}
