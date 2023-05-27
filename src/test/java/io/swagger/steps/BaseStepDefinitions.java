package io.swagger.steps;

import io.swagger.model.LoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import io.swagger.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SpringBootTest(classes = CucumberContextConfig.class)
@Slf4j
public class BaseStepDefinitions {

    @LocalServerPort
    private int port;

    @Value("${io.swagger}")
    private String baseUrl;
    public final HttpHeaders httpHeaders = new HttpHeaders();


    public String getBaseUrl() {
        return baseUrl + port;
    }

    public void setHttpHeaders(String token) {
        httpHeaders.clear();
        httpHeaders.add("Authorization",  "Bearer " + token);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }
}
