package com.microservice.order.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<String> userNotFoundHandler(ItemNotFoundException e){
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(IllegalActionException.class)
	public ResponseEntity<String> IllegalActionHandler(IllegalActionException e){
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(EmptyFieldException.class)
	public ResponseEntity<String> emptyFieldHandler(EmptyFieldException e){
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(IncorrectFieldException.class)
	public ResponseEntity<String> IncorrectFieldHandler(IncorrectFieldException e){
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> DataIntegrityViolationHandler(DataIntegrityViolationException e){
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		return new ResponseEntity<Object>("This request does not exist, Please enter a valid request",HttpStatus.BAD_REQUEST);
	}

}
