package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.ErrorMessageDTO;
import io.swagger.model.LoginDTO;
import io.swagger.model.LoginInputDTO;
import io.swagger.service.AccountService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith({SpringExtension.class})
@WebMvcTest(LoginApiController.class)
@Import(LoginApiController.class)
@ActiveProfiles("test")
class LoginApiControllerTest {

    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private LoginApiController loginApiController;

    @MockBean
    private AccountsApiController accountController;

    @MockBean
    private TransactionsApiController transactionController;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private CustomersApiController customersApiController;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new LoginApiController(userService, mapper, null))
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }
    @Test
    void login_withValidCredentials_returnsLoginDTO() throws Exception {
        LoginInputDTO loginInputDTO = new LoginInputDTO();
        loginInputDTO.setUsername("john_doe");
        loginInputDTO.setPassword("password");

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setJwtToken("dummyToken");

        when(userService.login(any(String.class), any(String.class))).thenReturn(loginDTO);

        MockHttpServletResponse response = mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginInputDTO)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        LoginDTO responseDTO = mapper.readValue(response.getContentAsString(), LoginDTO.class);
        assertThat(responseDTO.getJwtToken()).isEqualTo("dummyToken");
    }

    @Test
    void login_withInvalidCredentials_returnsUnauthorized() throws Exception {
        LoginInputDTO loginInputDTO = new LoginInputDTO();
        loginInputDTO.setUsername("john_doe");
        loginInputDTO.setPassword("password");

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setJwtToken("dummyToken");

        when(userService.login(any(String.class), any(String.class))).thenReturn(null);

        MockHttpServletResponse response = mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginInputDTO)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.getContentAsString()).isNotNull();

        String errorMessage = response.getContentAsString();

        assertThat(errorMessage).isEqualTo("{\"reason\":\"Wrong login credentials.\"}");
    }
}