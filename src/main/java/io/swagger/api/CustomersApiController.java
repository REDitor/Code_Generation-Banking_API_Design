package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.annotations.Api;
import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.ErrorMessageDTO;
import io.swagger.model.NewUserDTO;
import io.swagger.model.UpdateUserDTO;
import io.swagger.model.UserDTO;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T12:05:25.016Z[GMT]")
@RestController
@Api(tags = "Customers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("customers")
public class CustomersApiController extends UserApiController implements CustomersApi {

    private static final Logger log = LoggerFactory.getLogger(CustomersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final ModelMapper modelMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;

    PasswordEncoder passwordEncoder;

    public CustomersApiController(ObjectMapper objectMapper, HttpServletRequest request, UserService userService, PasswordEncoder passwordEncoder) {
        super(userService, passwordEncoder);
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());

        this.request = request;
        this.modelMapper = new ModelMapper();
        this.userService = userService; // Initialize the userService field
    }

    @PostMapping
    public ResponseEntity<UserDTO> createCustomer(@Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema = @Schema()) @Valid @RequestBody NewUserDTO body) {
        return createUser(body, Role.ROLE_CUSTOMER);
    }

    @PreAuthorize("hasRole('EMPLOYEE') || hasRole('CUSTOMER')")
    @PutMapping("{userID}")
    public ResponseEntity<UserDTO> updateCustomer(@Parameter(in = ParameterIn.PATH, description = "The userID of the customer", required = true, schema = @Schema()) @PathVariable("userID") UUID userID, @Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema = @Schema()) @Valid @RequestBody UpdateUserDTO body) {
        try {
            // Map body and make sure all the fields got filled properly
            User updatedUser = modelMapper.map(body, User.class);
            User userToUpdate = userService.getOneCustomer(userID);

            updatedUser = updateChecks(updatedUser, userToUpdate);

            User loggedUser = userService.getLoggedUser(request);

            // If logged user is a customer, ensure its only possible to change his information
            if(!loggedUser.getRoles().contains(Role.ROLE_EMPLOYEE) && !loggedUser.getuserId().equals(userID)){
                return new ResponseEntity(new ErrorMessageDTO("Not authorized to changed other user data."), HttpStatus.FORBIDDEN);
            }

            // Only update role of customer, if an employee is doing it
            if (loggedUser.getRoles().contains(Role.ROLE_EMPLOYEE)) {
                updatedUser.setRoles(
                        convertStringRoleToObjectRoleList(body.getRoles())
                );
            }else {
                updatedUser.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));
            }

            updatedUser.setuserId(userID);

            updatedUser = userService.save(updatedUser);

            return responseEntityUserOk(updatedUser);
        }catch (Exception e) {
            return new ResponseEntity(new ErrorMessageDTO(e.getMessage().toString()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE') || hasRole('CUSTOMER')")
    @GetMapping("{userID}")
    public ResponseEntity<UserDTO> getCustomer(@Parameter(in = ParameterIn.PATH, description = "The userID of the customer", required = true, schema = @Schema()) @PathVariable("userID") UUID userID) {
        try {
            //Check if provided userId is valid
            checkUserIDParameter(userID.toString());
            User userInformation = userService.getLoggedUser(request);

            // Check if user is a Customer, if he is, make sure he is only able to access his own information
            if (!userInformation.getRoles().contains(Role.ROLE_EMPLOYEE) && !userInformation.getuserId().equals(userID)) {
                return new ResponseEntity(new ErrorMessageDTO("You cannot access information that does not belong to you!"), HttpStatus.UNAUTHORIZED);
            }

            // Get requested user information
            User receivedUser = userService.getOneCustomer(userID);
            if (receivedUser == null) {
                return new ResponseEntity(new ErrorMessageDTO("Customer not found."), HttpStatus.NOT_FOUND);
            }

            return responseEntityUserOk(receivedUser);
        } catch (Exception e){
            return new ResponseEntity(new ErrorMessageDTO(e.getMessage().toString()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getCustomers(@Parameter(in = ParameterIn.QUERY, description = "search for this substring", schema = @Schema()) @Valid @RequestParam(value = "firstName", required = false) String firstName, @Parameter(in = ParameterIn.QUERY, description = "search for lastname", schema = @Schema()) @Valid @RequestParam(value = "lastName", required = false) String lastName, @Min(0) @Parameter(in = ParameterIn.QUERY, description = "number of records to skip for pagination", schema = @Schema(allowableValues = {})) @Valid @RequestParam(value = "skip", required = false) Integer skip, @Min(0) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "maximum number of records to return", schema = @Schema(allowableValues = {}, maximum = "50")) @Valid @RequestParam(value = "limit", required = false) Integer limit, @Parameter(in = ParameterIn.QUERY, description = "Get customers that have no accounts", schema = @Schema()) @Valid @RequestParam(value = "noAccounts", required = true) Boolean noAccounts) {
        try {
            // Check if pagination was set
            checkPagination(skip, limit);

            List<User> receivedUsers;

            if((firstName != null || lastName != null) && noAccounts == true) {
                receivedUsers = userService.getAllNoAccountsByName(PageRequest.of(skip, limit), firstName, lastName);
            } else if (firstName != null || lastName != null) {
                receivedUsers = userService.getAllByName(PageRequest.of(skip, limit), firstName, lastName);
            } else if(noAccounts == true){
                receivedUsers = userService.getAllNoAccounts(PageRequest.of(skip, limit));
            } else {
                receivedUsers = userService.getAll(PageRequest.of(skip, limit));
            }

            return responseEntityUserListOk(receivedUsers);
        } catch (Exception e){
            return new ResponseEntity(new ErrorMessageDTO(e.getMessage().toString()), HttpStatus.BAD_REQUEST);
        }
    }
}
