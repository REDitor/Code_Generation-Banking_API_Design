package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith({SpringExtension.class})
@WebMvcTest(AccountsApiController.class)
@Import(AccountsApiController.class)
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

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new TransactionsApiController(accountRepository, mapper, null, transactionService, userService))
                .build();
        mapper.registerModule(new JavaTimeModule());
    }

    // TODO: Add Tests here....
}
