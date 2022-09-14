package com.microservice.user.exception;

public class EmptyFieldException extends RuntimeException{
	public EmptyFieldException(String e) {
		super(e);
	}
}
