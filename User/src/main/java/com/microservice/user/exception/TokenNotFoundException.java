package com.microservice.user.exception;

public class TokenNotFoundException extends RuntimeException {
	public TokenNotFoundException(String e) {
		super(e);
	}
	
}