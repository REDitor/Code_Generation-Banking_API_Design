package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.Transaction;
import io.swagger.model.entity.User;
import io.swagger.repository.AccountRepository;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class})
@WebMvcTest(TransactionsApiController.class)
@Import(TransactionsApiController.class)
@ActiveProfiles("test")
public class TransactionsApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private UserService userService;

    @MockBean
    private AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(new TransactionsApiController(accountRepository, mapper, null, transactionService, userService))
                .build();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testTransactionsGetByUserId() throws Exception {
        // Mocking data
        UUID userId = UUID.randomUUID();

        List<Transaction> transactions = new ArrayList<>();

        when(transactionService.getAllByUserIdBetweenTimestamps(any(UUID.class), any(), any()))
                .thenReturn(transactions);

        mvc.perform(get("/transactions/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.size()").value(transactions.size()));
    }

    @Test
    public void testTransactionsGetByUserIdFromDateTime() throws Exception {
        // Mocking data
        UUID userId = UUID.randomUUID();
        String dateTimeFrom = "01-01-2023 00:00:00";

        List<Transaction> transactions = new ArrayList<>();

        when(transactionService.getAllByUserIdBetweenTimestamps(any(UUID.class), any(), any()))
                .thenReturn(transactions);

        // Perform the GET request
        mvc.perform(get("/transactions/users/" + userId)
                        .param("dateTimeFrom", dateTimeFrom))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.size()").value(transactions.size()));
    }

    @Test
    public void testTransactionsGetByUserIdToDateTime() throws Exception {
        // Mocking data
        UUID userId = UUID.randomUUID();
        String dateTimeTo = "31-12-2023 00:00:00";

        List<Transaction> transactions = new ArrayList<>();

        when(transactionService.getAllByUserIdBetweenTimestamps(any(UUID.class), any(), any()))
                .thenReturn(transactions);

        mvc.perform(get("/transactions/users/" + userId)
                        .param("dateTimeTo", dateTimeTo))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.size()").value(transactions.size()));
    }

    @Test
    public void testTransactionsGetByUserIdFromAndToDateTime() throws Exception {
        // Mocking data
        UUID userId = UUID.randomUUID();
        String dateTimeFrom = "01-01-2023 00:00:00";
        String dateTimeTo = "31-12-2023 00:00:00";

        List<Transaction> transactions = new ArrayList<>();

        when(transactionService.getAllByUserIdBetweenTimestamps(any(UUID.class), any(), any()))
                .thenReturn(transactions);

        mvc.perform(get("/transactions/users/" + userId)
                        .param("dateTimeFrom", dateTimeFrom)
                        .param("dateTimeTo", dateTimeTo))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.size()").value(transactions.size()));
    }

    @Test
    public void testTransactionsGetByUserId_WhenNoTransactionsExist() throws Exception {
        UUID userId = UUID.randomUUID();

        when(transactionService.getAllByUserIdBetweenTimestamps(any(UUID.class), any(), any()))
                .thenReturn(new ArrayList<>());

        mvc.perform(get("/transactions/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    public void testTransactionsGetByUserId_WithInvalidUserId() throws Exception {
        String invalidUserId = "invalidUserId";

        // Perform the GET request with an invalid userId
        mvc.perform(get("/transactions/users/" + invalidUserId))
                .andExpect(status().isBadRequest());
    }


    // Withdraw
    @Test
    public void testVerifyWithdrawal_WithInvalidUserId() throws Exception {
        // Perform the POST request with an invalid userId
        mvc.perform(post("/transactions/NL01INHO0000000002/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"invalidUserId\",\"accountId\":\"" + UUID.randomUUID() + "\",\"amount\":100.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testVerifyWithdrawal_WithNegativeAmount() throws Exception {
        // Perform the POST request with a negative amount
        mvc.perform(post("/transactions/NL01INHO0000000002/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"" + UUID.randomUUID() + "\",\"accountId\":\"" + UUID.randomUUID() + "\",\"amount\":-100.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testVerifyWithdrawal_WithSufficientBalance() throws Exception {
        // Mocking data
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        double amount = 100.0;

        Account account = new Account();
        account.setIBAN("1234567890");
        account.setBalance(200.0);
        // Set other account properties as needed

        when(accountRepository.findByUserIdAndAccountId(userId, accountId))
                .thenReturn(Optional.of(account));

        // Perform the POST request
        mvc.perform(post("/transactions/verify-withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"" + userId + "\",\"accountId\":\"" + accountId + "\",\"amount\":" + amount + "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.canWithdraw").value(true));
    }
}


















