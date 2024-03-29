package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.entity.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * AccountDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T12:05:25.016Z[GMT]")


public class AccountDTO   {
  @JsonProperty("IBAN")
  private String IBAN = null;

  @JsonProperty("Type")
  private AccountType type = null;

  @JsonProperty("Balance")
  private Integer balance = null;

  @JsonProperty("Status")
  private String status = null;

  @JsonProperty("MinimumBalance")
  private Integer minimumBalance = null;

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

  public AccountDTO type(AccountType type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @Schema(example = "ACCOUNT_TYPE_CURRENT", description = "")
  
    public AccountType getType() {
    return type;
  }

  public void setType(AccountType type) {
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
    return Objects.equals(this.IBAN, accountDTO.IBAN) &&
        Objects.equals(this.type, accountDTO.type) &&
        Objects.equals(this.balance, accountDTO.balance) &&
        Objects.equals(this.status, accountDTO.status) &&
        Objects.equals(this.minimumBalance, accountDTO.minimumBalance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(IBAN, type, balance, status, minimumBalance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccountDTO {\n");

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
