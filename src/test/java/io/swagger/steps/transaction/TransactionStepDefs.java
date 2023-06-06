package io.swagger.steps.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java8.En;
import io.swagger.TokenHolder;
import io.swagger.model.TransactionDTO;
import io.swagger.repository.AccountRepository;
import io.swagger.steps.BaseStepDefinitions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;

public class TransactionStepDefs extends BaseStepDefinitions implements En {

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;
    private HttpEntity<String> request;
    private Integer status;
    private JSONArray arrayResult;
    private JSONObject objectResult;
    private TransactionDTO newTransactionDTO;

    public TransactionStepDefs() {
        Given("^I have a valid token for role \"([^\"]*)\" or role \"([^\"]*)\"$", (String roleUser, String roleAdmin) -> {
            if (roleAdmin.equals("admin")) {
                setHttpHeaders(TokenHolder.VALID_TOKEN_ADMIN);
            } else if (roleUser.equals("user")) {
                setHttpHeaders(TokenHolder.VALID_TOKEN_USER);
            }
        });
        Given("^I have a valid token for role \"([^\"]*)\"$", (String role) -> {
            if (role.equals("admin")) {
                setHttpHeaders(TokenHolder.VALID_TOKEN_ADMIN);
            } else if (role.equals("user")) {
                setHttpHeaders(TokenHolder.VALID_TOKEN_USER);
            } else if (role.equals("thief")) {
                setHttpHeaders(TokenHolder.VALID_TOKEN_THIEF);
            }
        });
        Given("^I have an invalid token$", () -> {
            setHttpHeaders(TokenHolder.INVALID_TOKEN);
        });
        And("^I receive a response body with a transaction object$", () -> {
            Assert.assertTrue(objectResult.length() > 0);
        });
        And("^I receive a response body with at least (\\d+) transaction objects$", (Integer numberOfResults) -> {
            Assert.assertTrue(arrayResult.length() >= numberOfResults);
        });
        Then("^I receive status code (\\d+)$", (Integer statusCode) -> {
            Assertions.assertEquals(statusCode, status);
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

            if (status.equals(201)) {
                objectResult = new JSONObject(response.getBody());
            }
        });
        When("^I call the get all transactions endpoint with iban \"([^\"]*)\" and dateTimeFrom \"([^\"]*)\" and dateTimeTo \"([^\"]*)\"$", (String iban, String dateTimeFrom, String dateTimeTo) -> {
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/transactions/" + iban + "?dateTimeFrom=" + dateTimeFrom + "&dateTimeTo=" + dateTimeTo, HttpMethod.GET, request, String.class);
            status = response.getStatusCodeValue();

            if (status.equals(200)) {
                arrayResult = new JSONArray(response.getBody());
            }
        });

        And("^I have a transaction object with amount \"([^\"]*)\" and to \"([^\"]*)\"$", (String amount, String iban) -> {
            TransactionDTO transaction = new TransactionDTO();
            transaction.setAmount(Double.parseDouble(amount));
            transaction.setTo(iban);

            newTransactionDTO = transaction;
        });
        When("^I call the post deposit endpoint$", () -> {
            request = new HttpEntity<>(mapper.writeValueAsString(newTransactionDTO), httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "transactions/" + newTransactionDTO.getTo() + "/deposit", HttpMethod.POST, request, String.class);
            status = response.getStatusCodeValue();

            if (status.equals(201)) {
                objectResult = new JSONObject(response.getBody());
            }
        });
        When("^I call the get all transactions endpoint with userId \"([^\"]*)\" and dateTimeFrom \"([^\"]*)\" and dateTimeTo \"([^\"]*)\"$", (String userId, String dateTimeFrom, String dateTimeTo) -> {
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/transactions/users/" + userId + "?dateTimeFrom=" + dateTimeFrom + "&dateTimeTo=" + dateTimeTo, HttpMethod.GET, request, String.class);
            status = response.getStatusCodeValue();

            if (status.equals(200)) {
                arrayResult = new JSONArray(response.getBody());
            }
        });
        And("^I have a transaction object with amount \"([^\"]*)\" \\(higher or equal to account balance\\) and from \"([^\"]*)\"$", (String amount, String iban) -> {
            TransactionDTO transaction = new TransactionDTO();
            transaction.setAmount(Double.parseDouble(amount));
            transaction.setFrom(iban);

            newTransactionDTO = transaction;
        });
        When("^I call the post withdraw endpoint$", () -> {
            request = new HttpEntity<>(mapper.writeValueAsString(newTransactionDTO), httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "transactions/" + newTransactionDTO.getFrom() + "/withdraw", HttpMethod.POST, request, String.class);
            status = response.getStatusCodeValue();

            if (status.equals(201)) {
                objectResult = new JSONObject(response.getBody());
            }
        });
    }
}
