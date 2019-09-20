package com.cg.training.aspects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cg.training.exceptions.IncorrectResourceDetailException;
import com.cg.training.exceptions.ResourceAlreadyExistException;
import com.cg.training.exceptions.ResourceNotFoundException;
import com.cg.training.responses.CustomResponse;

@ControllerAdvice
public class AdminExceptionExpect {
	@ExceptionHandler(IncorrectResourceDetailException.class)
	public ResponseEntity<CustomResponse> handleIncorrectResourceDetailExceptionAspect(Exception e) {
		CustomResponse response = new CustomResponse(e.getMessage(), HttpStatus.EXPECTATION_FAILED.value());
		return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomResponse> handleResourceNotFoundExceptionAspect(Exception e) {
		CustomResponse response = new CustomResponse(e.getMessage(), HttpStatus.EXPECTATION_FAILED.value());
		return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(ResourceAlreadyExistException.class)
	public ResponseEntity<CustomResponse> handleResourceAlreadyExistExceptionAspect(Exception e) {
		CustomResponse response = new CustomResponse(e.getMessage(), HttpStatus.EXPECTATION_FAILED.value());
		return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
	}
}
