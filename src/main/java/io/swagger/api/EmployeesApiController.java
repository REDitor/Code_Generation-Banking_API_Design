package io.swagger.api;

import io.swagger.model.NewUserEmployeeDTO;
import io.swagger.model.UserEmployeeDTO;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-17T19:48:55.418Z[GMT]")
@RestController
public class EmployeesApiController implements EmployeesApi {

    private static final Logger log = LoggerFactory.getLogger(EmployeesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public EmployeesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<UserEmployeeDTO> createEmployee(@Parameter(in = ParameterIn.DEFAULT, description = "New Employee details", schema=@Schema()) @Valid @RequestBody NewUserEmployeeDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<UserEmployeeDTO>(objectMapper.readValue("{\n  \"StreetName\" : \"Pietersbergweg\",\n  \"Role\" : \"Employee\",\n  \"HouseNumber\" : 1234,\n  \"DailyLimit\" : 0,\n  \"FirstName\" : \"Bruno\",\n  \"ZipCode\" : \"0987 MB\",\n  \"Country\" : \"Netherlands\",\n  \"CustomerId\" : 1,\n  \"LastName\" : \"Coimbra Marques\",\n  \"City\" : \"Amsterdam\",\n  \"BirthDate\" : \"1999-10-12T00:00:00.000+00:00\",\n  \"TransactionAmountLimit\" : 0\n}", UserEmployeeDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<UserEmployeeDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<UserEmployeeDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<UserEmployeeDTO> getEmployee(@Parameter(in = ParameterIn.PATH, description = "the employeeId of the desired employee", required=true, schema=@Schema()) @PathVariable("employeeId") Integer employeeId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<UserEmployeeDTO>(objectMapper.readValue("{\n  \"StreetName\" : \"Pietersbergweg\",\n  \"Role\" : \"Employee\",\n  \"HouseNumber\" : 1234,\n  \"DailyLimit\" : 0,\n  \"FirstName\" : \"Bruno\",\n  \"ZipCode\" : \"0987 MB\",\n  \"Country\" : \"Netherlands\",\n  \"CustomerId\" : 1,\n  \"LastName\" : \"Coimbra Marques\",\n  \"City\" : \"Amsterdam\",\n  \"BirthDate\" : \"1999-10-12T00:00:00.000+00:00\",\n  \"TransactionAmountLimit\" : 0\n}", UserEmployeeDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<UserEmployeeDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<UserEmployeeDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<UserEmployeeDTO>> getEmployees(@Parameter(in = ParameterIn.QUERY, description = "search for this substring" ,schema=@Schema()) @Valid @RequestParam(value = "name", required = false) String name,@Min(0)@Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "offset", required = false) Integer offset,@Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return" ,schema=@Schema(allowableValues={  }, maximum="50"
)) @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<UserEmployeeDTO>>(objectMapper.readValue("[ {\n  \"StreetName\" : \"Pietersbergweg\",\n  \"Role\" : \"Employee\",\n  \"HouseNumber\" : 1234,\n  \"DailyLimit\" : 0,\n  \"FirstName\" : \"Bruno\",\n  \"ZipCode\" : \"0987 MB\",\n  \"Country\" : \"Netherlands\",\n  \"CustomerId\" : 1,\n  \"LastName\" : \"Coimbra Marques\",\n  \"City\" : \"Amsterdam\",\n  \"BirthDate\" : \"1999-10-12T00:00:00.000+00:00\",\n  \"TransactionAmountLimit\" : 0\n}, {\n  \"StreetName\" : \"Pietersbergweg\",\n  \"Role\" : \"Employee\",\n  \"HouseNumber\" : 1234,\n  \"DailyLimit\" : 0,\n  \"FirstName\" : \"Bruno\",\n  \"ZipCode\" : \"0987 MB\",\n  \"Country\" : \"Netherlands\",\n  \"CustomerId\" : 1,\n  \"LastName\" : \"Coimbra Marques\",\n  \"City\" : \"Amsterdam\",\n  \"BirthDate\" : \"1999-10-12T00:00:00.000+00:00\",\n  \"TransactionAmountLimit\" : 0\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<UserEmployeeDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<UserEmployeeDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<UserEmployeeDTO> updateEmployee(@Parameter(in = ParameterIn.PATH, description = "The employeeId of the employee to update", required=true, schema=@Schema()) @PathVariable("employeeId") Integer employeeId,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody NewUserEmployeeDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<UserEmployeeDTO>(objectMapper.readValue("{\n  \"StreetName\" : \"Pietersbergweg\",\n  \"Role\" : \"Employee\",\n  \"HouseNumber\" : 1234,\n  \"DailyLimit\" : 0,\n  \"FirstName\" : \"Bruno\",\n  \"ZipCode\" : \"0987 MB\",\n  \"Country\" : \"Netherlands\",\n  \"CustomerId\" : 1,\n  \"LastName\" : \"Coimbra Marques\",\n  \"City\" : \"Amsterdam\",\n  \"BirthDate\" : \"1999-10-12T00:00:00.000+00:00\",\n  \"TransactionAmountLimit\" : 0\n}", UserEmployeeDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<UserEmployeeDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<UserEmployeeDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

}
