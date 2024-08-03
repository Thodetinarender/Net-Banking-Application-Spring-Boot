package com.BankApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BankApp.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	Account findByPhone(String phone);
}
