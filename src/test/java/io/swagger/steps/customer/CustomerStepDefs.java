package io.swagger.steps.customer;

import io.cucumber.java8.En;
import io.swagger.steps.BaseStepDefinitions;
import org.junit.jupiter.api.Assertions;

public class CustomerStepDefs extends BaseStepDefinitions implements En {
    private Integer status;

    public CustomerStepDefs() {
        Then("^the response status code should be this (\\d+)$", (Integer code) -> {
            Assertions.assertEquals(code, status);
        });
    }
}
