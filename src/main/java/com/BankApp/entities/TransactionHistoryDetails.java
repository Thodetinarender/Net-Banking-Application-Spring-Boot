package com.BankApp.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="transaction_history_details_table")
@ToString
public class TransactionHistoryDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int fromAccId;
	private int toAccId;
	
	@Enumerated(EnumType.STRING)
	private TransactionType txType;
	private LocalDateTime dateTime;
	
	private  String authority;
	
	private BigDecimal amount;

	

	public TransactionHistoryDetails(int fromAccId, int toAccId, TransactionType txType, LocalDateTime dateTime,
			String authority, BigDecimal amount) {
		super();
		this.fromAccId = fromAccId;
		this.toAccId = toAccId;
		this.txType = txType;
		this.dateTime = dateTime;
		this.authority = authority;
		this.amount = amount;
	}
	
	
	
	
}
