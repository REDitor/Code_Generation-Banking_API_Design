package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.model.CustomerDTO;
import io.swagger.model.dto.NewCustomerDTO;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-12T21:54:51.581Z[GMT]")
@Api(tags = {"Customers"})
@RestController
public class CustomersApiController implements CustomersApi {

    private static final Logger log = LoggerFactory.getLogger(CustomersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public CustomersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<CustomerDTO> createCustomer(@Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema=@Schema()) @Valid @RequestBody NewCustomerDTO body) {
        // TODO

        return new ResponseEntity<CustomerDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<CustomerDTO> getCustomer(@Parameter(in = ParameterIn.PATH, description = "The customerID of the customer", required=true, schema=@Schema()) @PathVariable("customerId") Integer customerId,@Parameter(in = ParameterIn.QUERY, description = "include list of accounts of selected user" ,schema=@Schema()) @Valid @RequestParam(value = "includeAccountInfo", required = false) Boolean includeAccountInfo) {
        // TODO

        return new ResponseEntity<CustomerDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<CustomerDTO>> getCustomers(@Parameter(in = ParameterIn.QUERY, description = "include list of accounts of users" ,schema=@Schema()) @Valid @RequestParam(value = "includeAccountInfo", required = false) Boolean includeAccountInfo,@Parameter(in = ParameterIn.QUERY, description = "search for this substring" ,schema=@Schema()) @Valid @RequestParam(value = "name", required = false) String name,@Min(0)@Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "skip", required = false) Integer skip,@Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return" ,schema=@Schema(allowableValues={  }, maximum="50"
)) @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        // TODO

        return new ResponseEntity<List<CustomerDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<CustomerDTO> updateCustomer(@Parameter(in = ParameterIn.PATH, description = "The customerID of the customer", required=true, schema=@Schema()) @PathVariable("customerId") Integer customerId,@Parameter(in = ParameterIn.QUERY, description = "include list of accounts of selected user" ,schema=@Schema()) @Valid @RequestParam(value = "includeAccountInfo", required = false) Boolean includeAccountInfo,@Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema=@Schema()) @Valid @RequestBody NewCustomerDTO body) {
        // TODO

        return new ResponseEntity<CustomerDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

}
