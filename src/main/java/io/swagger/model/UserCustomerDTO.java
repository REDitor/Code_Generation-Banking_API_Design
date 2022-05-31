package io.swagger.model;

import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import org.threeten.bp.LocalDate;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * UserCustomerDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T12:05:25.016Z[GMT]")


public class UserCustomerDTO   {
  @JsonProperty("UserId")
  private UUID userId = null;

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
  private List<Role> roles = null;

  @JsonProperty("ZipCode")
  private String zipCode = null;

  @JsonProperty("City")
  private String city = null;

  @JsonProperty("Country")
  private String country = null;

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

  public UserCustomerDTO userId(UUID userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
   **/
  @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "")
  
    @Valid
    public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public UserCustomerDTO firstName(String firstName) {
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

  public UserCustomerDTO lastName(String lastName) {
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

  public UserCustomerDTO birthDate(LocalDate birthDate) {
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

  public UserCustomerDTO streetName(String streetName) {
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

  public UserCustomerDTO houseNumber(Integer houseNumber) {
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

  public UserCustomerDTO role(List<Role> role) {
    this.roles = role;
    return this;
  }

  /**
   * Get role
   * @return role
   **/
  @Schema(example = "Customer", description = "")
  
    public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public UserCustomerDTO zipCode(String zipCode) {
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

  public UserCustomerDTO city(String city) {
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

  public UserCustomerDTO country(String country) {
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

  public UserCustomerDTO email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(example = "brunocm@pm.me", description = "")
  
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserCustomerDTO username(String username) {
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

  public UserCustomerDTO password(String password) {
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

  public UserCustomerDTO transactionAmountLimit(Integer transactionAmountLimit) {
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

  public UserCustomerDTO dailyLimit(Integer dailyLimit) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserCustomerDTO userCustomerDTO = (UserCustomerDTO) o;
    return Objects.equals(this.userId, userCustomerDTO.userId) &&
        Objects.equals(this.firstName, userCustomerDTO.firstName) &&
        Objects.equals(this.lastName, userCustomerDTO.lastName) &&
        Objects.equals(this.birthDate, userCustomerDTO.birthDate) &&
        Objects.equals(this.streetName, userCustomerDTO.streetName) &&
        Objects.equals(this.houseNumber, userCustomerDTO.houseNumber) &&
        Objects.equals(this.roles, userCustomerDTO.roles) &&
        Objects.equals(this.zipCode, userCustomerDTO.zipCode) &&
        Objects.equals(this.city, userCustomerDTO.city) &&
        Objects.equals(this.country, userCustomerDTO.country) &&
        Objects.equals(this.email, userCustomerDTO.email) &&
        Objects.equals(this.username, userCustomerDTO.username) &&
        Objects.equals(this.password, userCustomerDTO.password) &&
        Objects.equals(this.transactionAmountLimit, userCustomerDTO.transactionAmountLimit) &&
        Objects.equals(this.dailyLimit, userCustomerDTO.dailyLimit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, firstName, lastName, birthDate, streetName, houseNumber, roles, zipCode, city, country, email, username, password, transactionAmountLimit, dailyLimit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserCustomerDTO {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    birthDate: ").append(toIndentedString(birthDate)).append("\n");
    sb.append("    streetName: ").append(toIndentedString(streetName)).append("\n");
    sb.append("    houseNumber: ").append(toIndentedString(houseNumber)).append("\n");
    sb.append("    role: ").append(toIndentedString(roles)).append("\n");
    sb.append("    zipCode: ").append(toIndentedString(zipCode)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
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
