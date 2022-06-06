package io.swagger.repository;


import io.swagger.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Query(value = "SELECT IBAN FROM Account ORDER BY IBAN DESC LIMIT 1", nativeQuery = true)
    String getLatestIban();

    Account findAccountByIBAN(String IBAN);

    Account getAccountByIBAN(String IBAN);
}
