package com.BankApp.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO { 
	
    private String username;
    
   
	private String password;
	
	@Email(message = "{userDTO.email.invalid}")
	@NotNull(message = "{userDTO.email.absent}")
	private String email;
	
	@NotNull(message = "{userDTO.phone.adsent}")
	@Pattern(regexp = "[789][0-9]{9}", message = "{userDTO.phone.invalid}")
	private String phone;
	
	@NotBlank(message = "Address is required")
	@Size(min = 15, message = "Address must be at least 15 characters long")
	private String address;

}
