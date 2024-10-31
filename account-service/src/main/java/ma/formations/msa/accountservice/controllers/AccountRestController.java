package ma.formations.msa.accountservice.controllers;

import ma.formations.msa.accountservice.clients.CustomerRestClient;
import ma.formations.msa.accountservice.entities.BankAccount;
import ma.formations.msa.accountservice.entities.Customer;
import ma.formations.msa.accountservice.repository.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountRestController {
    private final AccountRepository accountRepository;
    private final CustomerRestClient customerRestClient;

    public AccountRestController(AccountRepository accountRepository, CustomerRestClient customerRestClient) {
        this.accountRepository = accountRepository;
        this.customerRestClient = customerRestClient;
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity getBankAccountById(@PathVariable String accountId) {
        BankAccount bankAccount = accountRepository.findById(accountId).orElse(null);
        if (bankAccount == null) {
            return ResponseEntity.internalServerError().body(Map.of("errorMessage", "account not found"));
        }
        Customer customer = customerRestClient.getCustomerById(bankAccount.getCustomerId());
        bankAccount.setCustomer(customer);
        return ResponseEntity.ok(bankAccount);
    }

    @GetMapping("/accounts")
    public List<BankAccount> accountList() {
        return accountRepository.findAll();
    }
}