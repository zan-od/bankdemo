package com.tpe.bankdemo.service;

import com.tpe.bankdemo.dao.ClientDAO;
import com.tpe.bankdemo.model.BankAccount;
import com.tpe.bankdemo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientDAO clientDAO;
    private BankAccountService bankAccountService;

    @Autowired
    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Autowired
    public void setBankAccountService(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public void saveClient(Client client) {
        clientDAO.save(client);
    }

    @Override
    public Client getClient(Long id) {
        return clientDAO.getOne(id);
    }

    @Override
    public List<Client> listClients() {
        return clientDAO.findAll();
    }

    @Override
    public double getClientTotalAmount(Client client) {
        double sum = 0;
        for (BankAccount account : bankAccountService.listClientAccounts(client)) {
            sum = sum + account.getAmount();
        }

        return sum;
    }
}
