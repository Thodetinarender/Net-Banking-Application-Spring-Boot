package com.BankApp.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_table")
@ToString
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	
	@JsonIgnore
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="user_roles")
	private List<String>roles = new ArrayList<>();
	
	private String address;
	private String phone;
	private String email;
	
	@OneToMany(mappedBy = "user")
	private List<Account> accounts;
	
	
	public UserEntity(String username, String password, List<String> roles, String address, String phone, String email) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.address = address;
		this.phone = phone;
		this.email = email;
		
	}
	
	
	

}
