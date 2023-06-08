package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.model.ErrorMessageDTO;
import io.swagger.model.LoginDTO;
import io.swagger.model.LoginInputDTO;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T14:26:03.164Z[GMT]")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = "Authentication")
@RestController
@RequestMapping("login")
public class LoginApiController implements LoginApi {

    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    UserService userService;

    public LoginApiController(UserService userService, ObjectMapper objectMapper, HttpServletRequest request) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PostMapping
    public ResponseEntity<LoginDTO> login(@Parameter(in = ParameterIn.DEFAULT, description = "Login Information", schema=@Schema()) @Valid @RequestBody LoginInputDTO body) {
        String token = "";

        LoginDTO result = userService.login(body.getUsername(), body.getPassword());

        if (result == null){
            return new ResponseEntity(new ErrorMessageDTO("Wrong login credentials."), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<LoginDTO>(result,  HttpStatus.OK);
    }

}
