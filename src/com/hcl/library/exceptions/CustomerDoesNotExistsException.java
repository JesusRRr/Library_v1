package com.hcl.library.exceptions;

public class CustomerDoesNotExistsException extends Exception {
	

	private static final long serialVersionUID = 6034815584003358394L;

	public CustomerDoesNotExistsException() {
		super();
	}
	
	public CustomerDoesNotExistsException(String message) {
		super(message);
	}

}
