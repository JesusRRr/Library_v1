package com.hcl.library.service.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReturnLoan {
	@JsonProperty("id_loan")
	private int idLoan;
	
	@JsonProperty("staff")
	private int staffId;
	
	@JsonProperty("client")
	private int customerId;
	
	@JsonProperty("book")
	private int idBook;
}
