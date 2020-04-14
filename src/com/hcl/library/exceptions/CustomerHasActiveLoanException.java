package com.hcl.library.exceptions;

public class CustomerHasActiveLoanException extends Exception {


	private static final long serialVersionUID = -1176151710981499439L;

	public CustomerHasActiveLoanException(String errorMessage) {
		super(errorMessage);
	}
}
