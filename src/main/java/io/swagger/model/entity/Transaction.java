package io.swagger.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private UUID transactionId;
    private String timestamp;
    @ManyToOne
    private Account from;
    @ManyToOne
    private Account to;
    private Double amount;
    @ManyToOne
    private User performedByID;

    public Transaction(UUID transactionId, String timestamp, Account from, Account to, Double amount, User performedByID) {
        this.transactionId = transactionId;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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
        return "Id: " + transactionId +
                "\n, Amount: " + amount +
                "\n, Timestamp: " + timestamp +
                "\n, From: " + from +
                "\n, To: " + to +
                "\n, PerformedByID: " + performedByID;
    }
}
