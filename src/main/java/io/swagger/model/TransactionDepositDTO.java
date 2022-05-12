package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TransactionDepositDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-12T21:54:51.581Z[GMT]")


public class TransactionDepositDTO   {
  @JsonProperty("transactionId")
  private UUID transactionId = null;

  @JsonProperty("timestamp")
  private String timestamp = null;

  @JsonProperty("From")
  private String from = null;

  @JsonProperty("To")
  private String to = null;

  @JsonProperty("Amount")
  private Double amount = null;

  @JsonProperty("PerformedByID")
  private Integer performedByID = null;

  public TransactionDepositDTO transactionId(UUID transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  /**
   * Get transactionId
   * @return transactionId
   **/
  @Schema(description = "")
  
    @Valid
    public UUID getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(UUID transactionId) {
    this.transactionId = transactionId;
  }

  public TransactionDepositDTO timestamp(String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Date of birth
   * @return timestamp
   **/
  @Schema(example = "2021-03-20T09:12:28Z", description = "Date of birth")
  
    public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public TransactionDepositDTO from(String from) {
    this.from = from;
    return this;
  }

  /**
   * Get from
   * @return from
   **/
  @Schema(description = "")
  
  @Size(min=18,max=18)   public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public TransactionDepositDTO to(String to) {
    this.to = to;
    return this;
  }

  /**
   * Get to
   * @return to
   **/
  @Schema(example = "NL01INHO0000000002", description = "")
  
  @Size(min=18,max=18)   public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public TransactionDepositDTO amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * The amount to transfer
   * @return amount
   **/
  @Schema(example = "11.23", description = "The amount to transfer")
  
    public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public TransactionDepositDTO performedByID(Integer performedByID) {
    this.performedByID = performedByID;
    return this;
  }

  /**
   * Get performedByID
   * @return performedByID
   **/
  @Schema(example = "1", description = "")
  
    public Integer getPerformedByID() {
    return performedByID;
  }

  public void setPerformedByID(Integer performedByID) {
    this.performedByID = performedByID;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionDepositDTO transactionDepositDTO = (TransactionDepositDTO) o;
    return Objects.equals(this.transactionId, transactionDepositDTO.transactionId) &&
        Objects.equals(this.timestamp, transactionDepositDTO.timestamp) &&
        Objects.equals(this.from, transactionDepositDTO.from) &&
        Objects.equals(this.to, transactionDepositDTO.to) &&
        Objects.equals(this.amount, transactionDepositDTO.amount) &&
        Objects.equals(this.performedByID, transactionDepositDTO.performedByID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, timestamp, from, to, amount, performedByID);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionDepositDTO {\n");
    
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    performedByID: ").append(toIndentedString(performedByID)).append("\n");
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
