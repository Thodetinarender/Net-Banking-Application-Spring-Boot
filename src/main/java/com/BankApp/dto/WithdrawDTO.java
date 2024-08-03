package com.BankApp.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawDTO {
	
	@NotNull(message = "{withdrawDTO.accId.adsent}")
	private Integer accId;
	
	@NotNull(message = "{withdrawDTO.amount.adsent}")
	@Range(min = 10, max = 100000, message = "{withdrawDTO.amount.invalid}")
	private BigDecimal amount;

}
