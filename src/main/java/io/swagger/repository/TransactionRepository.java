package io.swagger.repository;

import io.swagger.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT t FROM Transaction t WHERE t.from.IBAN = ?1 OR t.to.IBAN = ?1")
    List<Transaction> findAllByIBAN(String IBAN);

    @Query("SELECT t FROM Transaction t WHERE (t.from.IBAN = ?1 OR t.to.IBAN = ?1) AND t.timestamp >= ?2 AND t.timestamp <= ?3")
    List<Transaction> findAllByIBANBetweenTimestamps(String IBAN, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);

    @Query("SELECT t FROM Transaction t WHERE t.performedByID.userId = ?1")
    List<Transaction> findAllByUserId(UUID userId);

    @Query("SELECT t FROM Transaction t WHERE (t.performedByID.userId = ?1) AND t.timestamp >= ?2 AND t.timestamp <= ?3")
    List<Transaction> findAllByUserIdBetweenTimeStamps(UUID userId, LocalDateTime from, LocalDateTime to);

}
