package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class AccountsAmountDTO {
    @JsonProperty("totalBalance")
    private Double totalBalance = null;

    public AccountsAmountDTO totalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
        return this;
    }

    /**
     * Get amount
     * @return amount
     **/
    @Schema(example = "11.23", required = true, description = "")
    @NotNull

    @Valid
    public Double gettotalBalance() {
        return totalBalance;
    }

    public void settotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountsAmountDTO accountsAmountDTO = (AccountsAmountDTO) o;
        return Objects.equals(this.totalBalance, accountsAmountDTO.totalBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalBalance);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AccountsAmountDTO {\n");

        sb.append("    totalBalance: ").append(toIndentedString(totalBalance)).append("\n");
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
