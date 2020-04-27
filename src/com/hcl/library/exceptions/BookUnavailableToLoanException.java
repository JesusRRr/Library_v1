package com.hcl.library.exceptions;

public class BookUnavailableToLoanException extends LoanException {

	private static final long serialVersionUID = 6169105602482591972L;

	public BookUnavailableToLoanException() {
		super();

	}

	public BookUnavailableToLoanException(String message) {
		super(message);
	}

}
