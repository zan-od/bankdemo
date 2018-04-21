package com.tpe.bankdemo.controller;

import com.tpe.bankdemo.model.BankAccount;
import com.tpe.bankdemo.model.Client;
import com.tpe.bankdemo.service.BankAccountService;
import com.tpe.bankdemo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BankAccountController {

    private BankAccountService bankAccountService;
    ClientService clientService;

    @Autowired
    public void setBankAccountService(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/client/{clientId}/accounts")
    public String clients(@PathVariable("clientId") Long clientId, Model model) {

        Client client = clientService.getClient(clientId);
        List<BankAccount> accounts = bankAccountService.listClientAccounts(client);

        model.addAttribute("error", null);
        model.addAttribute("new_account", new BankAccount());
        model.addAttribute("client", client);
        model.addAttribute("accounts", accounts);

        return "bank_accounts";
    }

    @PostMapping("/client/{clientId}/account/add")
    public String addClient(@PathVariable("clientId") Long clientId, @ModelAttribute("new_account") BankAccount account) {

        Client client = clientService.getClient(clientId);
//        if (client==null){
//            throw IllegalArgumentException(String.format("Client not found by id {1}", clientId));
//        }

        account.setOwner(client);
        bankAccountService.saveAccount(account);

        return String.format("redirect:/client/%d/accounts", clientId);
    }

}
