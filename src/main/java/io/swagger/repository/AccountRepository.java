package io.swagger.repository;


import io.swagger.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Query(value = "SELECT IBAN FROM Account ORDER BY IBAN DESC LIMIT 1", nativeQuery = true)
    String getLatestIban();

    Account findAccountByIBAN(String IBAN);

    Account getAccountByIBAN(String IBAN);

    List<Account> findAllByUser(UUID UserID);

    //@Query("SELECT a FROM Account a WHERE a.user.firstName LIKE ?1 OR a.user.lastName LIKE ?1 OR a.user.firstName a.user.lastName")
    //List<Account> findAllByUserName(String name);

    //List<Account> findAllByUser_FirstNameOrUser_LastName(String name);
}
