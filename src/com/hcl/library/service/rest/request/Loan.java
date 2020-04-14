package com.hcl.library.service.rest.request;

import java.util.List;

import lombok.Data;

@Data
public class Loan {
	
	private String customerCurp;
	private String staffUsername;
	private List<Integer> books;

}
