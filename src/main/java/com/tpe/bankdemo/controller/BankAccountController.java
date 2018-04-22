package com.tpe.bankdemo.controller;

import com.tpe.bankdemo.model.BankAccount;
import com.tpe.bankdemo.model.BankTransaction;
import com.tpe.bankdemo.model.Client;
import com.tpe.bankdemo.service.BankAccountService;
import com.tpe.bankdemo.service.BankTransactionService;
import com.tpe.bankdemo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BankAccountController {

    private BankAccountService bankAccountService;
    private BankTransactionService bankTransactionService;
    private ClientService clientService;

    @Autowired
    public void setBankAccountService(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setBankTransactionService(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
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
        if (client == null)
            throw new IllegalArgumentException(String.format("Client not found by id %d", clientId));

        account.setOwner(client);
        bankAccountService.saveAccount(account);

        return String.format("redirect:/client/%d/accounts", clientId);
    }

    @GetMapping("/account/{accountId}/transactions")
    public String accountTransactions(@PathVariable("accountId") Long accountId, Model model) {

        BankAccount account = bankAccountService.getAccount(accountId);
        if (account == null)
            throw new IllegalArgumentException(String.format("Account not found by id %d", accountId));

        List<BankTransaction> transactions = bankTransactionService.listAccountTransactions(account);

        Map<BankTransaction, Double> amountsAfter = new HashMap<>();
        double currentAmount = 0;
        for (BankTransaction transaction : transactions) {
            currentAmount += transaction.getAmount();
            amountsAfter.put(transaction, currentAmount);
        }

        model.addAttribute("error", null);
        model.addAttribute("account", account);
        model.addAttribute("transactions", transactions);
        model.addAttribute("amountsAfter", amountsAfter);

        return "account_transactions";
    }

}
