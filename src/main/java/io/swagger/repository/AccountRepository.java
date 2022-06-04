package io.swagger.repository;


import io.swagger.model.entity.Account;
import io.swagger.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Query(value = "SELECT IBAN FROM Account ORDER BY IBAN DESC LIMIT 1", nativeQuery = true)
    String getLatestIban();

    
}
