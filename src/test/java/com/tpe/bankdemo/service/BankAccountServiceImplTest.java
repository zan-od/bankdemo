package com.tpe.bankdemo.service;

import com.tpe.bankdemo.dao.BankAccountDAO;
import com.tpe.bankdemo.model.BankAccount;
import com.tpe.bankdemo.model.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class BankAccountServiceImplTest {

    @Mock
    private BankAccountDAO mockedBankAccountDAO;

    private BankAccount bankAccount;
    private Client client;
    private BankAccountService bankAccountService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        bankAccount = new BankAccount();
        client = new Client();

        bankAccountService = new BankAccountServiceImpl();
        ((BankAccountServiceImpl) bankAccountService).setBankAccountDAO(mockedBankAccountDAO);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void saveAccount_ShouldSaveToDatabase() {
        bankAccountService.saveAccount(bankAccount);

        verify(mockedBankAccountDAO, times(1)).save(bankAccount);
    }

    @Test
    public void listAllAccounts_ShouldReturnTheSameList() {
        List<BankAccount> list = new ArrayList<>();
        list.add(bankAccount);

        when(mockedBankAccountDAO.findAll()).thenReturn(list);

        assertTrue(bankAccountService.listAllAccounts().equals(list));
    }

    @Test
    public void listClientAccounts_ShouldReturnTheSameList() {
        List<BankAccount> list = new ArrayList<>();
        list.add(bankAccount);

        when(mockedBankAccountDAO.findByOwner(client)).thenReturn(list);

        assertTrue(bankAccountService.listClientAccounts(client).equals(list));
    }

    @Test
    public void getCurrentAmount_ShouldReturnTheSameValue() {
        bankAccount.setAmount(5);

        assertEquals(bankAccountService.getCurrentAmount(bankAccount), 5, 0);
    }

    @Test
    public void addAmount_ShouldIncrementTheValueAndSave() {
        bankAccount.setAmount(5);

        bankAccountService.addAmount(bankAccount, 3);

        assertEquals(bankAccount.getAmount(), 8, 0);
        verify(mockedBankAccountDAO, times(1)).save(bankAccount);
    }

    @Test(expected = IllegalStateException.class)
    public void addAmount_ShouldThrowException() {

        bankAccount.setAmount(5);

        bankAccountService.addAmount(bankAccount, -6);
        verify(mockedBankAccountDAO, times(0)).save(bankAccount);
    }
}