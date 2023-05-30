package io.swagger.steps.customer;

import com.google.gson.GsonBuilder;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java8.En;
import io.swagger.model.UpdateUserDTO;
import io.swagger.steps.BaseStepDefinitions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.google.gson.Gson;
import com.google.gson.FieldNamingPolicy;

import java.lang.reflect.InvocationTargetException;

public class CustomerStepDefs extends BaseStepDefinitions implements En {

    private static final String VALID_TOKEN_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTYW5kZXJIYXJrczEyMyIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfQ1VTVE9NRVIifV0sImlhdCI6MTY4NTQ4MzM1OSwiZXhwIjoxNjg1NDg2OTU5fQ.vibH4jPItop91gx-n0a1u-PqR-3NqO4sDpxvBBxj1tM";
    private static final String VALID_TOKEN_ADMIN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCcnVub01hcnF1ZXMxMjMiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn1dLCJpYXQiOjE2ODU0ODMxNDgsImV4cCI6MTY4NTQ4Njc0OH0.QSC8waCxlJPZJhgOwpBBt3KT9dHuBUeLWInfRxduaHY";

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    private ResponseEntity<String> response;
    private HttpEntity<String> request;
    private Integer status;
    private JSONArray arrayResult;
    private JSONObject objectResult;

    public CustomerStepDefs() {
        Then("^the response status codes should be (\\d+)$", (Integer code) -> {
            Assertions.assertEquals(code, status);
        });

        Given("^I have an valid JWT token for role \"([^\"]*)\"$", (String role) -> {
            if (role.equals("admin")) {
                setHttpHeaders(VALID_TOKEN_ADMIN);
            } else if(role.equals("user")) {
                setHttpHeaders(VALID_TOKEN_USER);
            }
        });

        When("^Fetching all the customers", () -> {
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/customers?skip=0&limit=20&noAccounts=true", HttpMethod.GET, new HttpEntity<>(null, httpHeaders), String.class);
            status = response.getStatusCodeValue();
            if(status.equals(200)) {
                arrayResult = new JSONArray(response.getBody());
            }
        });

        Then("^There should be at least (\\d+) results", (Integer numberOfResults) -> {
                Assert.assertTrue(arrayResult.length() >= numberOfResults);
        });

        Then("^There should be at least an object", () -> {
            Assert.assertTrue(objectResult.length() > 0);
        });

        When("^Fetching a random specific user", () -> {
                String userId = arrayResult.getJSONObject(0).getString("userId");
                // Continue with the existing code for fetching a single user
                request = new HttpEntity<>(null, httpHeaders);
                JSONObject user = new JSONArray(response.getBody()).getJSONObject(1);
                response = restTemplate.exchange(getBaseUrl() + "/customers/" + userId, HttpMethod.GET, request, String.class);
                status = response.getStatusCodeValue();

                if (status.equals(200)) {
                    objectResult = new JSONObject(response.getBody());
                }
        });
        When("^I make a post request to create new customer with the following username \"([^\"]*)\"$", (String username) -> {
            String customer = "{\n" +
                    "  \"BirthDate\": \"2023-05-23\",\n" +
                    "  \"City\": \"Amsterdam\",\n" +
                    "  \"Country\": \"Netherlands\",\n" +
                    "  \"DailyLimit\": 500,\n" +
                    "  \"Email\": \"brunocm@outlook.com\",\n" +
                    "  \"FirstName\": \"Bruno\",\n" +
                    "  \"HouseNumber\": 1234,\n" +
                    "  \"LastName\": \"Coimbra Marques\",\n" +
                    "  \"Password\": \"Test..123\",\n" +
                    "  \"StreetName\": \"Pietersbergweg\",\n" +
                    "  \"TransactionAmountLimit\": 2000,\n" +
                    "  \"Username\": \"" + username + "\",\n" +
                    "  \"ZipCode\": \"0987 MB\"\n" +
                    "}";

            request = new HttpEntity<>(customer, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/customers", HttpMethod.POST, request, String.class);
            status = response.getStatusCodeValue();
        });
        When("^I make a post request to create new customer with wrong parameters$", () -> {
            String customer = "{\n" +
                    "  \"BirthDate\": \"2023-05-23\",\n" +
                    "}";

            request = new HttpEntity<>(customer, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/customers", HttpMethod.POST, request, String.class);
            status = response.getStatusCodeValue();
        });

        And("^Updating a random customer's street name to \"([^\"]*)\"$", (String streetName) -> {
            JSONObject userObject = arrayResult.getJSONObject(0);
            String userId = userObject.getString("userId");
            String updatedCustomer = "{\n" +
                    "    \"FirstName\": \"dummy1\",\n" +
                    "    \"LastName\": \"dummy123\",\n" +
                    "    \"BirthDate\": \"1997-12-07\",\n" +
                    "    \"StreetName\": \"" + streetName + "\",\n" +
                    "    \"HouseNumber\": 123,\n" +
                    "    \"ZipCode\": \"4321AB\",\n" +
                    "    \"City\": \"Haarlem\",\n" +
                    "    \"Country\": \"The Netherlands\",\n" +
                    "    \"Email\": \"bruno@gmail.com\",\n" +
                    "    \"TransactionAmountLimit\": 500,\n" +
                    "    \"DailyLimit\": 2000,\n" +
                    "    \"Roles\": [\"Customer\"],\n" +
                    "    \"Username\": \"dummy1\",\n" +
                    "    \"Password\": \"test..123\"\n" +
                    "}";

            request = new HttpEntity<>(updatedCustomer, httpHeaders);
                response = restTemplate.exchange(getBaseUrl() + "/customers/" + userId, HttpMethod.PUT, request, String.class);
            status = response.getStatusCodeValue();
        });

        Then("^the response should contain the new street name of the customer as \"([^\"]*)\"$", (String streetName) -> {
            String result = response.getBody();
            JSONObject jsonArray = new JSONObject(result);
            String newStreetName = jsonArray.getString("StreetName");

            Assertions.assertEquals(streetName, newStreetName);
        });
    }
}
