package com.BankApp.entities;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="account_table")
@ToString
public class Account {
	
	@Id
	private int id;
	
	@NotNull(message = "{account.name.adsent}")
	@Pattern(regexp = "[A-Za-z]+( [A-Za-z]+)*", message = "{account.name.invalid}")
	private String name;
	
	@NotNull(message = "{account.balance.adsent}")
	@Range(min = 10, max = 200000, message = "{account.balance.invalid}")
	private BigDecimal balance;
	
	@NotNull(message = "{account.phone.adsent}")
	@Pattern(regexp = "[789][0-9]{9}", message = "{account.phone.invalid}")
	private String phone;
	
	@Email(message = "{account.email.invalid}")
	@NotNull(message = "{account.email.absent}")
	private String email;

	@ManyToOne
	private UserEntity user;
	
	public Account(String name, BigDecimal balance, String phone, String email) {
		super();
		this.name = name;
		this.balance = balance;
		this.phone = phone;
		this.email = email;
		
	}
	
}
