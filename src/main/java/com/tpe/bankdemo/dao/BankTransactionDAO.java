package com.tpe.bankdemo.dao;

import com.tpe.bankdemo.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BankTransactionDAO extends JpaRepository<BankTransaction, Long> {

    @Query(value = "SELECT DISTINCT * FROM (SELECT tr.* FROM bank_transactions as tr " +
            "INNER JOIN (SELECT ac.id as id FROM bank_accounts as ac WHERE ac.owner_id=:client_id) as ac " +
            "ON tr.sender_id=ac.id OR tr.recipient_id=ac.id " +
            "WHERE (tr.date>=:bDate) AND (tr.date<=:eDate)) as res " +
            "ORDER BY res.date", nativeQuery = true)
    List<BankTransaction> findByDateBetweenAndClient(@Param("bDate") Date bDate, @Param("eDate") Date eDate, @Param("client_id") Long clientId);

    List<BankTransaction> findByDateBetween(Date bDate, Date eDate);
}
