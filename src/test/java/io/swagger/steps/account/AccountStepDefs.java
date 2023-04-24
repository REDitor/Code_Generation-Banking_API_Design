package io.swagger.steps.account;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.swagger.model.AccountDTO;
import io.swagger.model.LoginDTO;
import io.swagger.model.NewAccountDTO;
import io.swagger.model.UserDTO;
import io.swagger.model.entity.AccountType;
import io.swagger.model.entity.User;
import io.swagger.service.UserService;
import io.swagger.steps.BaseStepDefinitions;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.json.*;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class AccountStepDefs extends BaseStepDefinitions implements En {

    private static final String VALID_TOKEN_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCcnVub01hcnF1ZXMxMjMiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn1dLCJpYXQiOjE2ODIyMDcwMTksImV4cCI6MTY4MjIxMDYxOX0.8NUjXwUrR9KOcmyC55MHK0NU69zKmDdi841ICUvmY_o";
    private static final String VALID_TOKEN_ADMIN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCcnVub01hcnF1ZXMxMjMiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn1dLCJpYXQiOjE2ODIzNzE4MzUsImV4cCI6MTY4MjM3NTQzNX0.I4MMPS3An4Hh6lbAkCstvlk9M84yqRbA4ohPn6nG-V8";

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    private ResponseEntity<String> response;
    private HttpEntity<String> request;
    private Integer status;
    private NewAccountDTO newAccountDTO;
    private UUID randomUserID;

    private LoginDTO dto;

    public AccountStepDefs() {
        Given("^I have an valid token for role \"([^\"]*)\" to access accounts$", (String role) -> {
            if (role.equals("admin")) {
                setHttpHeaders(VALID_TOKEN_ADMIN);
            } else {
                setHttpHeaders(VALID_TOKEN_USER);
            }
        });

        Given("^the following new account details:$", (DataTable accountDetails) -> {

            List<Map<String, String>> rows = accountDetails.asMaps(String.class, String.class);
            Map<String, String> accountMap = rows.get(0);

            NewAccountDTO account = new NewAccountDTO();
            account.setStatus(accountMap.get("Status"));
            account.setMinimumBalance(Integer.parseInt(accountMap.get("MinimumBalance")));
            account.setType("Type");

            newAccountDTO = account;
        });

        And("^i get a user without an account$", () -> {
            request = new HttpEntity<>(mapper.writeValueAsString(newAccountDTO), httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/customers?skip=0&limit=1&noAccounts=true", HttpMethod.GET, new HttpEntity<>(null, httpHeaders), String.class);
            String result = response.getBody();
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String userId = jsonObject.getString("userId");
            randomUserID = UUID.fromString(userId);
        });


        When("^i create the account$", () -> {
            newAccountDTO.setUserID(randomUserID);

            request = new HttpEntity<>(mapper.writeValueAsString(newAccountDTO), httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "accounts/", HttpMethod.POST, request, String.class);
            status = response.getStatusCodeValue();
        });

        When("^I call get accounts by IBAN \"([^\"]*)\"$", (String iban) -> {
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/accounts/" + iban, HttpMethod.GET, new HttpEntity<>(null, httpHeaders), String.class);
            status = response.getStatusCodeValue();
        });

        Then("^the response status code should be (\\d+)$", (Integer code) -> {
            Assertions.assertEquals(code, status);
        });
    }
}
