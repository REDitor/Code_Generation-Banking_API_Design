package io.swagger.api;

import io.swagger.model.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;


import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T14:26:03.164Z[GMT]")
@RestController
public class LoginApiController implements LoginApi {

    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public LoginApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<LoginDTO> login(@Parameter(in = ParameterIn.PATH, description = "username", required=true, schema=@Schema()) @PathVariable("username") String username,@Parameter(in = ParameterIn.PATH, description = "Password", required=true, schema=@Schema()) @PathVariable("password") String password) {
        String token = "";

        String result = userService.login(username, password);

        LoginDTO loginInfo = new LoginDTO();
        loginInfo.setJwtToken(result);

        return new ResponseEntity<LoginDTO>(loginInfo,  HttpStatus.OK);
    }

}
