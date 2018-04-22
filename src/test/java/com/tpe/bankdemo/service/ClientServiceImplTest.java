package com.tpe.bankdemo.service;

import com.tpe.bankdemo.dao.ClientDAO;
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

public class ClientServiceImplTest {

    @Mock
    private ClientDAO mockedClientDAO;

    @Mock
    private BankAccountService mockedBankAccountService;

    private Client client;
    private ClientService clientService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        client = new Client();

        clientService = new ClientServiceImpl();
        ((ClientServiceImpl) clientService).setBankAccountService(mockedBankAccountService);
        ((ClientServiceImpl) clientService).setClientDAO(mockedClientDAO);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void saveClient_ShouldSaveToDatabase() {
        clientService.saveClient(client);

        verify(mockedClientDAO, times(1)).save(client);
    }

    @Test
    public void getClient_ShouldReadFromDatabase() {

        when(mockedClientDAO.getOne(1L)).thenReturn(client);

        Client localClient = clientService.getClient(1L);

        verify(mockedClientDAO, times(1)).getOne(1L);
        assertEquals(localClient, client);
    }

    @Test
    public void listClients_ShouldReturnTheSameList() {
        List<Client> list = new ArrayList<>();
        list.add(client);

        when(mockedClientDAO.findAll()).thenReturn(list);

        assertTrue(clientService.listClients().equals(list));
    }

    @Test
    public void getClientTotalAmount_ShouldReturnTheSum() {
        BankAccount account1 = new BankAccount();
        account1.setAmount(3);

        BankAccount account2 = new BankAccount();
        account2.setAmount(5);

        List<BankAccount> list = new ArrayList<>();
        list.add(account1);
        list.add(account2);

        when(mockedBankAccountService.listClientAccounts(client)).thenReturn(list);

        assertEquals(clientService.getClientTotalAmount(client), 8, 0);
    }
}