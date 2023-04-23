package io.swagger.steps.account;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.swagger.model.AccountDTO;
import io.swagger.model.LoginDTO;
import io.swagger.model.NewAccountDTO;
import io.swagger.model.entity.AccountType;
import io.swagger.steps.BaseStepDefinitions;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;


public class AccountStepDefs extends BaseStepDefinitions implements En {

    private static final String VALID_TOKEN_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCcnVub01hcnF1ZXMxMjMiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn1dLCJpYXQiOjE2ODIyMDcwMTksImV4cCI6MTY4MjIxMDYxOX0.8NUjXwUrR9KOcmyC55MHK0NU69zKmDdi841ICUvmY_o";
    private static final String VALID_TOKEN_ADMIN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCcnVub01hcnF1ZXMxMjMiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn1dLCJpYXQiOjE2ODIyNjU2MjgsImV4cCI6MTY4MjI2OTIyOH0.hAPvDKj5dRyS4oogtNQYIOF2RxePaTFYIehorPHleKI";
    private static final String INVALID_TOKEN = "invalidtoken";

    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private final ObjectMapper mapper = new ObjectMapper();

    private ResponseEntity<String> response;
    private HttpEntity<String> request;
    private Integer status;

    private String token = null;
    private LoginDTO result = null;
    private AccountDTO newAccountDTO;

    private LoginDTO dto;

    public AccountStepDefs() {
        Given("^I have an valid token for role \"([^\"]*)\" to access accounts$", (String role) -> {
            if (role.equals("admin")) {
                token = VALID_TOKEN_ADMIN;
            }
        });

        Given("^the following new account details:$", (DataTable accountDetails) -> {

            List<Map<String, String>> rows = accountDetails.asMaps(String.class, String.class);
            Map<String, String> accountMap = rows.get(0);

            AccountDTO account = new AccountDTO();
            account.setIBAN(accountMap.get("IBAN"));
            account.setStatus(accountMap.get("Status"));
            account.setBalance(Integer.parseInt(accountMap.get("Balance")));
            account.setMinimumBalance(Integer.parseInt(accountMap.get("MinimumBalance")));
            account.setType(AccountType.valueOf(accountMap.get("Type")));

            newAccountDTO = account;
        });


        When("^I create the account$", () -> {
            httpHeaders.clear();
            httpHeaders.add("Authorization",  "Bearer " + VALID_TOKEN_ADMIN);
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            request = new HttpEntity<>(mapper.writeValueAsString(newAccountDTO), httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "accounts/", HttpMethod.POST, request, String.class);
            status = response.getStatusCodeValue();
        });

        When("^I call get accounts by IBAN \"([^\"]*)\"$", (String iban) -> {
            httpHeaders.clear();
            httpHeaders.add("Authorization",  "Bearer " + VALID_TOKEN_ADMIN);
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/accounts/" + iban, HttpMethod.GET, new HttpEntity<>(null,httpHeaders), String.class);
            status = response.getStatusCodeValue();
        });

        Then("^the response status code should be (\\d+)$", (Integer code) -> {
            Assertions.assertEquals(code, status);
        });

    }
}
