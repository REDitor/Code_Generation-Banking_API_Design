package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AccountDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-12T21:54:51.581Z[GMT]")


public class AccountDTO   {
  @JsonProperty("fkCustomerID")
  private Integer fkCustomerID = null;

  @JsonProperty("IBAN")
  private String IBAN = null;

  @JsonProperty("Type")
  private String type = null;

  @JsonProperty("Balance")
  private Integer balance = null;

  @JsonProperty("Status")
  private String status = null;

  @JsonProperty("MinimumBalance")
  private Integer minimumBalance = null;

  public AccountDTO fkCustomerID(Integer fkCustomerID) {
    this.fkCustomerID = fkCustomerID;
    return this;
  }

  /**
   * Get fkCustomerID
   * @return fkCustomerID
   **/
  @Schema(example = "1", description = "")
  
    public Integer getFkCustomerID() {
    return fkCustomerID;
  }

  public void setFkCustomerID(Integer fkCustomerID) {
    this.fkCustomerID = fkCustomerID;
  }

  public AccountDTO IBAN(String IBAN) {
    this.IBAN = IBAN;
    return this;
  }

  /**
   * Get IBAN
   * @return IBAN
   **/
  @Schema(example = "NL01INHO0000000002", description = "")
  
  @Size(min=18,max=18)   public String getIBAN() {
    return IBAN;
  }

  public void setIBAN(String IBAN) {
    this.IBAN = IBAN;
  }

  public AccountDTO type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @Schema(example = "Current", description = "")
  
    public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public AccountDTO balance(Integer balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
   **/
  @Schema(example = "0", description = "")
  
    public Integer getBalance() {
    return balance;
  }

  public void setBalance(Integer balance) {
    this.balance = balance;
  }

  public AccountDTO status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   **/
  @Schema(example = "Open", description = "")
  
    public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public AccountDTO minimumBalance(Integer minimumBalance) {
    this.minimumBalance = minimumBalance;
    return this;
  }

  /**
   * Get minimumBalance
   * @return minimumBalance
   **/
  @Schema(example = "0", description = "")
  
    public Integer getMinimumBalance() {
    return minimumBalance;
  }

  public void setMinimumBalance(Integer minimumBalance) {
    this.minimumBalance = minimumBalance;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountDTO accountDTO = (AccountDTO) o;
    return Objects.equals(this.fkCustomerID, accountDTO.fkCustomerID) &&
        Objects.equals(this.IBAN, accountDTO.IBAN) &&
        Objects.equals(this.type, accountDTO.type) &&
        Objects.equals(this.balance, accountDTO.balance) &&
        Objects.equals(this.status, accountDTO.status) &&
        Objects.equals(this.minimumBalance, accountDTO.minimumBalance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fkCustomerID, IBAN, type, balance, status, minimumBalance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccountDTO {\n");
    
    sb.append("    fkCustomerID: ").append(toIndentedString(fkCustomerID)).append("\n");
    sb.append("    IBAN: ").append(toIndentedString(IBAN)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    minimumBalance: ").append(toIndentedString(minimumBalance)).append("\n");
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
