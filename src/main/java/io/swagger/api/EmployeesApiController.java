package io.swagger.api;

import io.swagger.model.EmployeeDTO;
import io.swagger.model.NewEmployeeDTO;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-12T21:54:51.581Z[GMT]")
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

    public ResponseEntity<EmployeeDTO> createEmployee(@Parameter(in = ParameterIn.DEFAULT, description = "New Employee details", schema=@Schema()) @Valid @RequestBody NewEmployeeDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<EmployeeDTO>(objectMapper.readValue("{\n  \"StreetName\" : \"Eenhoornstraat\",\n  \"HouseNumber\" : 1234,\n  \"FirstName\" : \"Existing\",\n  \"ZipCode\" : \"1973 SH\",\n  \"Country\" : \"Netherlands\",\n  \"LastName\" : \"Employee\",\n  \"City\" : \"IJmuiden\",\n  \"EmployeeId\" : 1,\n  \"BirthDate\" : \"1997-07-12T00:00:00.000+00:00\"\n}", EmployeeDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<EmployeeDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<EmployeeDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<EmployeeDTO> getEmployee(@Parameter(in = ParameterIn.PATH, description = "the employeeId of the desired employee", required=true, schema=@Schema()) @PathVariable("employeeId") Integer employeeId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<EmployeeDTO>(objectMapper.readValue("{\n  \"StreetName\" : \"Eenhoornstraat\",\n  \"HouseNumber\" : 1234,\n  \"FirstName\" : \"Existing\",\n  \"ZipCode\" : \"1973 SH\",\n  \"Country\" : \"Netherlands\",\n  \"LastName\" : \"Employee\",\n  \"City\" : \"IJmuiden\",\n  \"EmployeeId\" : 1,\n  \"BirthDate\" : \"1997-07-12T00:00:00.000+00:00\"\n}", EmployeeDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<EmployeeDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<EmployeeDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<EmployeeDTO>> getEmployees(@Parameter(in = ParameterIn.QUERY, description = "search for this substring" ,schema=@Schema()) @Valid @RequestParam(value = "name", required = false) String name,@Min(0)@Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "offset", required = false) Integer offset,@Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return" ,schema=@Schema(allowableValues={  }, maximum="50"
)) @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<EmployeeDTO>>(objectMapper.readValue("[ {\n  \"StreetName\" : \"Eenhoornstraat\",\n  \"HouseNumber\" : 1234,\n  \"FirstName\" : \"Existing\",\n  \"ZipCode\" : \"1973 SH\",\n  \"Country\" : \"Netherlands\",\n  \"LastName\" : \"Employee\",\n  \"City\" : \"IJmuiden\",\n  \"EmployeeId\" : 1,\n  \"BirthDate\" : \"1997-07-12T00:00:00.000+00:00\"\n}, {\n  \"StreetName\" : \"Eenhoornstraat\",\n  \"HouseNumber\" : 1234,\n  \"FirstName\" : \"Existing\",\n  \"ZipCode\" : \"1973 SH\",\n  \"Country\" : \"Netherlands\",\n  \"LastName\" : \"Employee\",\n  \"City\" : \"IJmuiden\",\n  \"EmployeeId\" : 1,\n  \"BirthDate\" : \"1997-07-12T00:00:00.000+00:00\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<EmployeeDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<EmployeeDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<EmployeeDTO> updateEmployee(@Parameter(in = ParameterIn.PATH, description = "The employeeId of the employee to update", required=true, schema=@Schema()) @PathVariable("employeeId") Integer employeeId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<EmployeeDTO>(objectMapper.readValue("{\n  \"StreetName\" : \"Eenhoornstraat\",\n  \"HouseNumber\" : 1234,\n  \"FirstName\" : \"Existing\",\n  \"ZipCode\" : \"1973 SH\",\n  \"Country\" : \"Netherlands\",\n  \"LastName\" : \"Employee\",\n  \"City\" : \"IJmuiden\",\n  \"EmployeeId\" : 1,\n  \"BirthDate\" : \"1997-07-12T00:00:00.000+00:00\"\n}", EmployeeDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<EmployeeDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<EmployeeDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

}
