package com.swiftcart.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	//Handle ResourceNotFoundException--->
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request){
		ErrorResponse error = ErrorResponse.builder()
		.timeStamp(LocalDateTime.now())
		.status(HttpStatus.NOT_FOUND.value())
		.error("Not Found")
		.message(ex.getMessage())
		.path(request.getRequestURI())
		.build();
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	//Handle DuplicateResourceException---->
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex, HttpServletRequest request){
		ErrorResponse error = ErrorResponse.builder()
		.timeStamp(LocalDateTime.now())
		.status(HttpStatus.CONFLICT.value())
		.error("Conflict")
		.message(ex.getMessage())
		.path(request.getRequestURI())
		.build();
		
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
	
	//Handle InsufficientStockException---->
	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException ex, HttpServletRequest request){
		ErrorResponse error = ErrorResponse.builder()
		.timeStamp(LocalDateTime.now())
		.status(HttpStatus.BAD_REQUEST.value())
		.error("Insufficient Stock")
		.message(ex.getMessage())
		.path(request.getRequestURI())
		.build();
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	//Handle EmptyCartException---->
	@ExceptionHandler(EmptyCartException.class)
	public ResponseEntity<ErrorResponse> handleEmptyCartException(EmptyCartException ex, HttpServletRequest request){
		ErrorResponse error = ErrorResponse.builder()
		.timeStamp(LocalDateTime.now())
		.status(HttpStatus.BAD_REQUEST.value())
		.error("Empty Cart")
		.message(ex.getMessage())
		.path(request.getRequestURI())
		.build();
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	//Handle InvalidOperationException---->
	@ExceptionHandler(InvalidOperationException.class)
	public ResponseEntity<ErrorResponse> handleInvalidOperationException(InvalidOperationException ex, HttpServletRequest request){
		ErrorResponse error = ErrorResponse.builder()
		.timeStamp(LocalDateTime.now())
		.status(HttpStatus.BAD_REQUEST.value())
		.error("Invalid Operation")
		.message(ex.getMessage())
		.path(request.getRequestURI())
		.build();
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	//Handle MethodArgumentNotValidException---->
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request){
		
		Map<String, String> validationErrors = new HashMap<>();
		for(FieldError error :ex.getBindingResult().getFieldErrors()) {
			validationErrors.put(error.getField(), error.getDefaultMessage());
		}
		
		ErrorResponse error = ErrorResponse.builder()
		.timeStamp(LocalDateTime.now())
		.status(HttpStatus.BAD_REQUEST.value())
		.error("Validation Failed")
		.message("Input Validation Failed Please Check the Error")
		.validationError(validationErrors)
		.path(request.getRequestURI())
		.build();
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	//Handle MethodArgumentTypeMismatchException---->
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request){
		Class c= ex.getRequiredType();
		String message= String.format("Inavalid value '%s' for the Parameter '%s'. Expected type':%s'", ex.getValue(), ex.getName(), c != null ? c.getSimpleName() : "unkown");
		
		ErrorResponse error = ErrorResponse.builder()
		.timeStamp(LocalDateTime.now())
		.status(HttpStatus.BAD_REQUEST.value())
		.error("Type Mismatch")
		.message(message)
		.path(request.getRequestURI())
		.build();
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	//Handle IllegalArgumentException---->
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request){
		
		ErrorResponse error = ErrorResponse.builder()
		.timeStamp(LocalDateTime.now())
		.status(HttpStatus.BAD_REQUEST.value())
		.error("Wrong Argument")
		.message(ex.getMessage())
		.path(request.getRequestURI())
		.build();
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	//Handle Exception---->
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handletException(Exception ex, HttpServletRequest request){
		
		ErrorResponse error = ErrorResponse.builder()
		.timeStamp(LocalDateTime.now())
		.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
		.error("Internal Server Error")
		.message(ex.getMessage())
		.path(request.getRequestURI())
		.build();
		
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
