package com.BankApp.API;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.BankApp.config.InsufficientBalanceException;
import com.BankApp.dto.ErrorDetails;
import com.BankApp.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionHandlingAPI {
	
	//handle BacnkAccountNotFoundException
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails>handleResourceNotFoundException(ResourceNotFoundException ex){
		
		ErrorDetails errorDetails= ErrorDetails.builder()
				.timeStamp(LocalDateTime.now())
				.errorCode(HttpStatus.NOT_FOUND.toString())
				.errorMessage(ex.getMessage())
				.toContact("admin@bank.com")
				.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
	}
	
	
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails>handle403(AccessDeniedException ex){
		
		ErrorDetails errorDetails= ErrorDetails.builder()
				.timeStamp(LocalDateTime.now())
				.errorCode(HttpStatus.FORBIDDEN.toString())
				.errorMessage(ex.getMessage())
				.toContact("admin@bank.com")
				.build();
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
	}
	
	

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails>handle400(MethodArgumentNotValidException ex){
		
		String errorMessage =ex.getBindingResult()
				.getAllErrors()
				.stream()
				.map(e -> e.getDefaultMessage())
				.collect(Collectors.joining(" ,"));
		
		ErrorDetails errorDetails= ErrorDetails.builder()
				.timeStamp(LocalDateTime.now())
				.errorCode(HttpStatus.BAD_REQUEST.toString())
				.errorMessage(errorMessage)
				.toContact("admin@bank.com")
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
	
	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ErrorDetails> handleInsufficientBalanceException(InsufficientBalanceException ex) {
	
	    ErrorDetails errorDetails = ErrorDetails.builder()
	            .timeStamp(LocalDateTime.now())
	            .errorCode(HttpStatus.BAD_REQUEST.toString())
	            .errorMessage(ex.getMessage())
	            .toContact("admin@bank.com")
	            .build();

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
	
	
	
	

}
