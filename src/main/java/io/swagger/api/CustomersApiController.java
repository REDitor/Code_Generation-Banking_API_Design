package io.swagger.api;

import io.swagger.model.NewUserDTO;
import io.swagger.model.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.entity.User;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-16T16:44:39.788Z[GMT]")
@RestController
public class CustomersApiController implements CustomersApi {

    private static final Logger log = LoggerFactory.getLogger(CustomersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private UserService userService;
    @org.springframework.beans.factory.annotation.Autowired
    public CustomersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<UserDTO> createCustomer(@Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema=@Schema()) @Valid @RequestBody NewUserDTO body) {
        ModelMapper modelMapper = new ModelMapper();

        User newUser = modelMapper.map(body, User.class);

        newUser = userService.add(newUser);

        UserDTO response = modelMapper.map(newUser, UserDTO.class);
        return new ResponseEntity<UserDTO>(response,  HttpStatus.CREATED);
    }

    public ResponseEntity<UserDTO> getCustomer(@Parameter(in = ParameterIn.PATH, description = "The customerID of the customer", required=true, schema=@Schema()) @PathVariable("customerId") Integer customerId,@Parameter(in = ParameterIn.QUERY, description = "include list of accounts of selected user" ,schema=@Schema()) @Valid @RequestParam(value = "includeAccountInfo", required = false) Boolean includeAccountInfo) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<UserDTO>(objectMapper.readValue("{\n  \"StreetName\" : \"Pietersbergweg\",\n  \"HouseNumber\" : 1234,\n  \"DailyLimit\" : 500,\n  \"FirstName\" : \"Bruno\",\n  \"ZipCode\" : \"0987 MB\",\n  \"Country\" : \"Netherlands\",\n  \"CustomerId\" : 1,\n  \"LastName\" : \"Coimbra Marques\",\n  \"City\" : \"Amsterdam\",\n  \"BirthDate\" : \"1999-10-12T00:00:00.000+00:00\",\n  \"TransactionAmountLimit\" : 2000\n}", UserDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<UserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<UserDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<UserDTO>> getCustomers(@Parameter(in = ParameterIn.QUERY, description = "include list of accounts of users" ,schema=@Schema()) @Valid @RequestParam(value = "includeAccountInfo", required = false) Boolean includeAccountInfo,@Parameter(in = ParameterIn.QUERY, description = "search for this substring" ,schema=@Schema()) @Valid @RequestParam(value = "name", required = false) String name,@Min(0)@Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "skip", required = false) Integer skip,@Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return" ,schema=@Schema(allowableValues={  }, maximum="50"
)) @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<UserDTO>>(objectMapper.readValue("[ {\n  \"StreetName\" : \"Pietersbergweg\",\n  \"HouseNumber\" : 1234,\n  \"DailyLimit\" : 500,\n  \"FirstName\" : \"Bruno\",\n  \"ZipCode\" : \"0987 MB\",\n  \"Country\" : \"Netherlands\",\n  \"CustomerId\" : 1,\n  \"LastName\" : \"Coimbra Marques\",\n  \"City\" : \"Amsterdam\",\n  \"BirthDate\" : \"1999-10-12T00:00:00.000+00:00\",\n  \"TransactionAmountLimit\" : 2000\n}, {\n  \"StreetName\" : \"Pietersbergweg\",\n  \"HouseNumber\" : 1234,\n  \"DailyLimit\" : 500,\n  \"FirstName\" : \"Bruno\",\n  \"ZipCode\" : \"0987 MB\",\n  \"Country\" : \"Netherlands\",\n  \"CustomerId\" : 1,\n  \"LastName\" : \"Coimbra Marques\",\n  \"City\" : \"Amsterdam\",\n  \"BirthDate\" : \"1999-10-12T00:00:00.000+00:00\",\n  \"TransactionAmountLimit\" : 2000\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<UserDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<UserDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<UserDTO> updateCustomer(@Parameter(in = ParameterIn.PATH, description = "The customerID of the customer", required=true, schema=@Schema()) @PathVariable("customerId") Integer customerId,@Parameter(in = ParameterIn.QUERY, description = "include list of accounts of selected user" ,schema=@Schema()) @Valid @RequestParam(value = "includeAccountInfo", required = false) Boolean includeAccountInfo,@Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema=@Schema()) @Valid @RequestBody NewUserDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<UserDTO>(objectMapper.readValue("{\n  \"StreetName\" : \"Pietersbergweg\",\n  \"HouseNumber\" : 1234,\n  \"DailyLimit\" : 500,\n  \"FirstName\" : \"Bruno\",\n  \"ZipCode\" : \"0987 MB\",\n  \"Country\" : \"Netherlands\",\n  \"CustomerId\" : 1,\n  \"LastName\" : \"Coimbra Marques\",\n  \"City\" : \"Amsterdam\",\n  \"BirthDate\" : \"1999-10-12T00:00:00.000+00:00\",\n  \"TransactionAmountLimit\" : 2000\n}", UserDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<UserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<UserDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

}
