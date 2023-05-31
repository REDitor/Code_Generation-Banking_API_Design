package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.model.ErrorMessageDTO;
import io.swagger.model.NewUserDTO;
import io.swagger.model.UserDTO;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import io.swagger.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class UserApiController {

    private UserService userService;
    PasswordEncoder passwordEncoder;

    public UserApiController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    private final ModelMapper modelMapper =  new ModelMapper();

    // Code from stackoverflow: https://stackoverflow.com/questions/8204680/java-regex-email
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    /* ---------------- */
    /* Checks for code */
    /* ---------------- */
    public ResponseEntity<ErrorMessageDTO> checkUserBody(User userBody, Boolean updateCheck) throws Exception {

     if (!(userBody.getFirstName().length() > 1 &&
             userBody.getLastName().length() > 1 &&
             userBody.getStreetName().length() > 2 &&
             userBody.getHouseNumber() > 0 &&
             userBody.getZipCode().length() > 3 &&
             userBody.getCity().length() > 3 &&
             userBody.getCountry().length() > 3 &&
             userBody.getTransactionAmountLimit() >= 0 &&
             userBody.getDailyLimit() >= 0 &&
             validateEmail(userBody.getEmail()) &&
             userBody.getUsername().length() > 4)
             ) {
         throw new Exception("Bad request. Invalid request body.");
     }

     if(!updateCheck && userBody.getPassword().length() < 7 ){
         throw new Exception("Bad request. Invalid request body.");
     }

     if(!updateCheck && !isStrongPassword(userBody.getPassword())) {
         return new ResponseEntity<>(new ErrorMessageDTO("Password is to weak."), HttpStatus.BAD_REQUEST);
     }


     return null;
    }



    public ResponseEntity<ErrorMessageDTO> checkUserName(String username) throws Exception {
        if (userService.getUserByUsername(username) != null) {
            throw new Exception("Username already exists");
        }

        return null;
    }

    public void checkUserIDParameter(String userId) throws Exception {
        if (userId.length() < 8) {
            throw new Exception("Bad request. Invalid request parameters.");
        }
    }

    public void checkPagination(int offset, int limit) throws Exception {
        if (offset < 0 || limit < 1) {
            throw new Exception("Bad request. Invalid request parameters.");
        }
    }

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    // Regex from: https://stackoverflow.com/questions/5142103/regex-to-validate-password-strength/5142164#5142164
    // Modified it to be easier
    private boolean isStrongPassword(String password){
        return password.matches("^(?=.*[A-Z])(?=.*[!@#$&*.,;`_=+])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{6,}$");
    }

    public ResponseEntity<UserDTO> responseEntityUserOk(User user) {
        UserDTO response = modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<UserDTO>(response,  HttpStatus.OK);
    }

    public ResponseEntity<UserDTO> responseEntityUserCreated(User user) {
        UserDTO response = modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<UserDTO>(response,  HttpStatus.CREATED);
    }

    public ResponseEntity<List<UserDTO>> responseEntityUserListOk(List<User> user) {
        List<UserDTO> entityToDto = modelMapper.map(user, new TypeToken<List<UserDTO>>(){}.getType());
        return new ResponseEntity<List<UserDTO>>(entityToDto,  HttpStatus.OK);
    }

    /* ----------------- */

    public ResponseEntity createUser(NewUserDTO body, Role role){
        try {
            User newUser = modelMapper.map(body, User.class);

            checkUserBody(newUser, false);
            checkUserName(newUser.getUsername());

            // Set proper role for user and add user to database
            newUser.setRoles(Collections.singletonList(role));
            newUser = userService.add(newUser);

            return responseEntityUserCreated(newUser);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorMessageDTO(e.getMessage().toString()), HttpStatus.BAD_REQUEST);
        }
    }

    public List<Role> convertStringRoleToObjectRoleList(List<String> roles) throws Exception {
        List<Role> givenRoles = new LinkedList<Role>();
        for (String role : roles) {
            switch(role) {
                case "Customer":
                    givenRoles.add(Role.ROLE_CUSTOMER);
                    break;
                case "Employee":
                    givenRoles.add(Role.ROLE_EMPLOYEE);
                    break;
                default:
                    throw new Exception("Bad request. Invalid roles.");
            }
        }

        return givenRoles;
    }

    public User updateChecks(User updatedUser, User userToUpdate) throws Exception {
        // Make sure all the fields got filled properly
        checkUserBody(updatedUser, true);

        // Get requested user information
        if (userToUpdate == null) {
            throw new Exception("User not found.");
        }

        // Check if the username is trying to be changed and if it already exists
        if(!userToUpdate.getUsername().equals(updatedUser.getUsername())){
            checkUserName(updatedUser.getUsername());
        }

        // If password has been updated, then encode it
        if (!updatedUser.getPassword().equals("")){
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }else{
            updatedUser.setPassword(userToUpdate.getPassword());
        }

        return updatedUser;
    }
}
