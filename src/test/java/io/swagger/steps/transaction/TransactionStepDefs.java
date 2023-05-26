package io.swagger.steps.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java8.En;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.model.TransactionDTO;
import io.swagger.steps.BaseStepDefinitions;
import io.swagger.steps.CucumberContextConfig;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = CucumberContextConfig.class)
public class TransactionStepDefs extends BaseStepDefinitions implements En {

    private static final String VALID_TOKEN_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTYW5kZXJIYXJrczEyMyIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfQ1VTVE9NRVIifV0sImlhdCI6MTY4NTA0MDQ0OCwiZXhwIjoxNjg1MDQ0MDQ4fQ.N3wnqsLQsZst-qMeyVLNDc-9004SXBa7IRqmmUlejas";
    private static final String VALID_TOKEN_ADMIN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCcnVub01hcnF1ZXMxMjMiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn1dLCJpYXQiOjE2ODUwNDA0MzAsImV4cCI6MTY4NTA0NDAzMH0.bB6FQFUabBeQBg7jph2G0yh6LTpOWEqBc0aWagrwYZQ";

    private static final String INVALID_TOKEN = "InvalidToken";
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;
    private HttpEntity<String> request;
    private Integer status;

    private TransactionDTO newTransactionDTO;

    private final String validIBAN = "NL01INHO0000000002";

    public TransactionStepDefs() {
        //region Creating a Transaction
        // valid
        Given("^I have a valid token for role \"([^\"]*)\" or role \"([^\"]*)\"$", (String roleUser, String roleAdmin) -> {
            if (roleAdmin.equals("admin")) {
                setHttpHeaders(VALID_TOKEN_ADMIN);
            } else if (roleUser.equals("user")) {
                setHttpHeaders(VALID_TOKEN_USER);
            }
        });
        And("^I have a transaction object with amount \"([^\"]*)\" and from \"([^\"]*)\" and to \"([^\"]*)\"$", (String amount, String from, String to) -> {
            TransactionDTO transaction = new TransactionDTO();
            transaction.setAmount(Double.parseDouble(amount));
            transaction.setFrom(from);
            transaction.setTo(to);

            newTransactionDTO = transaction;
        });
        When("^I call the post transaction endpoint$", () -> {
            request = new HttpEntity<>(mapper.writeValueAsString(newTransactionDTO), httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "transactions/", HttpMethod.POST, request, String.class);
            status = response.getStatusCodeValue();
        });
        Then("^I receive status code (\\d+)$", (Integer statusCode) -> {
            Assertions.assertEquals(statusCode, status);
        });

        // invalid token
        Given("^I have an invalid token for creating a transaction$", () -> {
            setHttpHeaders(INVALID_TOKEN);
        });
        //endregion

        //region Get Transactions
        // Valid token, IBAN, and date
        Given("^I have a valid token for role \"([^\"]*)\"$", (String role) -> {
            if (role.equals("admin")) {
                setHttpHeaders(VALID_TOKEN_ADMIN);
            } else if (role.equals("user")) {
                setHttpHeaders(VALID_TOKEN_USER);
            }
        });
        When("^I call the get all transactions endpoint with iban \"([^\"]*)\" dateTimeFrom \"([^\"]*)\" and dateTimeTo \"([^\"]*)\"$", (String iban, String dateTimeFrom, String dateTimeTo) -> {
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/transactions/" + iban + "?dateTimeFrom=" + dateTimeFrom + "&dateTimeTo=" + dateTimeTo, HttpMethod.GET, request, String.class);
            status = response.getStatusCodeValue();
        });
        Given("^I have an invalid token for role \"([^\"]*)\"$", (String roleAdmin) -> {
            setHttpHeaders(INVALID_TOKEN);
        });


        And("^I have a transaction object with amount \"([^\"]*)\" and to \"([^\"]*)\"$", (String amount, String iban) -> {
            TransactionDTO transaction = new TransactionDTO();
            transaction.setAmount(Double.parseDouble(amount));
            transaction.setTo(iban);

            newTransactionDTO = transaction;
        });
        When("^I call the post deposit endpoint$", () -> {
            request = new HttpEntity<>(mapper.writeValueAsString(newTransactionDTO), httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "transactions/" + newTransactionDTO.getTo()  + "/deposit", HttpMethod.POST, request, String.class);
            status = response.getStatusCodeValue();
        });
        //endregion

    }
}
