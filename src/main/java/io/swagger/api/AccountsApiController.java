package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.model.AccountDTO;
import io.swagger.model.AccountIbanDTO;
import io.swagger.model.ErrorMessageDTO;
import io.swagger.model.NewAccountDTO;
import io.swagger.model.UpdateAccountDTO;
import io.swagger.model.*;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T12:05:25.016Z[GMT]")
@RestController
@Api(tags = "Accounts")
@RequestMapping("accounts")
public class AccountsApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private AccountService accountService;
    private UserService userService;
    private final ModelMapper modelMapper;

    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request, AccountService accountService, UserService userService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.modelMapper = new ModelMapper();
        this.accountService = accountService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping
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

    @PreAuthorize("hasRole('EMPLOYEE') || hasRole('CUSTOMER')")
    @GetMapping("{iban}")
    public ResponseEntity<AccountDTO> getAccount(@Size(min = 18, max = 18) @Parameter(in = ParameterIn.PATH, description = "The Iban of the account", required = true, schema = @Schema()) @PathVariable("iban") String iban) {

        User loggedUser = userService.getLoggedUser(request);

        Account receivedAccount = accountService.getAccountByIBAN(iban);

        if (iban.equals("NL01INHO0000000001"))
            return new ResponseEntity(new ErrorMessageDTO("Permission Denied: Cannot access master account"), HttpStatus.FORBIDDEN);

        if (!userService.isEmployee(request) && !userService.accountOwnerIsLoggedUser(receivedAccount, request))
            return new ResponseEntity(new ErrorMessageDTO("Permission Denied: You do not own this account or do not have employee permissions"), HttpStatus.FORBIDDEN);

        AccountDTO response = modelMapper.map(receivedAccount, AccountDTO.class);
        return new ResponseEntity<AccountDTO>(response, HttpStatus.OK);
    }
    @GetMapping("/ibans/{name}")
    public ResponseEntity<List<AccountIbanDTO>> getAccountByName(String name) {

        List<Account> receivedAccounts = accountService.getAccountByName(name);

        System.out.println("Received: " + receivedAccounts);

        if (receivedAccounts.size() <= 0)
            return new ResponseEntity(new ErrorMessageDTO("No accounts found belonging to that person"), HttpStatus.NOT_FOUND);

        List<AccountIbanDTO> ibanDTOs = new ArrayList<>();

        receivedAccounts.forEach(account -> {
            AccountIbanDTO ibanDTO = new AccountIbanDTO();
            ibanDTO.setIBAN(account.getIBAN());
            ibanDTO.setFirstName(account.getUser().getFirstName());
            ibanDTO.setLastName(account.getUser().getLastName());

            ibanDTOs.add(ibanDTO);
        });

        return new ResponseEntity<List<AccountIbanDTO>>(ibanDTOs, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("{iban}")
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

        return new ResponseEntity<AccountDTO>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE') || hasRole('CUSTOMER')")
    @GetMapping("/total/{userId}")
    public ResponseEntity<AccountsAmountDTO> getTotalBalanceByUserID(@Parameter(in = ParameterIn.PATH, description = "UserId of user", required=true, schema=@Schema()) @PathVariable("userId") UUID userId) {
        User user = userService.getUserById(userId);
        Double totalAmount = accountService.totalAmountFromAccounts(user);

        User loggedUser = userService.getLoggedUser(request);

        if(!loggedUser.getRoles().contains(Role.ROLE_EMPLOYEE) && !loggedUser.getuserId().equals(userId)){
            return new ResponseEntity(new ErrorMessageDTO("Not authorized to access total amount of other users."), HttpStatus.UNAUTHORIZED);
        }

        AccountsAmountDTO response = new AccountsAmountDTO();
        response.settotalBalance(totalAmount);

        return new ResponseEntity<AccountsAmountDTO>(response,HttpStatus.OK);
    }
}
