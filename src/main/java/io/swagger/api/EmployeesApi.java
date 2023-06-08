/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.34).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.NewUserDTO;
import io.swagger.model.UpdateUserDTO;
import io.swagger.model.UserDTO;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-31T12:15:45.146Z[GMT]")
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
public interface EmployeesApi {

    @Operation(summary = "Creates a new Employee", description = "Create new Employee", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Employee Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
        
        @ApiResponse(responseCode = "400", description = "Bad request. Invalid request body."),
        
        @ApiResponse(responseCode = "401", description = "Authorization information missing or invalid"),
        
        @ApiResponse(responseCode = "404", description = "Something went wrong."),
        
        @ApiResponse(responseCode = "5XX", description = "Internal Server Error.") })
    @RequestMapping(value = "/employees",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<UserDTO> createEmployee(@Parameter(in = ParameterIn.DEFAULT, description = "New Employee details", schema=@Schema()) @Valid @RequestBody NewUserDTO body);


    @Operation(summary = "Get an employee by Id", description = "Gets a specific Employee by Id", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Result of the selected employee", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
        
        @ApiResponse(responseCode = "400", description = "Bad request."),
        
        @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
        
        @ApiResponse(responseCode = "404", description = "Employee with the specified ID was not found."),
        
        @ApiResponse(responseCode = "5XX", description = "Unexpected error.") })
    @RequestMapping(value = "/employees/{employeeId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<UserDTO> getEmployee(@Parameter(in = ParameterIn.PATH, description = "the employeeId of the desired employee", required=true, schema=@Schema()) @PathVariable("employeeId") UUID employeeId);


    @Operation(summary = "Gets all employees available", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "All accounts", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))),
        
        @ApiResponse(responseCode = "400", description = "Bad request."),
        
        @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
        
        @ApiResponse(responseCode = "404", description = "Something went wrong."),
        
        @ApiResponse(responseCode = "5XX", description = "Internal Server Error.") })
    @RequestMapping(value = "/employees",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<UserDTO>> getEmployees(@Parameter(in = ParameterIn.QUERY, description = "search for this substring", schema = @Schema()) @Valid @RequestParam(value = "firstName", required = false) String firstName, @Parameter(in = ParameterIn.QUERY, description = "search for lastname", schema = @Schema()) @Valid @RequestParam(value = "lastName", required = false) String lastName, @Min(0)@Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "offset", required = false) Integer offset, @Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return" ,schema=@Schema(allowableValues={  }, maximum="50"
)) @Valid @RequestParam(value = "limit", required = false) Integer limit);


    @Operation(summary = "Updates employee by Id", description = "Updates employee by ID", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Result of the modified employee", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
        
        @ApiResponse(responseCode = "400", description = "Bad request. Invalid request body."),
        
        @ApiResponse(responseCode = "401", description = "Authorization information is missing or invalid."),
        
        @ApiResponse(responseCode = "404", description = "A user with the specified ID was not found."),
        
        @ApiResponse(responseCode = "5XX", description = "Unexpected error.") })
    @RequestMapping(value = "/employees/{userID}",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<UserDTO> updateEmployee(@Parameter(in = ParameterIn.PATH, description = "The employeeId of the employee to update", required=true, schema=@Schema()) @PathVariable("userID") UUID userID, @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody UpdateUserDTO body);

}

