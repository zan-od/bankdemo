package com.tpe.bankdemo.service;

import com.tpe.bankdemo.dao.BankTransactionDAO;
import com.tpe.bankdemo.model.BankAccount;
import com.tpe.bankdemo.model.BankTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class BankTransactionServiceImplTest {

    @Mock
    private BankTransactionDAO mockedBankTransactionDAO;

    @Mock
    private BankAccountService mockedBankAccountService;

    private BankTransactionService bankTransactionService;
    private BankTransaction bankTransaction;
    private Date currentDate = new Date();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        bankTransaction = new BankTransaction();

        bankTransactionService = new BankTransactionServiceImpl();
        ((BankTransactionServiceImpl) bankTransactionService).setBankAccountService(mockedBankAccountService);
        ((BankTransactionServiceImpl) bankTransactionService).setBankTransactionDAO(mockedBankTransactionDAO);
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test(expected = IllegalStateException.class)
    public void addTransaction_ShouldThrowException() {
        BankAccount account1 = new BankAccount();
        //BankAccount account2 = new BankAccount();

        doThrow(IllegalStateException.class).when(mockedBankAccountService).addAmount(account1, -10);

        bankTransaction.setAmount(10);
        bankTransaction.setSender(account1);
        bankTransactionService.addTransaction(bankTransaction);
    }

    @Test
    public void addTransaction_CheckAmounts() {
        BankAccount account1 = new BankAccount();
        BankAccount account2 = new BankAccount();

        bankTransaction.setAmount(5);
        bankTransaction.setSender(account1);
        bankTransaction.setRecipient(account2);

        bankTransactionService.addTransaction(bankTransaction);

        verify(mockedBankAccountService, times(1)).addAmount(account1, -5);
        verify(mockedBankAccountService, times(1)).addAmount(account2, 5);
        verify(mockedBankTransactionDAO, times(1)).save(bankTransaction);
    }

    @Test
    public void listTransactions_ShouldReturnTheSameList() {
        List<BankTransaction> list = new ArrayList<>();
        list.add(bankTransaction);

        when(mockedBankTransactionDAO.findAll()).thenReturn(list);

        assertTrue(bankTransactionService.listTransactions().equals(list));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findTransactions_CheckIfBDateIsNull() {
        bankTransactionService.findTransactions(null, currentDate, 0L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findTransactions_CheckIfEDateIsNull() {
        bankTransactionService.findTransactions(currentDate, null, 0L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findTransactions_CheckIfClientIdIsNull() {
        bankTransactionService.findTransactions(currentDate, currentDate, null);
    }

    public void findTransactions_WithoutClient() {
        bankTransactionService.findTransactions(currentDate, currentDate, 0L);

        verify(mockedBankTransactionDAO, times(1)).findByDateBetween(currentDate, currentDate);
    }

    public void findTransactions_WithClient() {
        bankTransactionService.findTransactions(currentDate, currentDate, 1L);

        verify(mockedBankTransactionDAO, times(1)).findByDateBetweenAndClient(currentDate, currentDate, 1L);
    }
}