package com.microservice.order.exception;

public class IncorrectFieldException extends RuntimeException {


	public IncorrectFieldException(String e) {
		super(e);
	}
}