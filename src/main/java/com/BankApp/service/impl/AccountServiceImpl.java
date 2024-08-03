package com.BankApp.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BankApp.config.InsufficientBalanceException;
import com.BankApp.dto.AccountContactDetailDTO;
import com.BankApp.entities.Account;
import com.BankApp.exceptions.ResourceNotFoundException;
import com.BankApp.repositories.AccountRepository;
import com.BankApp.repositories.UserRepository;
import com.BankApp.service.AccountService;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class AccountServiceImpl implements AccountService{
	
	
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<Account> getAll() {
		return accountRepository.findAll();
	}

	@Override
	public Account addAccount(Account account) {
		
		accountRepository.save(account);
		return account ;
	}

	@Override
	public Account getById(int accountId) {
		return accountRepository.findById(accountId).orElseThrow(
				()-> new ResourceNotFoundException("bank account with id "+ accountId + " is not found"));
	}

	@Override
	public Account deleteAccount(int accountId) {
		Account accountToDelete = getById(accountId);
		accountRepository.delete(accountToDelete);
		return accountToDelete;
	}

	@Override
	public void transfer(int fromAccId, int toAccId, BigDecimal amount) {
		
		long start = System.currentTimeMillis();
		
		Account fromAcc = getById(fromAccId);
		Account toAcc = getById(toAccId);
		
		 // Check if the balance is sufficient
	    if (fromAcc.getBalance().compareTo(amount) < 0) {
	        throw new InsufficientBalanceException("Insufficient balance in account id: " + fromAccId);
	    }
		
		fromAcc.setBalance(fromAcc.getBalance().subtract(amount));
		toAcc.setBalance(toAcc.getBalance().add(amount));
		
		accountRepository.save(fromAcc);
		accountRepository.save(toAcc);
		
		long end = System.currentTimeMillis();
		
		log.info("time taken to excute transfer method " +(end-start)+ " ms" );
		
	}

	@Override
	public void deposit(int accId, BigDecimal amount) {
		Account acc = getById(accId);
		acc.setBalance(acc.getBalance().add(amount));
		accountRepository.save(acc);
	}

	@Override
	public void withdraw(int accId, BigDecimal amount) {
		Account acc = getById(accId);
		 // Check if the balance is sufficient
	    if (acc.getBalance().compareTo(amount) < 0) {
	        throw new InsufficientBalanceException("Insufficient balance in account id: " + accId);
	    }
		
		acc.setBalance(acc.getBalance().subtract(amount));
		accountRepository.save(acc);
		
	}

	@Override
	public void updateAccountContactDetails(AccountContactDetailDTO accountContactDetailDTO) {
		Account acc = getById(accountContactDetailDTO.getAccId());
		acc.setEmail(accountContactDetailDTO.getEmail());
		acc.setPhone(accountContactDetailDTO.getPhone());
		
		accountRepository.save(acc);
	}
	
	@Override
    public Account findByPhone(String phone) {
        return accountRepository.findByPhone(phone);
    }

}
