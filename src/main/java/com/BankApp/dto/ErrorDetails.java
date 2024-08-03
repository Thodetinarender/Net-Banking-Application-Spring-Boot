package com.BankApp.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDetails {
	private String errorMessage;
	private String errorCode;
	private String toContact;
	private LocalDateTime timeStamp;

	

}
