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
    private Employee performedByID;
}
