package com.BankApp.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BankApp.dto.UserContactDetailDTO;
import com.BankApp.dto.UserDTO;
import com.BankApp.entities.Account;
import com.BankApp.entities.UserEntity;
import com.BankApp.exceptions.ResourceNotFoundException;
import com.BankApp.repositories.AccountRepository;
import com.BankApp.repositories.UserRepository;
import com.BankApp.service.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private AccountRepository accountRepository;

	@Override
	public List<UserEntity> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void addUserEntity(UserEntity userEntity) {
		
		userRepository.save(userEntity);
	}
	

	@Override
	public void deleteUserEntity(UserEntity userEntity) {
		userRepository.delete(userEntity);
		
	}

	@Override
	public void updateContactDetails(UserContactDetailDTO userContactDetailDTO) {
		
		UserEntity userToUpdate = getById(userContactDetailDTO.getId());
		userToUpdate.setAddress(userContactDetailDTO.getAddress());
		userToUpdate.setPhone(userContactDetailDTO.getPhone());
		userToUpdate.setEmail(userContactDetailDTO.getEmail());
		
		userRepository.save(userToUpdate);
	}

	@Override
	public UserEntity findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	

	@Override
	public UserEntity getById(int id) {
		return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
	}
	
	
	@Override
    public UserEntity findByIdAndUsername(int id, String username) {
        return userRepository.findByIdAndUsername(id, username);
    }
	
	///////-----------------------------------------------------------------------////////////////////
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Override
	 public void addUserEntitys(UserDTO userDTO) {
	     // Create and set up the UserEntity
	     UserEntity userEntity = new UserEntity();
	     userEntity.setUsername(userDTO.getUsername());
	     userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
	     userEntity.setEmail(userDTO.getEmail());
	     userEntity.setPhone(userDTO.getPhone());
	     userEntity.setAddress(userDTO.getAddress());
	     userEntity.setRoles(List.of("ROLE_CLERK")); // or set roles based on your logic

	     // Save UserEntity first
	     userEntity = userRepository.save(userEntity);

	     // Create and set up the Account object
	     Account account = new Account();
	     account.setId(userEntity.getId()); // Use UserEntity ID as Account ID
	     account.setName(userDTO.getUsername()); // Set the account name as the username
	     account.setEmail(userDTO.getEmail());   // Set the account email
	     account.setPhone(userDTO.getPhone());   // Set the account phone
	     account.setBalance(BigDecimal.valueOf(500)); // Set the default balance to 500
	     account.setUser(userEntity);            // Associate the account with the user

	     // Save Account
	     accountRepository.save(account);
	 }

	   

	    public boolean isUserExistByEmail(String email) {
	        return userRepository.existsByEmail(email);
	    }

	    public boolean isUserExistByPhone(String phone) {
	        return userRepository.existsByPhone(phone);
	    }

	    @Override
	    public int getAuthenticatedUserId(String username) {
	        UserEntity userEntity = userRepository.findByUsername(username); // Ensure this method returns a single unique user
	        return userEntity != null ? userEntity.getId() : null;
	    }
	    
	    
	    @Override
	    public List<UserEntity> findUsersByIds(Set<Integer> transactionUserIds) {
	        return userRepository.findByIdIn(transactionUserIds);
	    }

}
