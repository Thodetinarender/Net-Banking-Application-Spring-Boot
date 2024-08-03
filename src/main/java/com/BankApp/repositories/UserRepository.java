package com.BankApp.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BankApp.dto.UserDTO;
import com.BankApp.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	public UserEntity findByUsername(String username);

	public void save(UserDTO userDTO);

	public boolean existsByPhone(String phone);

	public boolean existsByEmail(String email);
	
	UserEntity findByIdAndUsername(int id, String username);

	public List<UserEntity> findByIdIn(Set<Integer> transactionUserIds);

	
}
