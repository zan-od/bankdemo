package com.tpe.bankdemo.service;

import com.tpe.bankdemo.dao.BankAccountDAO;
import com.tpe.bankdemo.model.BankAccount;
import com.tpe.bankdemo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    final BankAccountDAO bankAccountDAO;

    @Autowired
    public BankAccountServiceImpl(BankAccountDAO bankAccountDAO) {
        this.bankAccountDAO = bankAccountDAO;
    }

    @Override
    public void saveAccount(BankAccount account) {
        bankAccountDAO.save(account);
    }

    @Override
    public List<BankAccount> listClientAccounts(Client client) {
        return bankAccountDAO.findByOwner(client);
    }

    @Override
    public double getCurrentAmount(BankAccount account) {
        return account.getAmount();
    }
}
