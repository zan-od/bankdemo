package com.tpe.bankdemo.service;

import com.tpe.bankdemo.model.BankTransaction;

import java.util.List;

public interface BankTransactionService {
    void addTransaction(BankTransaction transaction);

    List<BankTransaction> listTransactions();
}
