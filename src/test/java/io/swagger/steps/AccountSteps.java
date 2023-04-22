package io.swagger.steps;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java8.En;
import io.swagger.model.AccountDTO;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
@SpringBootTest(classes = CucumberSpringConfig.class)
@Slf4j
public class AccountSteps implements En {

    private static final String VALID_TOKEN_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbXJpc2giLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NTkwMjMwMiwiZXhwIjoxNjU1OTA1OTAyfQ.6_Y033QiO66dvqVHceCEqyOaPWutsm4hRZKSIJ06ocg";
    private static final String VALID_TOKEN_ADMIN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XSwiaWF0IjoxNjU1NjY3NTk0LCJleHAiOjE2NTYyNzIzOTR9.XI7nat8c9C1oxrLkFydif3C6qtdzIIg6OGoiRcjLr6E";
    private static final String INVALID_TOKEN = "invalidtoken";
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private final ObjectMapper mapper = new ObjectMapper();

    private ResponseEntity<String> response;
    private HttpEntity<String> request;
    private Integer status;

    private String token = null;

    private AccountDTO accountDTO;
    @LocalServerPort
    private int port;

    @Value("${io.swagger}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl + port;
    }

    public AccountSteps() {
        Given("^I have an valid token for role \"([^\"]*)\" to access accounts$", (String role) -> {
            if (role.equals("admin")) {
                token = VALID_TOKEN_ADMIN;
            }
        });

        When("^I call get accounts by IBAN \"([^\"]*)\"$", (String iban) -> {
            httpHeaders.clear();
            httpHeaders.add("Authorization",  "Bearer " + token);
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/accounts/" + iban, HttpMethod.GET, new HttpEntity<>(null,httpHeaders), String.class);
            status = response.getStatusCodeValue();
        });

        Then("^I receive a status code of (\\d+)$", (Integer code) -> {
            Assertions.assertEquals(code, status);
        });
    }
}
