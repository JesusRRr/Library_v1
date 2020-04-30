package com.hcl.library.exceptions;

public class EmptyFieldException extends InvalidFieldException{

	private static final long serialVersionUID = 4352069292793975011L;

	public EmptyFieldException(String message) {
		super(message);
	}
}
