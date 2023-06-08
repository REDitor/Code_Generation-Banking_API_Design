package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * UserWithAccountsDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-17T19:48:55.418Z[GMT]")


public class UserWithAccountsDTO   {
  @JsonProperty("userId")
  private Integer userId = null;

  @JsonProperty("FirstName")
  private String firstName = null;

  @JsonProperty("LastName")
  private String lastName = null;

  @JsonProperty("BirthDate")
  private LocalDate birthDate = null;

  @JsonProperty("StreetName")
  private String streetName = null;

  @JsonProperty("HouseNumber")
  private Integer houseNumber = null;

  @JsonProperty("ZipCode")
  private String zipCode = null;

  @JsonProperty("City")
  private String city = null;

  @JsonProperty("Country")
  private String country = null;

  @JsonProperty("TransactionAmountLimit")
  private Integer transactionAmountLimit = null;

  @JsonProperty("DailyLimit")
  private Integer dailyLimit = null;

  @JsonProperty("Accounts")
  @Valid
  private List<AccountDTO> accounts = null;

  public UserWithAccountsDTO userId(Integer userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
   **/
  @Schema(example = "1", description = "")
  
    public Integer getuserId() {
    return userId;
  }

  public void setuserId(Integer userId) {
    this.userId = userId;
  }

  public UserWithAccountsDTO firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   **/
  @Schema(example = "Bruno", description = "")
  
    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public UserWithAccountsDTO lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   **/
  @Schema(example = "Coimbra Marques", description = "")
  
    public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public UserWithAccountsDTO birthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  /**
   * Date of birth
   * @return birthDate
   **/
  @Schema(example = "Tue Oct 12 00:00:00 GMT 1999", description = "Date of birth")
  
    @Valid
    public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public UserWithAccountsDTO streetName(String streetName) {
    this.streetName = streetName;
    return this;
  }

  /**
   * Get streetName
   * @return streetName
   **/
  @Schema(example = "Pietersbergweg", description = "")
  
    public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public UserWithAccountsDTO houseNumber(Integer houseNumber) {
    this.houseNumber = houseNumber;
    return this;
  }

  /**
   * Get houseNumber
   * @return houseNumber
   **/
  @Schema(example = "1234", description = "")
  
    public Integer getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(Integer houseNumber) {
    this.houseNumber = houseNumber;
  }

  public UserWithAccountsDTO zipCode(String zipCode) {
    this.zipCode = zipCode;
    return this;
  }

  /**
   * Get zipCode
   * @return zipCode
   **/
  @Schema(example = "0987 MB", description = "")
  
    public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public UserWithAccountsDTO city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Get city
   * @return city
   **/
  @Schema(example = "Amsterdam", description = "")
  
    public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public UserWithAccountsDTO country(String country) {
    this.country = country;
    return this;
  }

  /**
   * Get country
   * @return country
   **/
  @Schema(example = "Netherlands", description = "")
  
    public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public UserWithAccountsDTO transactionAmountLimit(Integer transactionAmountLimit) {
    this.transactionAmountLimit = transactionAmountLimit;
    return this;
  }

  /**
   * Get transactionAmountLimit
   * @return transactionAmountLimit
   **/
  @Schema(example = "2000", description = "")
  
    public Integer getTransactionAmountLimit() {
    return transactionAmountLimit;
  }

  public void setTransactionAmountLimit(Integer transactionAmountLimit) {
    this.transactionAmountLimit = transactionAmountLimit;
  }

  public UserWithAccountsDTO dailyLimit(Integer dailyLimit) {
    this.dailyLimit = dailyLimit;
    return this;
  }

  /**
   * Get dailyLimit
   * @return dailyLimit
   **/
  @Schema(example = "500", description = "")
  
    public Integer getDailyLimit() {
    return dailyLimit;
  }

  public void setDailyLimit(Integer dailyLimit) {
    this.dailyLimit = dailyLimit;
  }

  public UserWithAccountsDTO accounts(List<AccountDTO> accounts) {
    this.accounts = accounts;
    return this;
  }

  public UserWithAccountsDTO addAccountsItem(AccountDTO accountsItem) {
    if (this.accounts == null) {
      this.accounts = new ArrayList<AccountDTO>();
    }
    this.accounts.add(accountsItem);
    return this;
  }

  /**
   * Get accounts
   * @return accounts
   **/
  @Schema(description = "")
      @Valid
    public List<AccountDTO> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<AccountDTO> accounts) {
    this.accounts = accounts;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserWithAccountsDTO userWithAccountsDTO = (UserWithAccountsDTO) o;
    return Objects.equals(this.userId, userWithAccountsDTO.userId) &&
        Objects.equals(this.firstName, userWithAccountsDTO.firstName) &&
        Objects.equals(this.lastName, userWithAccountsDTO.lastName) &&
        Objects.equals(this.birthDate, userWithAccountsDTO.birthDate) &&
        Objects.equals(this.streetName, userWithAccountsDTO.streetName) &&
        Objects.equals(this.houseNumber, userWithAccountsDTO.houseNumber) &&
        Objects.equals(this.zipCode, userWithAccountsDTO.zipCode) &&
        Objects.equals(this.city, userWithAccountsDTO.city) &&
        Objects.equals(this.country, userWithAccountsDTO.country) &&
        Objects.equals(this.transactionAmountLimit, userWithAccountsDTO.transactionAmountLimit) &&
        Objects.equals(this.dailyLimit, userWithAccountsDTO.dailyLimit) &&
        Objects.equals(this.accounts, userWithAccountsDTO.accounts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, firstName, lastName, birthDate, streetName, houseNumber, zipCode, city, country, transactionAmountLimit, dailyLimit, accounts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserWithAccountsDTO {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    birthDate: ").append(toIndentedString(birthDate)).append("\n");
    sb.append("    streetName: ").append(toIndentedString(streetName)).append("\n");
    sb.append("    houseNumber: ").append(toIndentedString(houseNumber)).append("\n");
    sb.append("    zipCode: ").append(toIndentedString(zipCode)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    transactionAmountLimit: ").append(toIndentedString(transactionAmountLimit)).append("\n");
    sb.append("    dailyLimit: ").append(toIndentedString(dailyLimit)).append("\n");
    sb.append("    accounts: ").append(toIndentedString(accounts)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
