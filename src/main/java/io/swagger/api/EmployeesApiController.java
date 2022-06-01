package io.swagger.api;

import io.swagger.model.ErrorMessageDTO;
import io.swagger.model.NewUserEmployeeDTO;

import java.util.Collections;
import java.util.LinkedList;
import java.util.UUID;

import io.swagger.model.UpdateUserEmployeeDTO;
import io.swagger.model.UserEmployeeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T12:05:25.016Z[GMT]")
@RestController
public class EmployeesApiController implements EmployeesApi {

    private static final Logger log = LoggerFactory.getLogger(EmployeesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public EmployeesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserEmployeeDTO> createEmployee(@Parameter(in = ParameterIn.DEFAULT, description = "New Employee details", schema=@Schema()) @Valid @RequestBody NewUserEmployeeDTO body) {

        // Make sure all the fields got filled properly
        if (!(body.getFirstName().length() > 1 &&
                body.getLastName().length() > 1 &&
                body.getStreetName().length() > 2 &&
                body.getHouseNumber() > 0 &&
                body.getZipCode().length() > 3 &&
                body.getCity().length() > 3 &&
                body.getCountry().length() > 3 &&
                body.getTransactionAmountLimit() >= 0 &&
                body.getDailyLimit() >= 0 &&
                body.getUsername().length() > 4 &&
                body.getPassword().length() > 5)
        ) {
            return new ResponseEntity(new ErrorMessageDTO("Bad request. Invalid request body."), HttpStatus.BAD_REQUEST);
        }

        ModelMapper modelMapper = new ModelMapper();
        User newUser = modelMapper.map(body, User.class);

        // Check if username is already in use
        if (userService.getUserByUsername(newUser.getUsername()) != null) {
            return new ResponseEntity(new ErrorMessageDTO("Username already exists."), HttpStatus.BAD_REQUEST);
        }

        newUser.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));
        newUser = userService.add(newUser);

        UserEmployeeDTO response = modelMapper.map(newUser, UserEmployeeDTO.class);
        return new ResponseEntity<UserEmployeeDTO>(response,  HttpStatus.CREATED);
    }

    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserEmployeeDTO> getEmployee(@Parameter(in = ParameterIn.PATH, description = "the employeeId of the desired employee", required=true, schema=@Schema()) @PathVariable("employeeId") UUID employeeId) {

        if (employeeId.toString().length() < 8) {
            return new ResponseEntity(new ErrorMessageDTO("Bad request. Invalid request parameters."), HttpStatus.BAD_REQUEST);
        }

        ModelMapper modelMapper = new ModelMapper();
        User receivedUser = userService.getOneEmployee(employeeId);

        if (receivedUser == null) {
            return new ResponseEntity(new ErrorMessageDTO("Employee not found."), HttpStatus.NOT_FOUND);
        }

        UserEmployeeDTO response = modelMapper.map(receivedUser, UserEmployeeDTO.class);
        return new ResponseEntity<UserEmployeeDTO>(response,  HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<UserEmployeeDTO>> getEmployees(@Parameter(in = ParameterIn.QUERY, description = "search for this substring" ,schema=@Schema()) @Valid @RequestParam(value = "name", required = false) String name,@Min(0)@Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "offset", required = false) Integer offset,@Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return" ,schema=@Schema(allowableValues={  }, maximum="50"
)) @Valid @RequestParam(value = "limit", required = false) Integer limit) {

        // Check if pagination was set
        if (offset < 0 || limit < 1) {
            return new ResponseEntity(new ErrorMessageDTO("Bad request. Invalid request parameters."), HttpStatus.BAD_REQUEST);
        }

        Pageable page = PageRequest.of(offset, limit);

        ModelMapper modelMapper = new ModelMapper();
        List<User> receivedUser = userService.getAllEmployees(page);
        List<UserEmployeeDTO> entityToDto = modelMapper.map(receivedUser, new TypeToken<List<UserEmployeeDTO>>(){}.getType());
        return new ResponseEntity<List<UserEmployeeDTO>>(entityToDto,  HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserEmployeeDTO> updateEmployee(@Parameter(in = ParameterIn.PATH, description = "The employeeId of the employee to update", required=true, schema=@Schema()) @PathVariable("employeeId") UUID employeeId,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody UpdateUserEmployeeDTO body) {

        // Make sure all the fields got filled properly
        if (!(body.getFirstName().length() > 1 &&
                body.getLastName().length() > 1 &&
                body.getStreetName().length() > 2 &&
                body.getHouseNumber() > 0 &&
                body.getZipCode().length() > 3 &&
                body.getCity().length() > 3 &&
                body.getCountry().length() > 3 &&
                body.getTransactionAmountLimit() >= 0 &&
                body.getDailyLimit() >= 0 &&
                body.getUsername().length() > 4 &&
                body.getPassword().length() > 5
        )
        ) {
            return new ResponseEntity(new ErrorMessageDTO("Bad request. Invalid request body."), HttpStatus.BAD_REQUEST);
        }

        ModelMapper modelMapper = new ModelMapper();
        User updatedUser = modelMapper.map(body, User.class);

        List<Role> givenRoles = new LinkedList<Role>();

        for (String role : body.getRoles()) {
            switch(role) {
                case "Customer":
                    givenRoles.add(Role.ROLE_CUSTOMER);
                    break;
                case "Employee":
                    givenRoles.add(Role.ROLE_EMPLOYEE);
                    break;
                default:
                    return new ResponseEntity(new ErrorMessageDTO("Bad request. Invalid request body."), HttpStatus.BAD_REQUEST);
            }
        }

        updatedUser.setRoles(givenRoles);
        updatedUser.setuserId(employeeId);

        updatedUser = userService.save(updatedUser);

        UserEmployeeDTO response = modelMapper.map(updatedUser, UserEmployeeDTO.class);
        return new ResponseEntity<UserEmployeeDTO>(response,  HttpStatus.OK);
    }

}
