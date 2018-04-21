package com.tpe.bankdemo.service;

import com.tpe.bankdemo.model.Client;

import java.util.List;

public interface ClientService {
    void saveClient(Client client);

    Client getClient(Long id);

    List<Client> listClients();

    double getClientTotalAmount(Client client);
}
