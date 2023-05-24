package io.swagger.api;

import io.swagger.configuration.CustomApplicationRunner;
import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.AccountDTO;
import io.swagger.model.NewAccountDTO;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.AccountType;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import io.swagger.service.AccountService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.threeten.bp.LocalDate;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import javax.servlet.http.HttpServletRequest;

@ExtendWith({SpringExtension.class})
@WebMvcTest(AccountsApiController.class)
@Import(AccountsApiController.class)
@ActiveProfiles("test")
class AccountsApiControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthenticationManager authenticationManagerBean;

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserService userService;
    @MockBean
    private TransactionService transactionService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TransactionRepository transactionRepository;

    private HttpServletRequest request;

    private Account account;
    private User user;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new AccountsApiController(mapper, null, accountService, userService))
                .build();

        user = new User("dummy3", "dummy123", LocalDate.parse("1997-12-07"), "Someotherstreet", 123, "4321AB", "Haarlem",
                "The Netherlands", 500, 2000, Arrays.asList(Role.ROLE_CUSTOMER), "dummyCustomer1234@gmail.com", "dummyCustomer1234", "secret123");
        user.setuserId(UUID.randomUUID());

        account = new Account(accountService.generateIban(), user, AccountType.ACCOUNT_TYPE_CURRENT, 100000000, "open", 0);
    }

    @Test
    void createAccount() throws Exception {
        // Arrange
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

        // Act
        MockHttpServletResponse response = mvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newAccountDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isNotEmpty();

        AccountDTO accountDTO = new ObjectMapper().readValue(response.getContentAsString(), AccountDTO.class);
        assertThat(accountDTO.getIBAN()).isEqualTo("dummyIban");


    }

    // Helper method to convert an object to JSON string
    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAccount_withValidIban_returnsAccount() throws Exception {
        // Arrange
        String iban = "dummyIban";

        Account account = new Account();
        account.setIBAN(iban);

        User loggedUser = new User();

        when(userService.getLoggedUser(request)).thenReturn(loggedUser);
        when(accountService.getAccountByIBAN(iban)).thenReturn(account);
        when(userService.isEmployee(request)).thenReturn(true);

        // Act
        MockHttpServletResponse response = mvc.perform(get("/accounts/{iban}", iban))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        AccountDTO accountDTO = new ObjectMapper().readValue(response.getContentAsString(), AccountDTO.class);
        assertThat(accountDTO.getIBAN()).isEqualTo(iban);
    }

    @Test
    void getAccount() {
    }

    @Test
    void getAccountByName() {
    }

    @Test
    void updateAccount() {
    }

    @Test
    void getTotalBalanceByUserID() {
    }
}