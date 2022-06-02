package io.swagger.api;

import io.swagger.model.AccountDTO;
import io.swagger.model.NewAccountDTO;
import io.swagger.model.UpdateAccountDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.UserEmployeeDTO;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.User;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T12:05:25.016Z[GMT]")
@RestController
public class AccountsApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    private final ModelMapper modelMapper;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.modelMapper = new ModelMapper();
    }

    public ResponseEntity<AccountDTO> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema=@Schema()) @Valid @RequestBody NewAccountDTO body) {
        Account newAccount = modelMapper.map(body, Account.class);
        User user = userService.getOne(body.getFkuserID());

        newAccount.setFkUserID(user);

        String newIban = accountService.generateIban();

        newAccount.setIBAN(newIban);

        Account result = accountService.add(newAccount);

        AccountDTO response = modelMapper.map(newAccount, AccountDTO.class);
        return new ResponseEntity<AccountDTO>(response,  HttpStatus.OK);

    }

    public ResponseEntity<AccountDTO> getAccount(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "The Iban of the account", required=true, schema=@Schema()) @PathVariable("iban") String iban) {
        return null;
    }

    public ResponseEntity<AccountDTO> updateAccount(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "The Iban of the account", required=true, schema=@Schema()) @PathVariable("iban") String iban,@Parameter(in = ParameterIn.DEFAULT, description = "Fields that need to be updated", schema=@Schema()) @Valid @RequestBody UpdateAccountDTO body) {
        return null;
    }

}
