package com.BankApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BankApp.entities.TransactionHistoryDetails;
import com.BankApp.entities.TransactionType;
import com.BankApp.repositories.TransactionHistoryRepository;
import com.BankApp.service.TransactionHistoryService;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

	@Autowired
	private TransactionHistoryRepository transactionHistoryRepository;
	
	@Override
	public void addTransactionHistoryDeatil(TransactionHistoryDetails transactionHistoryDetails) {
		
		transactionHistoryRepository.save(transactionHistoryDetails);
	}

	@Override
	public List<TransactionHistoryDetails> getAll() {
		
		return transactionHistoryRepository.findAll();
	}

	@Override
	public List<TransactionHistoryDetails> findByFromAccIdOrToAccId(int accId) {
		return transactionHistoryRepository.findByFromAccIdOrToAccId(accId, accId) ;
	}
	
	@Override
	public List<TransactionHistoryDetails> findByFromAccIdOrToAccIdAndTxType(int accId, TransactionType txType) {
		return transactionHistoryRepository.findByFromAccIdOrToAccIdAndTxType(accId, txType);
	}

}
