package io.swagger.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private String IBAN;

    @OneToOne(mappedBy = "user")
    private User fkUserID;
    private String type;
    private Integer balance;
    private String status;
    private Integer minimumBalance;

    public Account(String IBAN, User fkUserID, String type, Integer balance, String status, Integer minimumBalance) {
        this.IBAN = IBAN;
        this.fkUserID = fkUserID;
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

    public User getFkUserID() {
        return fkUserID;
    }

    public void setFkUserID(User fkUserID) {
        this.fkUserID = fkUserID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
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
}

