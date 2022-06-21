package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * AccountIbanDTO
 */
@Validated
public class AccountIbanDTO   {
    @JsonProperty("IBAN")
    private String IBAN = null;

    @JsonProperty("FirstName")
    private String firstName = null;

    @JsonProperty("LastName")
    private String lastName = null;

    public AccountIbanDTO IBAN(String IBAN) {
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

    /**
     * Get firstName
     * @return firstName
     **/
    @Schema(example = "Albert", description = "")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get lastName
     * @return lastName
     **/
    @Schema(example = "Heijn", description = "")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountIbanDTO accountDTO = (AccountIbanDTO) o;
        return Objects.equals(this.IBAN, accountDTO.IBAN) &&
                Objects.equals(this.firstName, accountDTO.firstName) &&
                Objects.equals(this.lastName, accountDTO.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IBAN, firstName, lastName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AccountDTO {\n");

        sb.append("    IBAN: ").append(toIndentedString(IBAN)).append("\n");
        sb.append("    Name: ").append(toIndentedString(firstName + " " + lastName)).append("\n");
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

