package com.BankApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserContactDetailDTO {
	
	@NotNull(message = "{accountContactDetailDTO.id.adsent}")
	private int id;
	
	@Email(message = "{accountContactDetailDTO.email.invalid}")
	@NotNull(message = "{accountContactDetailDTO.email.absent}")
	private String email;
	
	@NotNull(message = "{accountContactDetailDTO.phone.adsent}")
	@Pattern(regexp = "[789][0-9]{9}", message = "{accountContactDetailDTO.phone.invalid}")
	private String phone;
	
	private String address;

}
