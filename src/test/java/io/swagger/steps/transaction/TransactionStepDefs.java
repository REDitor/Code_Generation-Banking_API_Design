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

    private static final String VALID_TOKEN_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTYW5kZXJIYXJrczEyMyIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfQ1VTVE9NRVIifV0sImlhdCI6MTY4Mjc4MzMyMSwiZXhwIjoxNjgyNzg2OTIxfQ.dlYr2lrSsaoWhkcju6aL4B6gjYLCjuNbSgTzlYTI3t8";
    private static final String VALID_TOKEN_ADMIN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCcnVub01hcnF1ZXMxMjMiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn1dLCJpYXQiOjE2ODQ4MzI0MzIsImV4cCI6MTY4NDgzNjAzMn0.Gt0GxC6q8HCCABXcjikp8neD_QMA_OYuBdH6BHYeyvU";

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;
    private HttpEntity<String> request;
    private Integer status;

    private TransactionDTO newTransactionDTO;

    public TransactionStepDefs() {
        // Transaction Creation
        Given("^I have a valid token for role \"([^\"]*)\" or role \"([^\"]*)\"$", (String roleUser, String roleAdmin) -> {
            if (roleAdmin.equals("admin")) {
                setHttpHeaders(VALID_TOKEN_ADMIN);
            } else if(roleUser.equals("user")) {
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
        Then("^I receive status code (\\d+) for creating a transaction$", (Integer statusCode) -> {
            Assertions.assertEquals(statusCode, status);
        });

        // Get all transactions

    }
}
