package io.swagger.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.threeten.bp.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private UUID userId;
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
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
    private String username;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "UserID")
    @JsonIgnore
    private List<Account> account = new ArrayList<>();

    public User(String firstName, String lastName, LocalDate birthDate, String streetName, Integer houseNumber, String zipCode, String city, String country, Integer transactionAmountLimit, Integer dailyLimit, List<Role> roles, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.transactionAmountLimit = transactionAmountLimit;
        this.dailyLimit = dailyLimit;
        this.roles = roles;
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(ArrayList<Account> account) {
        this.account = account;
    }

    public UUID getuserId() {
        return userId;
    }

    public void setuserId(UUID userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getTransactionAmountLimit() {
        return transactionAmountLimit;
    }

    public void setTransactionAmountLimit(Integer transactionAmountLimit) {
        this.transactionAmountLimit = transactionAmountLimit;
    }

    public Integer getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(Integer dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> role) {
        this.roles = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "\nID: " + this.userId +
                "\nFirstname: " + this.firstName +
                "\nLastname: " + this.lastName +
                "\nBirthdate: " + this.birthDate +
                "\nStreet: " + this.streetName +
                "\nHouse number: " + this.houseNumber +
                "\nZip code: " + this.zipCode +
                "\nCity: " + this.city +
                "\nCountry: " + this.country +
                "\nTransaction limit: " + this.transactionAmountLimit +
                "\nDaily limit: " + this.dailyLimit +
                "\nRoles: " + this.roles +
                "\nUsername: " + this.username +
                "\nPassword: " + this.password;
    }
}
