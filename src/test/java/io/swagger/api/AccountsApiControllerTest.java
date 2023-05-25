package io.swagger.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.model.*;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.AccountType;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import io.swagger.service.AccountService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import javax.servlet.http.HttpServletRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith({SpringExtension.class})
@WebMvcTest(AccountsApiController.class)
@Import(AccountsApiController.class)
@ActiveProfiles("test")
class AccountsApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserService userService;

    @MockBean
    private TransactionsApiController transactionController;

    @MockBean
    private TransactionService transactionService;

    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new AccountsApiController(mapper, null, accountService, userService))
                .build();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createAccount() throws Exception {
        // Setup 
        NewAccountDTO newAccountDTO = new NewAccountDTO();
        newAccountDTO.setUserID(UUID.randomUUID());
        newAccountDTO.setStatus("Open");
        newAccountDTO.setType("Savings");
        newAccountDTO.setMinimumBalance(0);

        Account newAccount = new Account();
        newAccount.setIBAN("dummyIban");

        User user = new User();
        user.setuserId(newAccountDTO.getUserID());

        when(accountService.generateIban()).thenReturn("dummyIban");
        when(accountService.add(any(Account.class))).thenReturn(newAccount);
        when(userService.getOneCustomer(newAccountDTO.getUserID())).thenReturn(user);

        // Mock 
        MockHttpServletResponse response = mvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newAccountDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        // check 
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isNotEmpty();

        AccountDTO accountDTO = new ObjectMapper().readValue(response.getContentAsString(), AccountDTO.class);
        assertThat(accountDTO.getIBAN()).isEqualTo("dummyIban");
    }

    @Test
    void getAccount_withValidIban_returnsAccount() throws Exception {
        // Setup 
        String iban = "dummyIban";

        Account account = new Account();
        account.setIBAN(iban);

        User loggedUser = new User();

        when(userService.getLoggedUser(request)).thenReturn(loggedUser);
        when(accountService.getAccountByIBAN(iban)).thenReturn(account);
        when(userService.isEmployee(request)).thenReturn(true);

        // Mock 
        MockHttpServletResponse response = mvc.perform(get("/accounts/{iban}", iban))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        // check 
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        AccountDTO accountDTO = new ObjectMapper().readValue(response.getContentAsString(), AccountDTO.class);
        assertThat(accountDTO.getIBAN()).isEqualTo(iban);
    }

    @Test
    void getAccount_withMasterAccount_returnsForbidden() throws Exception {
        // Setup
        String iban = "NL01INHO0000000001";

        when(userService.isEmployee(request)).thenReturn(false);

        // Mock
        MockHttpServletResponse response = mvc.perform(get("/accounts/{iban}", iban))
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse();

        // Check
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(response.getContentAsString()).isNotNull();

        String errorMessage = response.getContentAsString();

        assertThat(errorMessage).isEqualTo("{\"reason\":\"Permission Denied: Cannot access master account\"}");
    }

    @Test
    void getAccount_withUnauthorizedAccess_returnsForbidden() throws Exception {
        // Setup 
        String iban = "NL01INHO0000000003";

        when(userService.isEmployee(request)).thenReturn(false);

        // Mock 
        MockHttpServletResponse response = mvc.perform(get("/accounts/{iban}", iban))
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse();

        // check 
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(response.getContentAsString()).isNotNull();

        String errorMessage = response.getContentAsString();

        assertThat(errorMessage).isEqualTo("{\"reason\":\"Permission Denied: You do not own this account or do not have employee permissions\"}");
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void getAccountByName_withValidName_returnsAccounts() throws Exception {
        // Setup 
        String name = "John";

        List<Account> receivedAccounts = Arrays.asList(
                new Account("NL01INHO0000000001", new User(), AccountType.ACCOUNT_TYPE_CURRENT, 100000000, "open", 0),
                new Account("NL01INHO0000000002", new User(), AccountType.ACCOUNT_TYPE_SAVINGS, 50000000, "open", 0)
        );

        when(accountService.getAccountByName(name)).thenReturn(receivedAccounts);

        // Mock 
        MockHttpServletResponse response = mvc.perform(get("/accounts/ibans/{name}", name))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        // check 
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        List<AccountIbanDTO> accountIbanDTOs = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<AccountIbanDTO>>() {});
        assertThat(accountIbanDTOs.size()).isEqualTo(receivedAccounts.size());

        // Verify the mapping of accounts to AccountIbanDTOs
        for (int i = 0; i < receivedAccounts.size(); i++) {
            Account account = receivedAccounts.get(i);
            AccountIbanDTO accountIbanDTO = accountIbanDTOs.get(i);

            assertThat(accountIbanDTO.getIBAN()).isEqualTo(account.getIBAN());
            assertThat(accountIbanDTO.getFirstName()).isEqualTo(account.getUser().getFirstName());
            assertThat(accountIbanDTO.getLastName()).isEqualTo(account.getUser().getLastName());
        }
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void getAccountByName_withNoAccountsFound_returnsNotFound() throws Exception {
        // Setup 
        String name = "John";

        when(accountService.getAccountByName(name)).thenReturn(new ArrayList<>());

        // Mock 
        MockHttpServletResponse response = mvc.perform(get("/accounts/ibans/{name}", name))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        // check 
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isNotNull();

        assertThat(response.getContentAsString()).isEqualTo("{\"reason\":\"No accounts found belonging to that person\"}");
    }

    @Test
    void updateAccount_withValidRequestBody_returnsUpdatedAccount() throws Exception {
        // Setup 
        String iban = "dummyIban";

        UpdateAccountDTO requestBody = new UpdateAccountDTO();
        requestBody.setMinimumBalance(100);
        requestBody.setStatus("open");
        requestBody.setType("savings");

        Account oldAccountDetails = new Account();
        oldAccountDetails.setUser(new User());

        Account updatedAccount = new Account();
        updatedAccount.setIBAN(iban);
        updatedAccount.setUser(new User());

        when(accountService.getAccountByIBAN(iban)).thenReturn(oldAccountDetails);
        when(accountService.add(any(Account.class))).thenReturn(updatedAccount);

        // Mock 
        MockHttpServletResponse response = mvc.perform(put("/accounts/{iban}", iban)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestBody)))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        // check 
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        AccountDTO accountDTO = new ObjectMapper().readValue(response.getContentAsString(), AccountDTO.class);;
        assertThat(accountDTO.getIBAN()).isEqualTo(iban);

    }

    @Test
    void updateAccount_withInvalidRequestBody_returnsBadRequest() throws Exception {
        // Setup 
        String iban = "dummyIban";

        UpdateAccountDTO requestBody = new UpdateAccountDTO();
        requestBody.setMinimumBalance(-100);
        requestBody.setStatus("o");
        requestBody.setType("s");

        // Mock 
        MockHttpServletResponse response = mvc.perform(put("/accounts/{iban}", iban)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestBody)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        // check 
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isNotNull();

        assertThat(response.getContentAsString()).isEqualTo("{\"reason\":\"Bad request. Invalid request body.\"}");
    }

    @Test
    void getTotalBalanceByUserID_withValidUserId_returnsTotalBalance() throws Exception {
        // Setup 
        UUID userId = UUID.randomUUID();
        Double totalAmount = 1000.0;

        User user = new User();
        user.setuserId(userId);

        User loggedUser = new User();
        loggedUser.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));
        loggedUser.setuserId(UUID.randomUUID());

        when(userService.getUserById(userId)).thenReturn(user);
        when(accountService.totalAmountFromAccounts(user)).thenReturn(totalAmount);
        when(userService.getLoggedUser(request)).thenReturn(loggedUser);

        // Mock 
        MockHttpServletResponse response = mvc.perform(get("/accounts/total/{userId}", userId))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        // check 
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        AccountsAmountDTO accountsAmountDTO = new ObjectMapper().readValue(response.getContentAsString(), AccountsAmountDTO.class);
        assertThat(accountsAmountDTO.gettotalBalance()).isEqualTo(totalAmount);
    }

    @Test
    void getTotalBalanceByUserID_withUnauthorizedAccess_returnsUnauthorized() throws Exception {
        // Setup 
        UUID userId = UUID.randomUUID();

        User loggedUser = new User();
        loggedUser.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));
        loggedUser.setuserId(UUID.randomUUID());

        when(userService.getLoggedUser(request)).thenReturn(loggedUser);

        // Mock 
        MockHttpServletResponse response = mvc.perform(get("/accounts/total/{userId}", userId))
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse();

        // check 
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.getContentAsString()).isNotNull();

        String errorMessage = response.getContentAsString();
        assertThat(errorMessage).isEqualTo("{\"reason\":\"Not authorized to access total amount of other users.\"}");
    }


    // Helper method to convert an object to JSON string
    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}