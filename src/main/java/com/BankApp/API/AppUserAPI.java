package com.BankApp.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankApp.entities.UserEntity;
import com.BankApp.service.UserService;

@RestController
@RequestMapping(path="api/usermgt")
//@PreAuthorize("hasAuthority('ROLE_MGR')")
public class AppUserAPI {
	
	@Autowired
	private UserService userService;
	
	//get All the user( app user)
	//api/usermgt/users
	@GetMapping(path="users")
	public List<UserEntity>getAll(){
		return userService.findAll();
	}
	
	//api/usermgt/users/{id}
	@GetMapping(path = "users/{id}")
	public UserEntity getUser(@PathVariable int id) {
		
		return userService.getById(id);
	}
	//api/usermgt/users
	@PostMapping(path = "users")
	public ResponseEntity<String> registerNewUser(@RequestBody UserEntity userEntity){
		userService.addUserEntity(userEntity);
		return ResponseEntity.status(HttpStatus.CREATED).body("new Bank user is created");
		
	}
	
}
