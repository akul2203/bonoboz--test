package com.user.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ErrorResponse> resourceNotFoundException(ResourceNotFoundException rnfe) {
	        return new ResponseEntity<>(
	                new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, rnfe.getMessage()),
	                HttpStatus.NOT_FOUND);
	    }
	 
	 
	    @ExceptionHandler(UnauthorizedException.class)
	    public ResponseEntity<ErrorResponse> unauthorizationException(UnauthorizedException ue) {
	        return new ResponseEntity<>(
	                new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, ue.getMessage()),
	                HttpStatus.UNAUTHORIZED);
	    }
	    
	    @ExceptionHandler(ResourceAlreadyExistException.class)
	    public ResponseEntity<ErrorResponse> resourceAlreadyExistException(ResourceAlreadyExistException raee) {
	        return new ResponseEntity<>(
	                new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE, raee.getMessage()),
	                HttpStatus.NOT_ACCEPTABLE);
	    }
	    
	    @ExceptionHandler(ExpiredJwtException.class)
	    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
	    	  return new ResponseEntity<>(
	                  new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, ex.getMessage()),
	                  HttpStatus.BAD_REQUEST);
	    }
}

