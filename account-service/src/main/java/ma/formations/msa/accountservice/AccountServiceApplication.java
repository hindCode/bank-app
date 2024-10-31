package ma.formations.msa.accountservice;

import ma.formations.msa.accountservice.clients.CustomerRestClient;
import ma.formations.msa.accountservice.entities.AccountType;
import ma.formations.msa.accountservice.entities.BankAccount;
import ma.formations.msa.accountservice.entities.Customer;
import ma.formations.msa.accountservice.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class AccountServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(AccountRepository accountRepository, CustomerRestClient customerRestClient) {
        return args -> {
            List<Customer> allCustomers = customerRestClient.getAllCustomers();
            allCustomers.forEach(customer -> {
                for (AccountType accountType : AccountType.values()) {
                    BankAccount bankAccount = BankAccount.builder()
                            .id(UUID.randomUUID().toString())
                            .balance(Math.random() * 80000)
                            .createdAt(LocalDate.now())
                            .type(accountType)
                            .currency("MAD")
                            .customerId(customer.getId())
                            .build();
                    accountRepository.save(bankAccount);
                }
            });
        };
    }

}
