package io.swagger.repository;


import io.swagger.model.entity.Account;
import io.swagger.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface accountRepository extends JpaRepository<Account, UUID> {


}
