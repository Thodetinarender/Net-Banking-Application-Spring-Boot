package com.BankApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BankApp.entities.TransactionHistoryDetails;
import com.BankApp.entities.TransactionType;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistoryDetails, Integer>{
	
	public List<TransactionHistoryDetails>findByFromAccIdOrToAccId(int fromAccId, int toAccId);
	@Query("SELECT t FROM TransactionHistoryDetails t WHERE (t.fromAccId = :accountId OR t.toAccId = :accountId) AND t.txType = :txType")
	List<TransactionHistoryDetails> findByFromAccIdOrToAccIdAndTxType(@Param("accountId") int accountId, @Param("txType") TransactionType txType);

}
