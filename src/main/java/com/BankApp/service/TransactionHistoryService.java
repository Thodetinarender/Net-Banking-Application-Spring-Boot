package com.BankApp.service;

import java.util.List;

import com.BankApp.entities.TransactionHistoryDetails;
import com.BankApp.entities.TransactionType;

public interface TransactionHistoryService {

	public void addTransactionHistoryDeatil(TransactionHistoryDetails transactionHistoryDetails);
	public List<TransactionHistoryDetails> getAll();
	public List<TransactionHistoryDetails>findByFromAccIdOrToAccId(int accId);
	public List<TransactionHistoryDetails>findByFromAccIdOrToAccIdAndTxType(int accId, TransactionType txType);


}
