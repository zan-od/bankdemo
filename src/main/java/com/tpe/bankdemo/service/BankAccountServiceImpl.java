package com.tpe.bankdemo.service;

import com.tpe.bankdemo.dao.BankAccountDAO;
import com.tpe.bankdemo.model.BankAccount;
import com.tpe.bankdemo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountDAO bankAccountDAO;

    @Autowired
    public void setBankAccountDAO(BankAccountDAO bankAccountDAO) {
        this.bankAccountDAO = bankAccountDAO;
    }

    @Override
    public void saveAccount(BankAccount account) {
        bankAccountDAO.save(account);
    }

    @Override
    public List<BankAccount> listAllAccounts() {
        return bankAccountDAO.findAll();
    }

    @Override
    public List<BankAccount> listClientAccounts(Client client) {
        return bankAccountDAO.findByOwner(client);
    }

    @Override
    public double getCurrentAmount(BankAccount account) {
        return account.getAmount();
    }

    @Override
    public void addAmount(BankAccount account, double amount) {
        if (amount == 0) {
            return;
        }
        if (account == null) {
            return;
        }

        double currentAmount = account.getAmount();
        double newAmount = currentAmount + amount;
        if (newAmount < 0) {
            throw new IllegalStateException("Not enough money on account " + account + ": needed " + (-amount) + ", available " + currentAmount);
        }

        account.setAmount(newAmount);
        bankAccountDAO.save(account);
    }

    @Override
    public BankAccount getAccount(long id) {
        return bankAccountDAO.getOne(id);
    }
}
