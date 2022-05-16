package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NewAccountDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-16T16:44:39.788Z[GMT]")


public class NewAccountDTO   {
  @JsonProperty("fkCustomerID")
  private Integer fkCustomerID = null;

  @JsonProperty("Type")
  private String type = null;

  @JsonProperty("Status")
  private String status = null;

  @JsonProperty("MinimumBalance")
  private Integer minimumBalance = null;

  public NewAccountDTO fkCustomerID(Integer fkCustomerID) {
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

  public NewAccountDTO type(String type) {
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

  public NewAccountDTO status(String status) {
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

  public NewAccountDTO minimumBalance(Integer minimumBalance) {
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
    NewAccountDTO newAccountDTO = (NewAccountDTO) o;
    return Objects.equals(this.fkCustomerID, newAccountDTO.fkCustomerID) &&
        Objects.equals(this.type, newAccountDTO.type) &&
        Objects.equals(this.status, newAccountDTO.status) &&
        Objects.equals(this.minimumBalance, newAccountDTO.minimumBalance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fkCustomerID, type, status, minimumBalance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewAccountDTO {\n");
    
    sb.append("    fkCustomerID: ").append(toIndentedString(fkCustomerID)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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