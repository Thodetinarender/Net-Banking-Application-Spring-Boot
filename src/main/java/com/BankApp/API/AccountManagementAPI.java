package com.BankApp.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankApp.dto.AccountContactDetailDTO;
import com.BankApp.entities.Account;
import com.BankApp.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="api/accountmgt")
//@PreAuthorize("hasAuthority('ROLE_MGR')")
public class AccountManagementAPI {
	
	@Autowired
	private AccountService accountService;
	
	//-------create an new account----------
	
	//api/accountmgt/accounts
	@PostMapping(path="accounts")
	public ResponseEntity<Account> addAccount(@Valid @RequestBody Account account){
		
		accountService.addAccount(account);
		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}

	//-------update an existing account (email or phone) ----------
	//api/accountmgt/accounts
	@PutMapping(path="accounts")
	public ResponseEntity<String> updateAccount(@Valid @RequestBody AccountContactDetailDTO accountContactDetailDTO){
	   
		accountService.updateAccountContactDetails(accountContactDetailDTO);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body("contact details of customer"+accountContactDetailDTO.getAccId()+"is changed successfully");
	}

	//-------get all account----------
	//api/accountmgt/accounts
	@GetMapping(path="accounts")
	public List<Account> getAllAccount(){
		return accountService.getAll();
	}
	
	//-------get an specific account----------
	@GetMapping(path="accounts/{accountId}")
	public Account getAnAccountById(@PathVariable int accountId){
		return accountService.getById(accountId);
	}
	
	
	//-------delete an existing account----------
    @DeleteMapping(path="accounts/{accountId}")
	public ResponseEntity<Void> deleteAccountById(@PathVariable int accountId){
		 accountService.deleteAccount(accountId);
		 return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
    }
		
	
		

}
