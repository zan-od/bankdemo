package com.tpe.bankdemo.controller;

import com.tpe.bankdemo.model.BankTransaction;
import com.tpe.bankdemo.service.BankAccountService;
import com.tpe.bankdemo.service.BankTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BankTransactionController {

    private BankTransactionService bankTransactionService;
    private BankAccountService bankAccountService;

    @Autowired
    public void setBankTransactionService(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }

    @Autowired
    public void setBankAccountService(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/transactions")
    public String transactions(Model model) {

        model.addAttribute("transactions", bankTransactionService.listTransactions());

        return "transactions";
    }

    @GetMapping("/transaction/add")
    public String addTransaction(Model model) {

        model.addAttribute("error", null);
        model.addAttribute("new_transaction", new BankTransaction());
        model.addAttribute("accounts", bankAccountService.listAllAccounts());

        return "add_transaction";
    }

    @PostMapping("/transaction/add")
    public String addTransaction(@ModelAttribute("new_transaction") BankTransaction transaction) {

        bankTransactionService.addTransaction(transaction);

        return "redirect:/transactions";
    }

}
