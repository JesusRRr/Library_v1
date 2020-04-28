package com.hcl.library.exceptions;

public class LoanNotFoundException extends LoanException {

	private static final long serialVersionUID = 8782416026359430218L;
	
	public LoanNotFoundException() {
		super();
	}
	
	public LoanNotFoundException(String message) {
		super(message);
	}

}
