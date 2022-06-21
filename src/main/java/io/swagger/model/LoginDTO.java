package io.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * LoginDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T14:26:03.164Z[GMT]")


public class LoginDTO {

  @JsonProperty("user")
  @JsonIgnoreProperties({"account", "password"})
  private User user = null;
  @JsonProperty("jwtToken")
  private String jwtToken = null;

  public LoginDTO user(User user) {
    this.user = user;
    return this;
  }

  /**
   * Get User
   * @return User
   **/
  @Schema(example = "", description = "")

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public LoginDTO jwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
    return this;
  }

  /**
   * Get jwtToken
   * @return jwtToken
   **/
  @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", description = "")
  
    public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoginDTO loginDTO = (LoginDTO) o;
    return Objects.equals(this.jwtToken, loginDTO.jwtToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jwtToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoginDTO {\n");

    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    jwtToken: ").append(toIndentedString(jwtToken)).append("\n");
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
