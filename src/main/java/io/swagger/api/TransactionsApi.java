/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.34).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.CreateTransactionDTO;
import io.swagger.model.DepositDTO;
import io.swagger.model.TransactionDTO;
import io.swagger.model.TransactionDepositDTO;
import io.swagger.model.TransactionWithdrawlDTO;
import io.swagger.model.WithdrawDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T12:05:25.016Z[GMT]")
@Validated
public interface TransactionsApi {

    @Operation(summary = "Create a transaction", description = "Create a transaction", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transactions" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "The created transaction object", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDTO.class))),
        
        @ApiResponse(responseCode = "400", description = "invalid input, object invalid"),
        
        @ApiResponse(responseCode = "403", description = "Forbidden, you do not have access rights") })
    @RequestMapping(value = "/transactions",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<TransactionDTO> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "Transaction details", schema=@Schema()) @Valid @RequestBody CreateTransactionDTO body);


    @Operation(summary = "Deposit to selected account.", description = "Deposit to selected account. This method will mostly be used by ATM machines.   Permissions: - Customers ", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transactions" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "The created transaction object", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDepositDTO.class))),
        
        @ApiResponse(responseCode = "400", description = "Bad request. Iban is not in the right format."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized or authorization information is missing or invalid."),
        
        @ApiResponse(responseCode = "404", description = "The account with the specified Iban was not found."),
        
        @ApiResponse(responseCode = "5XX", description = "Unexpected error.") })
    @RequestMapping(value = "/transactions/{iban}/deposit",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<TransactionDepositDTO> deposit(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "The Iban for the account to deposit to", required=true, schema=@Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.DEFAULT, description = "Deposit details", schema=@Schema()) @Valid @RequestBody DepositDTO body);


    @Operation(summary = "Get all transactions by Iban", description = "", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transactions" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "transactions received", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TransactionDTO.class)))) })
    @RequestMapping(value = "/transactions/{iban}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<TransactionDTO>> transactionsIbanGet(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.QUERY, description = "search transaction from dateTime" ,schema=@Schema()) @Valid @RequestParam(value = "dateTimeFrom", required = false) String dateTimeFrom, @Parameter(in = ParameterIn.QUERY, description = "search transaction to dateTime" ,schema=@Schema()) @Valid @RequestParam(value = "dateTimeTo", required = false) String dateTimeTo);

    @Operation(summary = "Get all transactions for all accounts owned by a certain users", description = "", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transactions" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "transactions received", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TransactionDTO.class)))) })
    @RequestMapping(value = "/transactions/users/{userId}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<TransactionDTO>> transactionsGetByUserId(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("userId") UUID userId, @Parameter(in = ParameterIn.QUERY, description = "search transaction from dateTime" ,schema=@Schema()) @Valid @RequestParam(value = "dateTimeFrom", required = false) String dateTimeFrom, @Parameter(in = ParameterIn.QUERY, description = "search transaction to dateTime" ,schema=@Schema()) @Valid @RequestParam(value = "dateTimeTo", required = false) String dateTimeTo);


    @Operation(summary = "Withdraw from selected account.", description = "Withdraw from selected account. This method will mostly be used by ATM machines.   Permissions: - Customers ", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transactions" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "The created transaction object", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionWithdrawlDTO.class))),
        
        @ApiResponse(responseCode = "400", description = "Bad request. Iban is not in the right format."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized or authorization information is missing or invalid."),
        
        @ApiResponse(responseCode = "404", description = "The account with the specified Iban was not found."),
        
        @ApiResponse(responseCode = "5XX", description = "Unexpected error.") })
    @RequestMapping(value = "/transactions/{iban}/withdraw",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<TransactionWithdrawlDTO> withdraw(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "The Iban for the account to withdraw from", required=true, schema=@Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.DEFAULT, description = "Withdraw details", schema=@Schema()) @Valid @RequestBody WithdrawDTO body);

}

