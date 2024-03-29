package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

/**
 * TransactionDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T12:05:25.016Z[GMT]")


public class TransactionDTO   {
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
  private UUID performedByID = null;

  public TransactionDTO transactionId(UUID transactionId) {
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

  public TransactionDTO timestamp(String timestamp) {
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

  public TransactionDTO from(String from) {
    this.from = from;
    return this;
  }

  /**
   * Get from
   * @return from
   **/
  @Schema(example = "NL01INHO0000000001", required = true, description = "")
      @NotNull

  @Size(min=18,max=18)   public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public TransactionDTO to(String to) {
    this.to = to;
    return this;
  }

  /**
   * Get to
   * @return to
   **/
  @Schema(example = "NL01INHO0000000002", required = true, description = "")
      @NotNull

  @Size(min=18,max=18)   public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public TransactionDTO amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * The amount to transfer
   * @return amount
   **/
  @Schema(example = "11.23", required = true, description = "The amount to transfer")
      @NotNull

    public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public TransactionDTO performedByID(UUID performedByID) {
    this.performedByID = performedByID;
    return this;
  }

  /**
   * Get performedByID
   * @return performedByID
   **/
  @Schema(example = "1", description = "")
  
    public UUID getPerformedByID() {
    return performedByID;
  }

  public void setPerformedByID(UUID performedByID) {
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
    TransactionDTO transactionDTO = (TransactionDTO) o;
    return Objects.equals(this.transactionId, transactionDTO.transactionId) &&
        Objects.equals(this.timestamp, transactionDTO.timestamp) &&
        Objects.equals(this.from, transactionDTO.from) &&
        Objects.equals(this.to, transactionDTO.to) &&
        Objects.equals(this.amount, transactionDTO.amount) &&
        Objects.equals(this.performedByID, transactionDTO.performedByID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, timestamp, from, to, amount, performedByID);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionDTO {\n");
    
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
