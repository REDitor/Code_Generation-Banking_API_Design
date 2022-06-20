package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.*;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.User;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.*;

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

    public ResponseEntity<AccountDTO> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema = @Schema()) @Valid @RequestBody NewAccountDTO body) {
        Account newAccount = modelMapper.map(body, Account.class);
        User user = userService.getOneCustomer(body.getUserID());
        String newIban = accountService.generateIban();

        newAccount.setIBAN(newIban);

        Account result = accountService.add(newAccount);

        user.setAccount(new ArrayList<>(Arrays.asList(result)));
        userService.put(user);

        AccountDTO response = modelMapper.map(newAccount, AccountDTO.class);
        return new ResponseEntity<AccountDTO>(response, HttpStatus.CREATED);

    }

    public ResponseEntity<AccountDTO> getAccount(@Size(min = 18, max = 18) @Parameter(in = ParameterIn.PATH, description = "The Iban of the account", required = true, schema = @Schema()) @PathVariable("iban") String iban) {
        Account receivedAccount = accountService.getAccountByIBAN(iban);

        AccountDTO response = modelMapper.map(receivedAccount, AccountDTO.class);
        return new ResponseEntity<AccountDTO>(response, HttpStatus.OK);
    }

    public ResponseEntity<AccountDTO> updateAccount(@Size(min = 18, max = 18) @Parameter(in = ParameterIn.PATH, description = "The Iban of the account", required = true, schema = @Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.DEFAULT, description = "Fields that need to be updated", schema = @Schema()) @Valid @RequestBody UpdateAccountDTO body) {
        if(!(body.getMinimumBalance() >= 0 &&
                body.getStatus().length() > 1 &&
                body.getType().length() > 1)
        ){
            return new ResponseEntity(new ErrorMessageDTO("Bad request. Invalid request body."), HttpStatus.BAD_REQUEST);
        }
        Account oldAccountDetails = accountService.getAccountByIBAN(iban);
        Account updatedAccount = modelMapper.map(body, Account.class);
        updatedAccount.setIBAN(iban);
        updatedAccount.setUser(oldAccountDetails.getUser());

        accountService.add(updatedAccount);
        AccountDTO response = modelMapper.map(updatedAccount, AccountDTO.class);

        return new ResponseEntity<AccountDTO>(response,HttpStatus.OK);
    }

}
