package io.swagger.steps.authentication;

        import io.cucumber.java8.En;
        import io.swagger.steps.BaseStepDefinitions;
        import org.junit.jupiter.api.Assertions;

public class AuthenticationStepDefs extends BaseStepDefinitions implements En {
    private Integer status;

    public AuthenticationStepDefs() {
        Then("^the response status code should be as follows: (\\d+)$", (Integer code) -> {
            Assertions.assertEquals(code, status);
        });
    }
}
