package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.model.dto.CustomerWithAccountsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-12T21:54:51.581Z[GMT]")
@Api(tags = {"Authentication"})
@RestController
public class LoginApiController implements LoginApi {

    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public LoginApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<CustomerWithAccountsDTO>> login(@Parameter(in = ParameterIn.PATH, description = "Email", required=true, schema=@Schema()) @PathVariable("email") String email,@Parameter(in = ParameterIn.PATH, description = "Password", required=true, schema=@Schema()) @PathVariable("password") String password) {
        // TODO

        return new ResponseEntity<List<CustomerWithAccountsDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
