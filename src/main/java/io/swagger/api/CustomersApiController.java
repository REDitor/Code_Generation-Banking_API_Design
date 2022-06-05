package io.swagger.api;

import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.ErrorMessageDTO;

import java.util.Collections;
import java.util.LinkedList;
import java.util.UUID;

import io.swagger.model.NewUserDTO;
import io.swagger.model.UpdateUserCustomerDTO;
import io.swagger.model.UserCustomerDTO;
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
    public ResponseEntity<UserCustomerDTO> createCustomer(@Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema = @Schema()) @Valid @RequestBody NewUserDTO body) {
        User newUser = modelMapper.map(body, User.class);

        // Make sure all the fields got filled properly and heck if username is already in use
        checkUserBody(newUser);
        checkUserName(newUser.getUsername());

        // Set proper role for user and add user to database
        newUser.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));
        newUser = userService.add(newUser);

        UserCustomerDTO response = modelMapper.map(newUser, UserCustomerDTO.class);
        return new ResponseEntity<UserCustomerDTO>(response, HttpStatus.CREATED);
    }

    //@PreAuthorize("hasRole('EMPLOYEE') || hasRole('CUSTOMER')")
    public ResponseEntity<UserCustomerDTO> getCustomer(@Parameter(in = ParameterIn.PATH, description = "The userID of the customer", required = true, schema = @Schema()) @PathVariable("userID") UUID userID) {
        // CHeck if provided userId is valid
        checkUserIDParameter(userID.toString());

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
        User receivedUser = userService.getOne(userID);

        if (receivedUser == null) {
            return new ResponseEntity(new ErrorMessageDTO("Customer not found."), HttpStatus.NOT_FOUND);
        }

        UserCustomerDTO response = modelMapper.map(receivedUser, UserCustomerDTO.class);
        return new ResponseEntity<UserCustomerDTO>(response, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<UserCustomerDTO>> getCustomers(@Parameter(in = ParameterIn.QUERY, description = "search for this substring", schema = @Schema()) @Valid @RequestParam(value = "name", required = false) String name, @Min(0) @Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination", schema = @Schema(allowableValues = {})) @Valid @RequestParam(value = "skip", required = false) Integer skip, @Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return", schema = @Schema(allowableValues = {}, maximum = "50")) @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        // TODO: search name in list of customers

        // Check if pagination was set
        checkPagination(skip, limit);

        Pageable page = PageRequest.of(skip, limit);
        List<User> receivedUsers = userService.getAll(page);

        List<UserCustomerDTO> entityToDto = modelMapper.map(receivedUsers, new TypeToken<List<UserCustomerDTO>>() {
        }.getType());
        return new ResponseEntity<List<UserCustomerDTO>>(entityToDto, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserCustomerDTO> updateCustomer(@Parameter(in = ParameterIn.PATH, description = "The userID of the customer", required = true, schema = @Schema()) @PathVariable("userID") UUID userID, @Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema = @Schema()) @Valid @RequestBody UpdateUserCustomerDTO body) {
        User updatedUser = modelMapper.map(body, User.class);

        // Make sure all the fields got filled properly
        checkUserBody(updatedUser);

        List<Role> givenRoles = new LinkedList<Role>();
        for (String role : body.getRoles()) {
            switch (role) {
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
        updatedUser.setuserId(userID);

        updatedUser = userService.save(updatedUser);

        UserCustomerDTO response = modelMapper.map(updatedUser, UserCustomerDTO.class);
        return new ResponseEntity<UserCustomerDTO>(response, HttpStatus.OK);
    }

}
