package com.microservice.user.exception;

public class TokenExpiredException extends RuntimeException {
	public TokenExpiredException(String e) {
		super(e);
	}
	
}