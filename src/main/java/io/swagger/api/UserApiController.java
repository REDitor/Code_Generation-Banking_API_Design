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

abstract public class UserApiController {

    @Autowired
    private UserService userService;

    private final ModelMapper modelMapper =  new ModelMapper();


    public ResponseEntity checkUserBody(User userBody) {

     if (!(userBody.getFirstName().length() > 1 &&
             userBody.getLastName().length() > 1 &&
             userBody.getStreetName().length() > 2 &&
             userBody.getHouseNumber() > 0 &&
             userBody.getZipCode().length() > 3 &&
             userBody.getCity().length() > 3 &&
             userBody.getCountry().length() > 3 &&
             userBody.getTransactionAmountLimit() >= 0 &&
             userBody.getDailyLimit() >= 0 &&
             userBody.getUsername().length() > 4 &&
             userBody.getPassword().length() > 5)
             ) {
         return new ResponseEntity(new ErrorMessageDTO("Bad request. Invalid request body."), HttpStatus.BAD_REQUEST);
     }

     return null;
    }

    public ResponseEntity checkUserName(String username) {
        if (userService.getUserByUsername(username) != null) {
            return new ResponseEntity(new ErrorMessageDTO("Username already exists."), HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    public ResponseEntity checkUserIDParameter(String userId) {
        if (userId.length() < 8) {
            return new ResponseEntity(new ErrorMessageDTO("Bad request. Invalid request parameters."), HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    public ResponseEntity checkPagination(int offset, int limit) {
        if (offset < 0 || limit < 1) {
            return new ResponseEntity(new ErrorMessageDTO("Bad request. Invalid request parameters."), HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    public ResponseEntity   updateUser(UUID employeeId, UpdateUserDTO body) {
        User updatedUser = modelMapper.map(body, User.class);

        // Make sure all the fields got filled properly
        checkUserBody(updatedUser);

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

    public ResponseEntity responseEntityUserOk(User user) {
        UserDTO response = modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<UserDTO>(response,  HttpStatus.OK);
    }

    public ResponseEntity responseEntityUserListOk(List<User> user) {
        List<UserDTO> entityToDto = modelMapper.map(user, new TypeToken<List<UserDTO>>(){}.getType());
        return new ResponseEntity<List<UserDTO>>(entityToDto,  HttpStatus.OK);
    }


}
