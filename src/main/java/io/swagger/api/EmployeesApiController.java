package io.swagger.api;

import io.swagger.model.ErrorMessageDTO;

import java.util.Collections;
import java.util.LinkedList;
import java.util.UUID;

import io.swagger.model.NewUserDTO;
import io.swagger.model.UpdateUserDTO;
import io.swagger.model.UserDTO;
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
public class EmployeesApiController extends UserApiController implements EmployeesApi {

    private static final Logger log = LoggerFactory.getLogger(EmployeesApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public EmployeesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.modelMapper = new ModelMapper();
    }

    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserDTO> createEmployee(@Parameter(in = ParameterIn.DEFAULT, description = "New Employee details", schema=@Schema()) @Valid @RequestBody NewUserDTO body) {
        User newUser = modelMapper.map(body, User.class);

        ResponseEntity validation;
        // Make sure all the fields got filled properly and check if username is already in use
        validation = checkUserBody(newUser);
        if (validation != null) return validation;

        validation = checkUserName(newUser.getUsername());
        if (validation != null) return validation;

        if (validation != null)
            return validation;

        newUser.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));
        newUser = userService.add(newUser);

        return responseEntityUserOk(newUser);
    }

    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserDTO> getEmployee(@Parameter(in = ParameterIn.PATH, description = "the employeeId of the desired employee", required=true, schema=@Schema()) @PathVariable("employeeId") UUID employeeId) {
        // CHeck if provided userId is valid
        ResponseEntity validation = checkUserIDParameter(employeeId.toString());

        if (validation != null)
            return validation;

        // Get requested user information
        User receivedUser = userService.getOneEmployee(employeeId);
        if (receivedUser == null) {
            return new ResponseEntity(new ErrorMessageDTO("Employee not found."), HttpStatus.NOT_FOUND);
        }

        return responseEntityUserOk(receivedUser);
    }

    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<UserDTO>> getEmployees(@Parameter(in = ParameterIn.QUERY, description = "search for this substring", schema = @Schema()) @Valid @RequestParam(value = "firstName", required = false) String firstName, @Parameter(in = ParameterIn.QUERY, description = "search for lastname", schema = @Schema()) @Valid @RequestParam(value = "lastName", required = false) String lastName, @Min(0)@Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination" ,schema=@Schema(allowableValues={  }
)) @Valid @RequestParam(value = "offset", required = false) Integer offset,@Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return" ,schema=@Schema(allowableValues={  }, maximum="50"
)) @Valid @RequestParam(value = "limit", required = false) Integer limit) {

        // Check if pagination was set
        ResponseEntity validation = checkPagination(offset, limit);
        if (validation != null)
            return validation;

        List<User> receivedUsers;
        if (firstName != null || lastName != null) {
            receivedUsers = userService.getAllEmployeesByName(PageRequest.of(offset, limit), firstName, lastName);
        } else {
            receivedUsers = userService.getAllEmployees(PageRequest.of(offset, limit));
        }


        return responseEntityUserListOk(receivedUsers);
    }

    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserDTO> updateEmployee(@Parameter(in = ParameterIn.PATH, description = "The employeeId of the employee to update", required=true, schema=@Schema()) @PathVariable("userId") UUID userId,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody UpdateUserDTO body) {
        return updateUser(userId, body);
    }

}
