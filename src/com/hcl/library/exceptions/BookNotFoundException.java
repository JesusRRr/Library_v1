package com.hcl.library.exceptions;

public class BookNotFoundException extends Exception{


	private static final long serialVersionUID = -3598052556149258654L;
	
	public BookNotFoundException(String message) {
		super(message);
	}
	

}
