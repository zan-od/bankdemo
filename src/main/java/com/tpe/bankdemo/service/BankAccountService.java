package com.tpe.bankdemo.service;

import com.tpe.bankdemo.model.BankAccount;
import com.tpe.bankdemo.model.Client;

import java.util.List;

public interface BankAccountService {
    void saveAccount(BankAccount account);

    List<BankAccount> listAllAccounts();

    List<BankAccount> listClientAccounts(Client client);

    double getCurrentAmount(BankAccount account);

    void addAmount(BankAccount account, double amount);
}
