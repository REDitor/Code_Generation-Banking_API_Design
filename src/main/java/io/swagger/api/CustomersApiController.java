package io.swagger.api;

import io.swagger.model.NewUserCustomerDTO;
import io.swagger.model.UserCustomerDTO;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-17T19:48:55.418Z[GMT]")
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

    public ResponseEntity<UserCustomerDTO> createCustomer(@Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema=@Schema()) @Valid @RequestBody NewUserCustomerDTO body) {
        ModelMapper modelMapper = new ModelMapper();

        User newUser = modelMapper.map(body, User.class);
        newUser.setRole("Customer");

        newUser = userService.add(newUser);

        UserCustomerDTO response = modelMapper.map(newUser, UserCustomerDTO.class);
        return new ResponseEntity<UserCustomerDTO>(response,  HttpStatus.CREATED);
    }

    public ResponseEntity<UserCustomerDTO> getCustomer(@Parameter(in = ParameterIn.PATH, description = "The customerID of the customer", required=true, schema=@Schema()) @PathVariable("customerId") Integer customerId,@Parameter(in = ParameterIn.QUERY, description = "include list of accounts of selected user" ,schema=@Schema()) @Valid @RequestParam(value = "includeAccountInfo", required = false) Boolean includeAccountInfo) {
        // TODO

        return new ResponseEntity<UserCustomerDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<UserCustomerDTO>> getCustomers(@Parameter(in = ParameterIn.QUERY, description = "include list of accounts of users" ,schema=@Schema()) @Valid @RequestParam(value = "includeAccountInfo", required = false) Boolean includeAccountInfo,@Parameter(in = ParameterIn.QUERY, description = "search for this substring" ,schema=@Schema()) @Valid @RequestParam(value = "name", required = false) String name,@Min(0)@Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "skip", required = false) Integer skip,@Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return" ,schema=@Schema(allowableValues={  }, maximum="50"
)) @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        // TODO

        return new ResponseEntity<List<UserCustomerDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<UserCustomerDTO> updateCustomer(@Parameter(in = ParameterIn.PATH, description = "The customerID of the customer", required=true, schema=@Schema()) @PathVariable("customerId") Integer customerId,@Parameter(in = ParameterIn.QUERY, description = "include list of accounts of selected user" ,schema=@Schema()) @Valid @RequestParam(value = "includeAccountInfo", required = false) Boolean includeAccountInfo,@Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema=@Schema()) @Valid @RequestBody NewUserCustomerDTO body) {
        // TODO

        return new ResponseEntity<UserCustomerDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

}
