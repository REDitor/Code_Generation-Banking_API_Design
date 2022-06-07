package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.LocalDate;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UpdateUserCustomerDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-31T12:15:45.146Z[GMT]")


public class UpdateUserDTO {
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

  @JsonProperty("Roles")
  @Valid
  private List<String> roles = new ArrayList<String>();

  @JsonProperty("Email")
  private String email = null;

  @JsonProperty("Username")
  private String username = null;

  @JsonProperty("Password")
  private String password = null;

  @JsonProperty("TransactionAmountLimit")
  private Integer transactionAmountLimit = null;

  @JsonProperty("DailyLimit")
  private Integer dailyLimit = null;

  public UpdateUserDTO firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   **/
  @Schema(example = "Bruno", required = true, description = "")
      @NotNull

    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public UpdateUserDTO lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   **/
  @Schema(example = "Coimbra Marques", required = true, description = "")
      @NotNull

    public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public UpdateUserDTO birthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  /**
   * Date of birth
   * @return birthDate
   **/
  @Schema(example = "Tue Oct 12 00:00:00 GMT 1999", required = true, description = "Date of birth")
      @NotNull

    @Valid
    public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public UpdateUserDTO streetName(String streetName) {
    this.streetName = streetName;
    return this;
  }

  /**
   * Get streetName
   * @return streetName
   **/
  @Schema(example = "Pietersbergweg", required = true, description = "")
      @NotNull

    public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public UpdateUserDTO houseNumber(Integer houseNumber) {
    this.houseNumber = houseNumber;
    return this;
  }

  /**
   * Get houseNumber
   * @return houseNumber
   **/
  @Schema(example = "1234", required = true, description = "")
      @NotNull

    public Integer getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(Integer houseNumber) {
    this.houseNumber = houseNumber;
  }

  public UpdateUserDTO zipCode(String zipCode) {
    this.zipCode = zipCode;
    return this;
  }

  /**
   * Get zipCode
   * @return zipCode
   **/
  @Schema(example = "0987 MB", required = true, description = "")
      @NotNull

    public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public UpdateUserDTO city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Get city
   * @return city
   **/
  @Schema(example = "Amsterdam", required = true, description = "")
      @NotNull

    public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public UpdateUserDTO country(String country) {
    this.country = country;
    return this;
  }

  /**
   * Get country
   * @return country
   **/
  @Schema(example = "Netherlands", required = true, description = "")
      @NotNull

    public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public UpdateUserDTO roles(List<String> roles) {
    this.roles = roles;
    return this;
  }

  public UpdateUserDTO addRolesItem(String rolesItem) {
    this.roles.add(rolesItem);
    return this;
  }

  /**
   * Get roles
   * @return roles
   **/
  @Schema(example = "[\"Customer\"]", required = true, description = "")
      @NotNull

    public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public UpdateUserDTO email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(example = "brunocm@gmail.com", description = "")
  
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UpdateUserDTO username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
   **/
  @Schema(example = "brumarq", description = "")
  
    public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UpdateUserDTO password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
   **/
  @Schema(example = "test..123", description = "")
  
    public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UpdateUserDTO transactionAmountLimit(Integer transactionAmountLimit) {
    this.transactionAmountLimit = transactionAmountLimit;
    return this;
  }

  /**
   * Get transactionAmountLimit
   * @return transactionAmountLimit
   **/
  @Schema(example = "0", required = true, description = "")
      @NotNull

    public Integer getTransactionAmountLimit() {
    return transactionAmountLimit;
  }

  public void setTransactionAmountLimit(Integer transactionAmountLimit) {
    this.transactionAmountLimit = transactionAmountLimit;
  }

  public UpdateUserDTO dailyLimit(Integer dailyLimit) {
    this.dailyLimit = dailyLimit;
    return this;
  }

  /**
   * Get dailyLimit
   * @return dailyLimit
   **/
  @Schema(example = "0", required = true, description = "")
      @NotNull

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
    UpdateUserDTO updateUserDTO = (UpdateUserDTO) o;
    return Objects.equals(this.firstName, updateUserDTO.firstName) &&
        Objects.equals(this.lastName, updateUserDTO.lastName) &&
        Objects.equals(this.birthDate, updateUserDTO.birthDate) &&
        Objects.equals(this.streetName, updateUserDTO.streetName) &&
        Objects.equals(this.houseNumber, updateUserDTO.houseNumber) &&
        Objects.equals(this.zipCode, updateUserDTO.zipCode) &&
        Objects.equals(this.city, updateUserDTO.city) &&
        Objects.equals(this.country, updateUserDTO.country) &&
        Objects.equals(this.roles, updateUserDTO.roles) &&
        Objects.equals(this.email, updateUserDTO.email) &&
        Objects.equals(this.username, updateUserDTO.username) &&
        Objects.equals(this.password, updateUserDTO.password) &&
        Objects.equals(this.transactionAmountLimit, updateUserDTO.transactionAmountLimit) &&
        Objects.equals(this.dailyLimit, updateUserDTO.dailyLimit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, birthDate, streetName, houseNumber, zipCode, city, country, roles, email, username, password, transactionAmountLimit, dailyLimit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateUserCustomerDTO {\n");
    
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    birthDate: ").append(toIndentedString(birthDate)).append("\n");
    sb.append("    streetName: ").append(toIndentedString(streetName)).append("\n");
    sb.append("    houseNumber: ").append(toIndentedString(houseNumber)).append("\n");
    sb.append("    zipCode: ").append(toIndentedString(zipCode)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    roles: ").append(toIndentedString(roles)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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
