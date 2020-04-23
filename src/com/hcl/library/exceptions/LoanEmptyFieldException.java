package com.hcl.library.exceptions;

public class LoanEmptyFieldException extends LoanException {

	private static final long serialVersionUID = 7812553157345908472L;
	
	public LoanEmptyFieldException() {
		super();
	}
	
	public LoanEmptyFieldException(String message) {
		super(message);
	}


}
