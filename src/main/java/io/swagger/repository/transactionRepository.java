package io.swagger.repository;

import io.swagger.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface transactionRepository extends JpaRepository<Transaction, UUID> {

}
