package com.microservice.order.exception;

public class ItemNotFoundException extends RuntimeException {
	public ItemNotFoundException(String e) {
		super(e);
	}

}
