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
public class DepositDTO {

	@NotNull(message = "{depositDTO.accId.adsent}")
	private Integer accId;
	
	@NotNull(message = "{depositDTO.username.adsent}")
	private String username;
	    
	@NotNull(message = "{depositDTO.phone.adsent}")
	@Pattern(regexp = "[789][0-9]{9}", message = "{depositDTO.phone.invalid}")
	private String phone;
	
	@NotNull(message = "{depositDTO.amount.adsent}")
	@Range(min = 10, max = 200000, message = "{depositDTO.amount.invalid}")
	private BigDecimal amount;
	
	

}
