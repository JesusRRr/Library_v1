package com.hcl.library.exceptions;

public class OnlyDigitsException extends InvalidFieldException{
	
	private static final long serialVersionUID = -3081808703167897373L;

	public OnlyDigitsException(String message) {
		super(message);
	}
}
