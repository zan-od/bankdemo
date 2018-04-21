package com.tpe.bankdemo.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bank_transactions")
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="sender_id")
    @Nullable
    private BankAccount sender;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="recipient_id")
    @Nullable
    private BankAccount recipient;

    @Column(name = "amount", nullable = false)
    private double amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BankAccount getSender() {
        return sender;
    }

    public void setSender(BankAccount sender) {
        this.sender = sender;
    }

    public BankAccount getRecipient() {
        return recipient;
    }

    public void setRecipient(BankAccount recipient) {
        this.recipient = recipient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
