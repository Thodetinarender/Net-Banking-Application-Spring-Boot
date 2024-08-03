package com.BankApp.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverDTO {
	
	@NotNull(message = "{receiverDTO.phone.adsent}")
	@Pattern(regexp = "[789][0-9]{9}", message = "{receiverDTO.phone.invalid}")
	private String phone;

}
