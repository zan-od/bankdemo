package com.tpe.bankdemo.dao;

import com.tpe.bankdemo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDAO extends JpaRepository<Client, Long> {
}
