package io.swagger.api;

import io.swagger.model.ErrorMessageDTO;
import io.swagger.model.UpdateUserDTO;
import io.swagger.model.UserDTO;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import io.swagger.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class UserApiController {

    @Autowired
    private UserService userService;

    private final ModelMapper modelMapper =  new ModelMapper();

    // Code from stackoverflow: https://stackoverflow.com/questions/8204680/java-regex-email
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    /* ---------------- */
    /* Checks for code */
    /* ---------------- */
    public ResponseEntity<ErrorMessageDTO> checkUserBody(User userBody) {

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
             userBody.getUsername().length() > 4 &&
             userBody.getPassword().length() >= 7)
             ) {
         return new ResponseEntity<>(new ErrorMessageDTO("Bad request. Invalid request body."), HttpStatus.BAD_REQUEST);
     }

     if(!isStrongPassword(userBody.getPassword())) {
         return new ResponseEntity<>(new ErrorMessageDTO("Password is to weak."), HttpStatus.BAD_REQUEST);
     }

     return null;
    }

    public ResponseEntity<ErrorMessageDTO> checkUserName(String username) {
        if (userService.getUserByUsername(username) != null) {
            return new ResponseEntity<>(new ErrorMessageDTO("Username already exists."), HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    public ResponseEntity<ErrorMessageDTO> checkUserIDParameter(String userId) {
        if (userId.length() < 8) {
            return new ResponseEntity<>(new ErrorMessageDTO("Bad request. Invalid request parameters."), HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    public ResponseEntity<ErrorMessageDTO> checkPagination(int offset, int limit) {
        if (offset < 0 || limit < 1) {
            return new ResponseEntity<>(new ErrorMessageDTO("Bad request. Invalid request parameters."), HttpStatus.BAD_REQUEST);
        }

        return null;
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

    /* ----------------- */

    public ResponseEntity   updateUser(UUID employeeId, UpdateUserDTO body) {
        User updatedUser = modelMapper.map(body, User.class);

        // Make sure all the fields got filled properly
        checkUserBody(updatedUser);

        // Check which roles have been selected, and assign enum to class
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

        UserDTO response = modelMapper.map(updatedUser, UserDTO.class);
        return new ResponseEntity<UserDTO>(response,  HttpStatus.OK);
    }

    public ResponseEntity<UserDTO> responseEntityUserOk(User user) {
        UserDTO response = modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<UserDTO>(response,  HttpStatus.OK);
    }

    public ResponseEntity<List<UserDTO>> responseEntityUserListOk(List<User> user) {
        List<UserDTO> entityToDto = modelMapper.map(user, new TypeToken<List<UserDTO>>(){}.getType());
        return new ResponseEntity<List<UserDTO>>(entityToDto,  HttpStatus.OK);
    }



}
