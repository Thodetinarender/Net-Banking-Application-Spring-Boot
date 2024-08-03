package com.BankApp.service;

import java.math.BigDecimal;
import java.util.List;

import com.BankApp.dto.AccountContactDetailDTO;
import com.BankApp.entities.Account;
import com.BankApp.entities.UserEntity;

public interface AccountService {
	public List<Account>getAll();
	public Account addAccount(Account account);
	
	public Account getById(int accountId);
	
	public Account deleteAccount(int accountId);
	
	public void transfer(int fromAccId, int toAccId, BigDecimal amount);

	public void deposit(int accId, BigDecimal amount);

	public void withdraw(int accId, BigDecimal amount);
	
	public void updateAccountContactDetails(AccountContactDetailDTO accountContactDetailDTO);
	
	public Account findByPhone(String phone);
	
	

}
