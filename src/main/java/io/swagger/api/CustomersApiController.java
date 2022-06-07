package io.swagger.api;

import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.ErrorMessageDTO;

import java.util.Collections;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T12:05:25.016Z[GMT]")
@RestController
public class CustomersApiController extends UserApiController implements CustomersApi {

    private static final Logger log = LoggerFactory.getLogger(CustomersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final ModelMapper modelMapper;

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public CustomersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.modelMapper = new ModelMapper();
    }

    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserDTO> createCustomer(@Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema = @Schema()) @Valid @RequestBody NewUserDTO body) {
        User newUser = modelMapper.map(body, User.class);

        ResponseEntity validation;
        // Make sure all the fields got filled properly and heck if username is already in use
        validation = checkUserBody(newUser);
        validation = checkUserName(newUser.getUsername());

        if (validation != null)
            return validation;

        // Set proper role for user and add user to database
        newUser.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));
        newUser = userService.add(newUser);

        return responseEntityUserOk(newUser);
    }

    //@PreAuthorize("hasRole('EMPLOYEE') || hasRole('CUSTOMER')")
    public ResponseEntity<UserDTO> getCustomer(@Parameter(in = ParameterIn.PATH, description = "The userID of the customer", required = true, schema = @Schema()) @PathVariable("userID") UUID userID) {
        // CHeck if provided userId is valid
        ResponseEntity validation = checkUserIDParameter(userID.toString());

        if (validation != null)
            return validation;

        // Get JWT token and the information of the authenticated user
        String receivedToken = jwtTokenProvider.resolveToken(request);
        jwtTokenProvider.validateToken(receivedToken);
        String authenticatedUserUsername = jwtTokenProvider.getUsername(receivedToken);
        User userInformation = userService.getUserByUsername(authenticatedUserUsername);

        // Check if user is a Customer, if he is, make sure he is only able to access his own information
        if (userInformation.getRoles().contains(Role.ROLE_CUSTOMER) && userInformation.getuserId() != userID) {
            return new ResponseEntity(new ErrorMessageDTO("Unauthorized or authorization information is missing or invalid."), HttpStatus.UNAUTHORIZED);
        }

        // Get requested user information
        User receivedUser = userService.getOneCustomer(userID);
        if (receivedUser == null) {
            return new ResponseEntity(new ErrorMessageDTO("Customer not found."), HttpStatus.NOT_FOUND);
        }

        return responseEntityUserOk(receivedUser);
    }

    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<UserDTO>> getCustomers(@Parameter(in = ParameterIn.QUERY, description = "search for this substring", schema = @Schema()) @Valid @RequestParam(value = "firstName", required = false) String firstName, @Parameter(in = ParameterIn.QUERY, description = "search for lastname", schema = @Schema()) @Valid @RequestParam(value = "lastName", required = false) String lastName, @Min(0) @Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination", schema = @Schema(allowableValues = {})) @Valid @RequestParam(value = "skip", required = false) Integer skip, @Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return", schema = @Schema(allowableValues = {}, maximum = "50")) @Valid @RequestParam(value = "limit", required = false) Integer limit) {

        // Check if pagination was set
        ResponseEntity validation = checkPagination(skip, limit);
        if (validation != null)
            return validation;

        List<User> receivedUsers;

        if (firstName != null || lastName != null) {
             receivedUsers = userService.getAllByName(PageRequest.of(skip, limit), firstName, lastName);
        } else {
            receivedUsers = userService.getAll(PageRequest.of(skip, limit));
        }

        return responseEntityUserListOk(receivedUsers);
    }

    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserDTO> updateCustomer(@Parameter(in = ParameterIn.PATH, description = "The userID of the customer", required = true, schema = @Schema()) @PathVariable("userID") UUID userID, @Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema = @Schema()) @Valid @RequestBody UpdateUserDTO body) {
        return updateUser(userID, body);
    }

}
