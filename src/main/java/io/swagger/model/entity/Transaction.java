package io.swagger.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private UUID transactionId;
    private LocalDateTime timestamp;
    @ManyToOne
    private Account from;
    @ManyToOne
    private Account to;
    private Double amount;
    @ManyToOne
    private User performedByID;

    public Transaction(LocalDateTime timestamp, Account from, Account to, Double amount, User performedByID) {
        this.timestamp = timestamp;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.performedByID = performedByID;
    }

    public Transaction() {

    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getPerformedByID() {
        return performedByID;
    }

    public void setPerformedByID(User performedByID) {
        this.performedByID = performedByID;
    }

    @Override
    public String toString() {
        return "\nID: " + transactionId +
                "\nTimestamp: " + timestamp +
                "\nFrom: " + from +
                "\nTo: " + to +
                "\nAmount: " + amount +
                "\nPerformedBy: " + performedByID;
    }
}
