package io.swagger.api;

import io.swagger.model.UserCustomerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T12:05:25.016Z[GMT]")
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

    public ResponseEntity<List<UserCustomerDTO>> login(@Parameter(in = ParameterIn.PATH, description = "Email", required=true, schema=@Schema()) @PathVariable("email") String email,@Parameter(in = ParameterIn.PATH, description = "Password", required=true, schema=@Schema()) @PathVariable("password") String password) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<UserCustomerDTO>>(objectMapper.readValue("[ {\n  \"StreetName\" : \"Pietersbergweg\",\n  \"HouseNumber\" : 1234,\n  \"Email\" : \"brunocm@pm.me\",\n  \"DailyLimit\" : 500,\n  \"FirstName\" : \"Bruno\",\n  \"ZipCode\" : \"0987 MB\",\n  \"City\" : \"Amsterdam\",\n  \"TransactionAmountLimit\" : 2000,\n  \"Role\" : \"Customer\",\n  \"Username\" : \"brumarq\",\n  \"UserId\" : \"123e4567-e89b-12d3-a456-426614174000\",\n  \"Country\" : \"Netherlands\",\n  \"LastName\" : \"Coimbra Marques\",\n  \"BirthDate\" : \"1999-10-12T00:00:00.000+00:00\",\n  \"Password\" : \"test..123\"\n}, {\n  \"StreetName\" : \"Pietersbergweg\",\n  \"HouseNumber\" : 1234,\n  \"Email\" : \"brunocm@pm.me\",\n  \"DailyLimit\" : 500,\n  \"FirstName\" : \"Bruno\",\n  \"ZipCode\" : \"0987 MB\",\n  \"City\" : \"Amsterdam\",\n  \"TransactionAmountLimit\" : 2000,\n  \"Role\" : \"Customer\",\n  \"Username\" : \"brumarq\",\n  \"UserId\" : \"123e4567-e89b-12d3-a456-426614174000\",\n  \"Country\" : \"Netherlands\",\n  \"LastName\" : \"Coimbra Marques\",\n  \"BirthDate\" : \"1999-10-12T00:00:00.000+00:00\",\n  \"Password\" : \"test..123\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<UserCustomerDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<UserCustomerDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
