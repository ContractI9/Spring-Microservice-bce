package com.microservice.user.exception;

public class ItemNotFoundException extends RuntimeException {
	public ItemNotFoundException(String e) {
		super(e);
	}

}
