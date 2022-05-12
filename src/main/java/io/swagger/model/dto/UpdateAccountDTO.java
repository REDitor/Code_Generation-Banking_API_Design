package io.swagger.model.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

/**
 * UpdateAccountDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-12T21:54:51.581Z[GMT]")


public class UpdateAccountDTO   {
  @JsonProperty("Type")
  private String type = null;

  @JsonProperty("MinimumBalance")
  private Integer minimumBalance = null;

  @JsonProperty("Status")
  private String status = null;

  public UpdateAccountDTO type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @Schema(example = "Current", required = true, description = "")
      @NotNull

    public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public UpdateAccountDTO minimumBalance(Integer minimumBalance) {
    this.minimumBalance = minimumBalance;
    return this;
  }

  /**
   * Get minimumBalance
   * @return minimumBalance
   **/
  @Schema(example = "0", required = true, description = "")
      @NotNull

    public Integer getMinimumBalance() {
    return minimumBalance;
  }

  public void setMinimumBalance(Integer minimumBalance) {
    this.minimumBalance = minimumBalance;
  }

  public UpdateAccountDTO status(String status) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateAccountDTO updateAccountDTO = (UpdateAccountDTO) o;
    return Objects.equals(this.type, updateAccountDTO.type) &&
        Objects.equals(this.minimumBalance, updateAccountDTO.minimumBalance) &&
        Objects.equals(this.status, updateAccountDTO.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, minimumBalance, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateAccountDTO {\n");
    
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    minimumBalance: ").append(toIndentedString(minimumBalance)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
