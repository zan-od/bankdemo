package com.tpe.bankdemo.controller;

import com.tpe.bankdemo.model.Client;
import com.tpe.bankdemo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping({"/clients", "/"})
    public String clients(Model model) {

        List<Client> clients = clientService.listClients();

        model.addAttribute("error", null);
        model.addAttribute("new_client", new Client());
        model.addAttribute("clients", clients);
        model.addAttribute("clientAmounts", getClientTotalAmounts(clients));

        return "clients";
    }

    @PostMapping("/client/add")
    public String addClient(@ModelAttribute("new_client") Client client) {

        clientService.saveClient(client);

        return "redirect:/clients";
    }

    private Map<Client, Double> getClientTotalAmounts(List<Client> clients) {
        Map<Client, Double> totalAmounts = new HashMap<>();
        for (Client client : clients) {
            totalAmounts.put(client, clientService.getClientTotalAmount(client));
        }
        return totalAmounts;
    }

}
