package io.swagger.api;

import io.swagger.configuration.CustomApplicationRunner;
import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.AccountDTO;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.threeten.bp.LocalDate;

import java.util.Arrays;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

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

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new AccountsApiController(mapper, null, accountService, userService))
                .build();
    }
    @Test
    void createAccount() throws Exception {
        User testUser = new User("dummy3", "dummy123", LocalDate.parse("1997-12-07"), "Someotherstreet", 123, "4321AB", "Haarlem",
                "The Netherlands", 500, 2000, Arrays.asList(Role.ROLE_CUSTOMER), "bruno@gmail.com", "dummyCustomer3", "secret123");

        Account testAccount = new Account(accountService.generateIban(), testUser, AccountType.ACCOUNT_TYPE_CURRENT, 100000000, "open", 0);

        AccountDTO dto = new AccountDTO();

        Mockito.when(accountService.add(any(Account.class)))
                .thenReturn(testAccount);

        mvc.perform(MockMvcRequestBuilders.post("/accounts/")
                        .contentType(MediaType.APPLICATION_JSON)  // Set the Content-Type header to application/json
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
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