package com.tpe.bankdemo.controller;

import com.tpe.bankdemo.model.BankTransaction;
import com.tpe.bankdemo.service.BankAccountService;
import com.tpe.bankdemo.service.BankTransactionService;
import com.tpe.bankdemo.service.ClientService;
import com.tpe.bankdemo.validator.BankTransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class BankTransactionController {

    private BankTransactionService bankTransactionService;
    private BankAccountService bankAccountService;
    private ClientService clientService;
    private BankTransactionValidator bankTransactionValidator;

    @Autowired
    public void setBankTransactionService(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }

    @Autowired
    public void setBankAccountService(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setBankTransactionValidator(BankTransactionValidator bankTransactionValidator) {
        this.bankTransactionValidator = bankTransactionValidator;
    }

    @GetMapping("/transactions")
    public String transactions(Model model) {

        model.addAttribute("transactions", bankTransactionService.listTransactions());
        model.addAttribute("clients", clientService.listClients());
        model.addAttribute("bdate", new Date());
        model.addAttribute("edate", new Date());
        model.addAttribute("client_id", 0);

        return "transactions";
    }

    @GetMapping(value = "/transactions/filter", params = {"bdate", "edate", "client_id"})
    public String filterTransactions(Model model, @RequestParam("bdate") String bDate, @RequestParam("edate") String eDate,
                                     @RequestParam("client_id") Long clientId) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        boolean hasError = false;
        StringBuilder error = new StringBuilder();
        Date bPeriod = null;
        Date ePeriod = null;

        try {
            bPeriod = sdf.parse(bDate);
        } catch (ParseException e) {
            error.append("Error parsing date from: ").append(bDate).append(". ");
            hasError = true;
        }

        try {
            ePeriod = sdf.parse(eDate);
        } catch (ParseException e) {
            error.append("Error parsing date till: ").append(eDate).append(". ");
            hasError = true;
        }

        List<BankTransaction> transactions;
        if (hasError) {
            transactions = new ArrayList<>();
        } else {
            transactions = bankTransactionService.findTransactions(bPeriod, atEndOfDay(ePeriod), clientId);
        }

        model.addAttribute("transactions", transactions);
        model.addAttribute("clients", clientService.listClients());
        model.addAttribute("filterError", hasError ? error.toString() : null);
        model.addAttribute("bdate", bPeriod);
        model.addAttribute("edate", ePeriod);
        model.addAttribute("client_id", clientId);

        return "transactions";
    }

    private Date atEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    @GetMapping("/transaction/add")
    public String addTransaction(Model model) {

        fillAddTransactionModel(model, new BankTransaction(), null);

        return "add_transaction";
    }

    private void fillAddTransactionModel(Model model, BankTransaction transaction, String error) {
        model.addAttribute("error", error);
        model.addAttribute("new_transaction", transaction);
        model.addAttribute("accounts", bankAccountService.listAllAccounts());
    }

    @PostMapping("/transaction/add")
    public String addTransaction(Model model, @Valid @ModelAttribute("new_transaction") BankTransaction transaction, BindingResult bindingResult) {

        bankTransactionValidator.validate(transaction, bindingResult);
        if (bindingResult.hasErrors()) {
            fillAddTransactionModel(model, transaction, null);
            return "add_transaction";
        }

        try {
            bankTransactionService.addTransaction(transaction);
        } catch (IllegalStateException e) {
            fillAddTransactionModel(model, transaction, e.getLocalizedMessage());
            return "add_transaction";
        }

        return "redirect:/transactions";
    }

}
