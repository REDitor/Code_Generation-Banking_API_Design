package io.swagger.steps.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java8.En;
import io.swagger.TokenHolder;
import io.swagger.model.UserDTO;
import io.swagger.steps.BaseStepDefinitions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class EmployeeStepDefs extends BaseStepDefinitions implements En {

//    private static final String VALID_TOKEN_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTYW5kZXJIYXJrczEyMyIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfQ1VTVE9NRVIifV0sImlhdCI6MTY4NTUzNDUzMiwiZXhwIjoxNjg1NTM4MTMyfQ.XtIzyh7sLh1DSixDSuwjfVzxQSM86ZkIwuUOmoc97JQ";
//    private static final String VALID_TOKEN_ADMIN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCcnVub01hcnF1ZXMxMjMiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn1dLCJpYXQiOjE2ODU1MzQ1NzgsImV4cCI6MTY4NTUzODE3OH0.7a0klyBtSqhgnL8CkmBlfDYfbV8xVs0dx2kUuUHRy_Y";
//    private static final String INVALID_TOKEN = "InvalidToken";

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;
    private HttpEntity<String> request;
    private Integer status;
    private String newEmployee;
    private JSONArray arrayResult;
    private JSONObject objectResult;

    public EmployeeStepDefs() {
        Given("^I have a valid authentication token for role \"([^\"]*)\"$", (String role) -> {
            if (role.equals("admin")) {
                setHttpHeaders(TokenHolder.VALID_TOKEN_ADMIN);
            } else if (role.equals("user")) {
                setHttpHeaders(TokenHolder.VALID_TOKEN_USER);
            }
        });
        Given("^I have an invalid authentication token for role \"([^\"]*)\"$", (String role) -> {
            setHttpHeaders(TokenHolder.INVALID_TOKEN);
        });
        Given("^I have an invalid authentication token$", () -> {
            setHttpHeaders(TokenHolder.INVALID_TOKEN);
        });

        Then("^The status code is (\\d+)$", (Integer statusCode) -> {
            Assertions.assertEquals(statusCode, status);
        });
        And("^I get a response body with at least (\\d+) employee objects$", (Integer numberOfResults) -> {
            Assert.assertTrue(arrayResult.length() >= numberOfResults);
        });
        And("^I get a response with an employee object$", () -> {
            Assert.assertTrue(objectResult.length() > 0);
        });
        And("^I have a new employee object with username \"([^\"]*)\"$", (String username) -> {
            newEmployee = "{\n" +
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
        });
        When("^I call the post new employee endpoint$", () -> {
            request = new HttpEntity<>(newEmployee, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/employees", HttpMethod.POST, request, String.class);
            status = response.getStatusCodeValue();

            if (status.equals(201)) {
                objectResult = new JSONObject(response.getBody());
            }
        });
        And("^I have a new employee object with wrong parameters$", () -> {
            newEmployee = "{\n" +
                    "  \"BirthDate\": \"2023-05-23\",\n" +
                    "}";
        });
        When("^I call the get all employees endpoint$", () -> {
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/employees?offset=0&limit=20", HttpMethod.GET, new HttpEntity<>(null, httpHeaders), String.class);
            status = response.getStatusCodeValue();

            if (status.equals(200)) {
                arrayResult = new JSONArray(response.getBody());
            }
        });

    }
}

