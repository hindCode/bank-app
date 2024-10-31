package ma.formations.msa.accountservice.repository;

import ma.formations.msa.accountservice.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<BankAccount, String> {
}
