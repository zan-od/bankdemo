package com.tpe.bankdemo.service;

import com.tpe.bankdemo.dao.BankTransactionDAO;
import com.tpe.bankdemo.model.BankAccount;
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
    public void setBankTransactionDAO(BankTransactionDAO bankTransactionDAO) {
        this.bankTransactionDAO = bankTransactionDAO;
    }

    @Autowired
    public void setBankAccountService(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public void addTransaction(BankTransaction transaction) throws IllegalStateException {

        transaction.setDate(new Date());

        bankAccountService.addAmount(transaction.getSender(), -transaction.getAmount());
        bankAccountService.addAmount(transaction.getRecipient(), transaction.getAmount());

        bankTransactionDAO.save(transaction);
    }

    @Override
    public List<BankTransaction> listTransactions() {
        return bankTransactionDAO.findAll();
    }

    @Override
    public List<BankTransaction> listAccountTransactions(BankAccount account) {
        return bankTransactionDAO.findBySenderOrRecipientOrderByDate(account, account);
    }

    @Override
    public List<BankTransaction> findTransactions(Date bDate, Date eDate, Long clientId) throws IllegalArgumentException {
        if (bDate == null) throw new IllegalArgumentException("Date from mustn't be null!");
        if (eDate == null) throw new IllegalArgumentException("Date till mustn't be null!");
        if (clientId == null) throw new IllegalArgumentException("Client ID mustn't be null!");

        List<BankTransaction> transactions;
        if (clientId > 0) {
            transactions = bankTransactionDAO.findByDateBetweenAndClient(bDate, eDate, clientId);
        } else {
            transactions = bankTransactionDAO.findByDateBetween(bDate, eDate);
        }

        return transactions;
    }
}
