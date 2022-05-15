package io.swagger.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.threeten.bp.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Integer customerId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String streetName;
    private Integer houseNumber;
    private String zipCode;
    private String city;
    private String country;
    private Integer transactionAmountLimit;
    private Integer dailyLimit;
}
