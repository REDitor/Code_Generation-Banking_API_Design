/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.34).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.AccountDTO;
import io.swagger.model.AccountIbanDTO;
import io.swagger.model.NewAccountDTO;
import io.swagger.model.UpdateAccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T12:05:25.016Z[GMT]")
@Validated
public interface AccountsApi {

    @Operation(summary = "Creates a new account", description = "Create new Account.  Permissions: - Employees  ", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Account Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
        
        @ApiResponse(responseCode = "400", description = "Bad request. Invalid request body."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized or authorization information is missing or invalid."),
        
        @ApiResponse(responseCode = "5XX", description = "Unexpected error.") })
    @RequestMapping(value = "/accounts",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<AccountDTO> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema=@Schema()) @Valid @RequestBody NewAccountDTO body);


    @Operation(summary = "Gets a account by Iban", description = "Gets a ccount by Iban  Permissions: - Employees - Customers (only if it is their own information) ", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Result of the selected ccount", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
        
        @ApiResponse(responseCode = "400", description = "Bad request. Iban is not in the right format."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized or authorization information is missing or invalid."),
        
        @ApiResponse(responseCode = "404", description = "An account with the specified Iban was not found."),
        
        @ApiResponse(responseCode = "5XX", description = "Unexpected error.") })
    @RequestMapping(value = "/accounts/{iban}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<AccountDTO> getAccount(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "The Iban of the account", required=true, schema=@Schema()) @PathVariable("iban") String iban);

    @Operation(summary = "Gets a account by name", description = "Gets a ccount by name  Permissions: Customers (only if it is their own information) ", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Result of the selected ccount", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad request. Name is not in the right format."),

            @ApiResponse(responseCode = "401", description = "Unauthorized or authorization information is missing or invalid."),

            @ApiResponse(responseCode = "404", description = "An account belonging to the specified person was not found."),

            @ApiResponse(responseCode = "5XX", description = "Unexpected error.") })
    @RequestMapping(value = "/accounts/{iban}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<AccountDTO> getAccountByName(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "The name of the owner of the account", required=true, schema=@Schema()) @PathVariable("name") String name);


    @Operation(summary = "Update account information", description = "Update Account information.  However, it is only possible to change the type of the account, and the amount limit.  Permissions: - Employees - Customers (only if it is their own information) ", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Result after updating account", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))),
        
        @ApiResponse(responseCode = "400", description = "Bad request. Iban is not in the right format."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized or authorization information is missing or invalid."),
        
        @ApiResponse(responseCode = "404", description = "An account with the specified Iban was not found."),
        
        @ApiResponse(responseCode = "5XX", description = "Unexpected error.") })
    @RequestMapping(value = "/accounts/{iban}",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<AccountDTO> updateAccount(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "The Iban of the account", required=true, schema=@Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.DEFAULT, description = "Fields that need to be updated", schema=@Schema()) @Valid @RequestBody UpdateAccountDTO body);

}

