package io.swagger.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import org.threeten.bp.LocalDate;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserEmployeeDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-17T19:48:55.418Z[GMT]")


public class UserEmployeeDTO   {
  @JsonProperty("CustomerId")
  private UUID customerId = null;

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

  @JsonProperty("Role")
  private List<Role> role = null;

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

  public UserEmployeeDTO customerId(UUID customerId) {
    this.customerId = customerId;
    return this;
  }

  /**
   * Get customerId
   * @return customerId
   **/
  @Schema(example = "1", description = "")
  
    public UUID getCustomerId() {
    return customerId;
  }

  public void setCustomerId(UUID customerId) {
    this.customerId = customerId;
  }

  public UserEmployeeDTO firstName(String firstName) {
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

  public UserEmployeeDTO lastName(String lastName) {
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

  public UserEmployeeDTO birthDate(LocalDate birthDate) {
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

  public UserEmployeeDTO streetName(String streetName) {
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

  public UserEmployeeDTO houseNumber(Integer houseNumber) {
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

  public UserEmployeeDTO role(List<Role> role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   * @return role
   **/
  @Schema(example = "Employee", description = "")
  
    public List<Role> getRole() {
    return role;
  }

  public void setRole(List<Role> role) {
    this.role = role;
  }

  public UserEmployeeDTO zipCode(String zipCode) {
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

  public UserEmployeeDTO city(String city) {
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

  public UserEmployeeDTO country(String country) {
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

  public UserEmployeeDTO transactionAmountLimit(Integer transactionAmountLimit) {
    this.transactionAmountLimit = transactionAmountLimit;
    return this;
  }

  /**
   * Get transactionAmountLimit
   * @return transactionAmountLimit
   **/
  @Schema(example = "0", description = "")
  
    public Integer getTransactionAmountLimit() {
    return transactionAmountLimit;
  }

  public void setTransactionAmountLimit(Integer transactionAmountLimit) {
    this.transactionAmountLimit = transactionAmountLimit;
  }

  public UserEmployeeDTO dailyLimit(Integer dailyLimit) {
    this.dailyLimit = dailyLimit;
    return this;
  }

  /**
   * Get dailyLimit
   * @return dailyLimit
   **/
  @Schema(example = "0", description = "")
  
    public Integer getDailyLimit() {
    return dailyLimit;
  }

  public void setDailyLimit(Integer dailyLimit) {
    this.dailyLimit = dailyLimit;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserEmployeeDTO userEmployeeDTO = (UserEmployeeDTO) o;
    return Objects.equals(this.customerId, userEmployeeDTO.customerId) &&
        Objects.equals(this.firstName, userEmployeeDTO.firstName) &&
        Objects.equals(this.lastName, userEmployeeDTO.lastName) &&
        Objects.equals(this.birthDate, userEmployeeDTO.birthDate) &&
        Objects.equals(this.streetName, userEmployeeDTO.streetName) &&
        Objects.equals(this.houseNumber, userEmployeeDTO.houseNumber) &&
        Objects.equals(this.role, userEmployeeDTO.role) &&
        Objects.equals(this.zipCode, userEmployeeDTO.zipCode) &&
        Objects.equals(this.city, userEmployeeDTO.city) &&
        Objects.equals(this.country, userEmployeeDTO.country) &&
        Objects.equals(this.transactionAmountLimit, userEmployeeDTO.transactionAmountLimit) &&
        Objects.equals(this.dailyLimit, userEmployeeDTO.dailyLimit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerId, firstName, lastName, birthDate, streetName, houseNumber, role, zipCode, city, country, transactionAmountLimit, dailyLimit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserEmployeeDTO {\n");
    
    sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    birthDate: ").append(toIndentedString(birthDate)).append("\n");
    sb.append("    streetName: ").append(toIndentedString(streetName)).append("\n");
    sb.append("    houseNumber: ").append(toIndentedString(houseNumber)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    zipCode: ").append(toIndentedString(zipCode)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    transactionAmountLimit: ").append(toIndentedString(transactionAmountLimit)).append("\n");
    sb.append("    dailyLimit: ").append(toIndentedString(dailyLimit)).append("\n");
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
