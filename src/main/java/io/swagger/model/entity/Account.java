package io.swagger.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Account {

    @Id
    @Column(name = "IBAN")
    private String IBAN;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User UserID;
    private String type;
    private Integer balance;
    private String status;
    private Integer minimumBalance;

    public Account(String IBAN, User UserID, String type, Integer balance, String status, Integer minimumBalance) {
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


