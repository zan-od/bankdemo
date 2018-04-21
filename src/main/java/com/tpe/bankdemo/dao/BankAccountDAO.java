package com.tpe.bankdemo.dao;

import com.tpe.bankdemo.model.BankAccount;
import com.tpe.bankdemo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountDAO extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findByOwner(Client owner);
}
