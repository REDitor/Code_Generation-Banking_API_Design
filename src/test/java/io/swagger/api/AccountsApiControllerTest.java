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
    void getAccount_withMasterAccount_returnsForbidden() throws Exception {
        String iban = "NL01INHO0000000001";

        when(userService.isEmployee(request)).thenReturn(false);

        MockHttpServletResponse response = mvc.perform(get("/accounts/{iban}", iban))
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(response.getContentAsString()).isNotNull();

        String errorMessage = response.getContentAsString();

        assertThat(errorMessage).isEqualTo("{\"reason\":\"Permission Denied: Cannot access master account\"}");
    }

    @Test
    void getAccount_withUnauthorizedAccess_returnsForbidden() throws Exception {
        String iban = "NL01INHO0000000003";

        when(userService.isEmployee(request)).thenReturn(false);

        MockHttpServletResponse response = mvc.perform(get("/accounts/{iban}", iban))
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(response.getContentAsString()).isNotNull();

        String errorMessage = response.getContentAsString();

        assertThat(errorMessage).isEqualTo("{\"reason\":\"Permission Denied: You do not own this account or do not have employee permissions\"}");
    }

    @Test
    void updateAccount_withInvalidRequestBody_returnsBadRequest() throws Exception {
        String iban = "dummyIban";

        UpdateAccountDTO requestBody = new UpdateAccountDTO();
        requestBody.setMinimumBalance(-100);
        requestBody.setStatus("o");
        requestBody.setType("s");

        MockHttpServletResponse response = mvc.perform(put("/accounts/{iban}", iban)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestBody)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isNotNull();

        assertThat(response.getContentAsString()).isEqualTo("{\"reason\":\"Bad request. Invalid request body.\"}");
    }

    @Test
    void getTotalBalanceByUserID_withValidUserId_returnsTotalBalance() throws Exception {
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

        MockHttpServletResponse response = mvc.perform(get("/accounts/total/{userId}", userId))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        AccountsAmountDTO accountsAmountDTO = new ObjectMapper().readValue(response.getContentAsString(), AccountsAmountDTO.class);
        assertThat(accountsAmountDTO.gettotalBalance()).isEqualTo(totalAmount);
    }

    @Test
    void getTotalBalanceByUserID_withUnauthorizedAccess_returnsUnauthorized() throws Exception {
        UUID userId = UUID.randomUUID();

        User loggedUser = new User();
        loggedUser.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));
        loggedUser.setuserId(UUID.randomUUID());

        when(userService.getLoggedUser(request)).thenReturn(loggedUser);

        MockHttpServletResponse response = mvc.perform(get("/accounts/total/{userId}", userId))
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse();

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