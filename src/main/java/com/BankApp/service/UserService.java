package com.BankApp.service;

import java.util.List;
import java.util.Set;

import com.BankApp.dto.UserContactDetailDTO;
import com.BankApp.dto.UserDTO;
import com.BankApp.entities.Account;
import com.BankApp.entities.UserEntity;

public interface UserService {
	
	public List<UserEntity>findAll();
	public UserEntity getById(int id);
	public UserEntity findByUsername(String username);
	public void addUserEntity(UserEntity userEntity);
	public void deleteUserEntity(UserEntity userEntity);
	
	 UserEntity findByIdAndUsername(int id, String username);
	
	public void updateContactDetails(UserContactDetailDTO userContactDetailDTO);
	public boolean isUserExistByPhone(String phone);
	public boolean isUserExistByEmail(String email);
	public void addUserEntitys(UserDTO userDTO);
	public int getAuthenticatedUserId(String username);
	public List<UserEntity> findUsersByIds(Set<Integer> transactionUserIds);

}
