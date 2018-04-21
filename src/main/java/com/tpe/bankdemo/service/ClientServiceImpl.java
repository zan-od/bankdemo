package com.tpe.bankdemo.service;

import com.tpe.bankdemo.dao.ClientDAO;
import com.tpe.bankdemo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    final ClientDAO clientDAO;

    @Autowired
    public ClientServiceImpl(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
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
        return -1.0;
    }
}
