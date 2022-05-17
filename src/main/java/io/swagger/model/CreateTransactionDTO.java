package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CreateTransactionDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-17T19:48:55.418Z[GMT]")


public class CreateTransactionDTO   {
  @JsonProperty("From")
  private String from = null;

  @JsonProperty("To")
  private String to = null;

  @JsonProperty("Amount")
  private BigDecimal amount = null;

  public CreateTransactionDTO from(String from) {
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

  public CreateTransactionDTO to(String to) {
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

  public CreateTransactionDTO amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(example = "11.23", required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTransactionDTO createTransactionDTO = (CreateTransactionDTO) o;
    return Objects.equals(this.from, createTransactionDTO.from) &&
        Objects.equals(this.to, createTransactionDTO.to) &&
        Objects.equals(this.amount, createTransactionDTO.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTransactionDTO {\n");
    
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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
