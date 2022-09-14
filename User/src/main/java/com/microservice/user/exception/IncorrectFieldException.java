package com.microservice.user.exception;

public class IncorrectFieldException extends RuntimeException {


	public IncorrectFieldException(String e) {
		super(e);
	}
}