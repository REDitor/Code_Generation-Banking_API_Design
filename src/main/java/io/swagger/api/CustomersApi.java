/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.34).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.NewUserCustomerDTO;
import java.util.UUID;

import io.swagger.model.UpdateUserCustomerDTO;
import io.swagger.model.UserCustomerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T12:05:25.016Z[GMT]")
@Validated
public interface CustomersApi {

    @Operation(summary = "Creates a new customer", description = "Create new Customer.", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Customers" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Account Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCustomerDTO.class))),
        
        @ApiResponse(responseCode = "400", description = "Bad request. Invalid request body."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized or authorization information is missing or invalid."),
        
        @ApiResponse(responseCode = "404", description = "A user with the specified ID was not found."),
        
        @ApiResponse(responseCode = "5XX", description = "Unexpected error.") })
    @RequestMapping(value = "/customers",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<UserCustomerDTO> createCustomer(@Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema=@Schema()) @Valid @RequestBody NewUserCustomerDTO body);


    @Operation(summary = "Gets a customer by ID", description = "Gets a customer by ID", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Customers" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Result of the selected customer", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCustomerDTO.class))),
        
        @ApiResponse(responseCode = "400", description = "Bad request. User ID must be an integer and larger than 0."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized or authorization information is missing or invalid."),
        
        @ApiResponse(responseCode = "404", description = "A user with the specified ID was not found."),
        
        @ApiResponse(responseCode = "5XX", description = "Unexpected error.") })
    @RequestMapping(value = "/customers/{userID}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<UserCustomerDTO> getCustomer(@Parameter(in = ParameterIn.PATH, description = "The userID of the customer", required=true, schema=@Schema()) @PathVariable("userID") UUID userID);


    @Operation(summary = "Gets all the customers available", description = "Gets customers in the system", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Customers" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "The accounts", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserCustomerDTO.class)))),
        
        @ApiResponse(responseCode = "400", description = "Bad request. User ID must be an integer and larger than 0."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized or authorization information is missing or invalid."),
        
        @ApiResponse(responseCode = "404", description = "A user with the specified ID was not found."),
        
        @ApiResponse(responseCode = "5XX", description = "Unexpected error.") })
    @RequestMapping(value = "/customers",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<UserCustomerDTO>> getCustomers(@Parameter(in = ParameterIn.QUERY, description = "search for this substring" ,schema=@Schema()) @Valid @RequestParam(value = "name", required = false) String name, @Min(0)@Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "skip", required = false) Integer skip, @Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return" ,schema=@Schema(allowableValues={  }, maximum="50"
)) @Valid @RequestParam(value = "limit", required = false) Integer limit);


    @Operation(summary = "Updates user selected ID", description = "Updates user selected ID", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Customers" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Result of the modified customer", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCustomerDTO.class))),
        
        @ApiResponse(responseCode = "400", description = "Bad request. Invalid request body."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized or authorization information is missing or invalid."),
        
        @ApiResponse(responseCode = "404", description = "A user with the specified ID was not found."),
        
        @ApiResponse(responseCode = "5XX", description = "Unexpected error.") })
    @RequestMapping(value = "/customers/{userID}",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<UserCustomerDTO> updateCustomer(@Parameter(in = ParameterIn.PATH, description = "The userID of the customer", required=true, schema=@Schema()) @PathVariable("userID") UUID userID, @Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema=@Schema()) @Valid @RequestBody UpdateUserCustomerDTO body);

}

