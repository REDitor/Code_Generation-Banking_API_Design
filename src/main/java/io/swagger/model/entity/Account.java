package io.swagger.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "IBAN")
    private String IBAN;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User UserID;
    private AccountType type;
    private double balance;
    private String status;
    private Integer minimumBalance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "UserID")
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String IBAN, User UserID, AccountType type, double balance, String status, Integer minimumBalance) {
        this.IBAN = IBAN;
        this.UserID = UserID;
        this.type = type;
        this.balance = balance;
        this.status = status;
        this.minimumBalance = minimumBalance;
    }

    public Account() {

    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public User getUserID() {
        return UserID;
    }

    public void setUserID(User UserID) {
        this.UserID = UserID;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Integer minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    @Override
    public String toString() {
        return "\nIBAN: " + this.IBAN +
                "\nUser: " + this.UserID +
                "\nType: " + this.type +
                "\nBalance: " + this.balance +
                "\nStatus: " + this.status +
                "\nMinimum Balance: " + this.minimumBalance;
    }
}


