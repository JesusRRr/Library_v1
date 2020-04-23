package com.hcl.library.exceptions;

public class CustomerLoanException extends LoanException {


	private static final long serialVersionUID = -1176151710981499439L;

	public CustomerLoanException(String errorMessage) {
		super(errorMessage);
	}
}
