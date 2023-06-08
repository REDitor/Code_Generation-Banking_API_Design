package io.swagger.steps.login;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java8.En;
import io.swagger.steps.BaseStepDefinitions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class LoginStepDefs extends BaseStepDefinitions implements En {
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    private ResponseEntity<String> response;
    private HttpEntity<String> request;
    private Integer status;
    private JSONArray arrayResult;
    private JSONObject objectResult;

    public LoginStepDefs() {
        Then("^the response status should be (\\d+)$", (Integer code) -> {
            Assertions.assertEquals(code, status);
        });
        When("^Login in with \"([^\"]*)\" and \"([^\"]*)\"$", (String username, String password) -> {
            String customer = "{\n" +
                    "  \"Username\": \""+username+"\",\n" +
                    "  \"Password\": \""+password+"\"\n" +
                    "}";
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            request = new HttpEntity<>(customer, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/login/", HttpMethod.POST, request, String.class);
            status = response.getStatusCodeValue();
        });


    }
}
