package com.tpe.bankdemo.service;

import com.tpe.bankdemo.dao.BankTransactionDAO;
import com.tpe.bankdemo.model.BankTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BankTransactionServiceImpl implements BankTransactionService {

    private BankTransactionDAO bankTransactionDAO;
    private BankAccountService bankAccountService;

    @Autowired
    public void getBankTransactionDAO(BankTransactionDAO bankTransactionDAO) {
        this.bankTransactionDAO = bankTransactionDAO;
    }

    @Autowired
    public void getBankAccountService(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public void addTransaction(BankTransaction transaction) {

        transaction.setDate(new Date());

        bankAccountService.addAmount(transaction.getSender(), -transaction.getAmount());
        bankAccountService.addAmount(transaction.getRecipient(), transaction.getAmount());

        bankTransactionDAO.save(transaction);
    }

    @Override
    public List<BankTransaction> listTransactions() {
        return bankTransactionDAO.findAll();
    }
}
