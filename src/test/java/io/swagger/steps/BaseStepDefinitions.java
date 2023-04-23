package io.swagger.steps;

import io.swagger.model.LoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import io.swagger.service.UserService;

@SpringBootTest(classes = CucumberContextConfig.class)
@Slf4j
public class BaseStepDefinitions {

    @LocalServerPort
    private int port;

    @Value("${io.swagger}")
    private String baseUrl;

    UserService userService = new UserService();

    public String getBaseUrl() {
        return baseUrl + port;
    }

    public LoginDTO authenticate() {

        LoginDTO result = userService.login("BrunoMarques123", "secret123");

        return result;
    }
}
