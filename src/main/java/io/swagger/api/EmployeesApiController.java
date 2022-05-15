package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.model.EmployeeDTO;
import io.swagger.model.dto.NewEmployeeDTO;
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
@Api(tags = {"Employees"})
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
        // TODO

        return new ResponseEntity<EmployeeDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<EmployeeDTO> getEmployee(@Parameter(in = ParameterIn.PATH, description = "the employeeId of the desired employee", required=true, schema=@Schema()) @PathVariable("employeeId") Integer employeeId) {
        // TODO

        return new ResponseEntity<EmployeeDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<EmployeeDTO>> getEmployees(@Parameter(in = ParameterIn.QUERY, description = "search for this substring" ,schema=@Schema()) @Valid @RequestParam(value = "name", required = false) String name,@Min(0)@Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "offset", required = false) Integer offset,@Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return" ,schema=@Schema(allowableValues={  }, maximum="50"
)) @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        // TODO

        return new ResponseEntity<List<EmployeeDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<EmployeeDTO> updateEmployee(@Parameter(in = ParameterIn.PATH, description = "The employeeId of the employee to update", required=true, schema=@Schema()) @PathVariable("employeeId") Integer employeeId) {
        // TODO

        return new ResponseEntity<EmployeeDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

}
