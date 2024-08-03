package com.BankApp.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {
	
	
	@NotNull(message = "{transferDTO.phone.adsent}")
	@Pattern(regexp = "[789][0-9]{9}", message = "{transferDTO.phone.invalid}")
	private String phone;
	
	
	@NotNull(message ="{transferDTO.amount.adsent}")
	@Range(min = 10, max = 200000, message ="{transferDTO.amount.invalid}")
	private BigDecimal amount;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@NotNull(message = "{transferDTO.phone.adsent}")
//	@Pattern(regexp = "[789][0-9]{9}", message = "{transferDTO.phone.invalid}")
//	private String phone;
//	
//	@NotNull(message ="{transferDTO.accId.adsent}")
//	private Integer fromAccId;
//
//	@NotNull(message ="{transferDTO.accId.adsent}")
//	private Integer toAccId;
//	
//	@NotNull(message ="{transferDTO.amount.adsent}")
//	@Range(min = 10, max = 200000, message ="{transferDTO.amount.invalid}")
//	private BigDecimal amount;
	

}
