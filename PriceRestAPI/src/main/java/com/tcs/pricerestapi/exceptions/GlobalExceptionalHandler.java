package com.tcs.pricerestapi.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionalHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ProductIdNotFoundException.class)
	public ResponseEntity<?> productIdNotFoundException(ProductIdNotFoundException exception, WebRequest request) {
		
		ErrorDetails errorDetails = new ErrorDetails
				(new Date(), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(InvalidPriceException.class)
	public ResponseEntity<?> invalidPriceException(InvalidPriceException exception, WebRequest request) {
		
		ErrorDetails errorDetails = new ErrorDetails
				(new Date(), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.NOT_ACCEPTABLE);
	}
 
}
