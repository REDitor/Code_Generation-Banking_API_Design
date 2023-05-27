package io.swagger.steps.account;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.swagger.model.LoginDTO;
import io.swagger.model.NewAccountDTO;
import io.swagger.model.UpdateAccountDTO;
import io.swagger.steps.BaseStepDefinitions;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.json.*;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class AccountStepDefs extends BaseStepDefinitions implements En {
    private static final String VALID_TOKEN_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTYW5kZXJIYXJrczEyMyIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfQ1VTVE9NRVIifV0sImlhdCI6MTY4NTIwMTA1OSwiZXhwIjoxNjg1MjA0NjU5fQ.GLd3CRzR67U2I397tUbdFuUKXlq-qyL_CDCi_3KReog";
    private static final String VALID_TOKEN_ADMIN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCcnVub01hcnF1ZXMxMjMiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn1dLCJpYXQiOjE2ODUyMDA5OTIsImV4cCI6MTY4NTIwNDU5Mn0.CVNOpjDomVZ-db0-AGFSQtzHUHN0xOIuOqLjo2RlfYs";

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    private ResponseEntity<String> response;
    private HttpEntity<String> request;
    private Integer status;
    private NewAccountDTO newAccountDTO;
    private UUID randomUserID;
    private JSONObject amount;

    public AccountStepDefs() {
        Given("^I have an valid token for role \"([^\"]*)\"$", (String role) -> {
            if (role.equals("admin")) {
                setHttpHeaders(VALID_TOKEN_ADMIN);
            } else if(role.equals("user")) {
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
            setHttpHeaders(VALID_TOKEN_ADMIN);
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

        When("^Fetching accounts by the name \"([^\"]*)\"$", (String name) -> {
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/accounts/ibans/" + name, HttpMethod.GET, new HttpEntity<>(null, httpHeaders), String.class);
            status = response.getStatusCodeValue();
        });

        When("I call get customers without accounts", () -> {
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/customers?skip=0&limit=5&noAccounts=true", HttpMethod.GET, new HttpEntity<>(null, httpHeaders), String.class);
            status = response.getStatusCodeValue();
        });

        When("^I get the total balance using the UserID", () -> {
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/accounts/totalBalance/" + randomUserID, HttpMethod.GET, new HttpEntity<>(null, httpHeaders), String.class);
            status = response.getStatusCodeValue();
            if(status.equals(200)) {
                amount = new JSONObject(response.getBody());
            }
        });

        Then("^the balance amount should be (.+)$", (Double selectedAmount) -> {
            Double storedAmount = amount.getDouble("totalBalance");
            Assertions.assertEquals(storedAmount, selectedAmount);
        });

        Then("^the response status code should be (\\d+)$", (Integer code) -> {
            Assertions.assertEquals(code, status);
        });

        When("^I change the status of account \"([^\"]*)\" to \"([^\"]*)\"$", (String IBAN, String givenStatus) -> {

            String object = "{\n" +
                            "  \"MinimumBalance\": 0,\n" +
                            "  \"Status\": \"" + givenStatus + "\",\n" +
                            "  \"Type\": \"Current\"\n" +
                            "}";

            request = new HttpEntity<>(object, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/accounts/NL01INHO0000000001", HttpMethod.PUT, request, String.class);
            status = response.getStatusCodeValue();
        });
        Then("^the response should contain the new status of the account as \"([^\"]*)\"$", (String status) -> {
            String result = response.getBody();
            JSONObject jsonArray = new JSONObject(result);
            String statusResponse = jsonArray.getString("Status");

            Assertions.assertEquals(status, statusResponse);
        });
    }
}
