package com.BankApp;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.BankApp.entities.Account;
import com.BankApp.entities.UserEntity;
import com.BankApp.service.AccountService;
import com.BankApp.service.UserService;

@SpringBootApplication
public class BankAppApplication implements CommandLineRunner {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(BankAppApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        addUserEntities(passwordEncoder());
//        addAccounts();
//    }

//    private void addUserEntities(PasswordEncoder passwordEncoder) {
//        userService.addUserEntity(new UserEntity("raj", passwordEncoder.encode("raj123"), List.of("ROLE_MGR", "ROLE_CLERK"), "A 31 Delhi", "8987487475", "raj@gmail.com"));
//        userService.addUserEntity(new UserEntity("ekta", passwordEncoder.encode("ekta123"), List.of("ROLE_CLERK"), "M 2 Noida", "9988764736", "ekta@gmail.com"));
//    }
//
//    private void addAccounts() {
//        accountService.addAccount(new Account("umesh", BigDecimal.valueOf(1000), "9908635735", "umesh@gmail.com"));
//        accountService.addAccount(new Account("sumit", BigDecimal.valueOf(1000), "9747946946", "sumit@gmail.com"));
//        System.out.println("Accounts are added");
//    }

  
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
