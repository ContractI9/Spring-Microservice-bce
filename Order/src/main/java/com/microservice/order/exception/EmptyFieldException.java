package com.microservice.order.exception;

public class EmptyFieldException extends RuntimeException{
	public EmptyFieldException(String e) {
		super(e);
	}
}
