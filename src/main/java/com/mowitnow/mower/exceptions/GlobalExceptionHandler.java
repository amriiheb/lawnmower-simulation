package com.mowitnow.mower.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleFileNotFoundException(FileNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse("File not found", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FileProcessingException.class)
	public ResponseEntity<ErrorResponse> handleFileProcessingException(FileProcessingException ex) {
		ErrorResponse errorResponse = new ErrorResponse("File processing error", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FileFormatException.class)
	public ResponseEntity<ErrorResponse> handleFileFormatException(FileFormatException ex) {
		ErrorResponse errorResponse = new ErrorResponse("File format error", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}