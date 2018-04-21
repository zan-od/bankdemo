package com.tpe.bankdemo.dao;

import com.tpe.bankdemo.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionDAO extends JpaRepository<BankTransaction, Long> {
}
