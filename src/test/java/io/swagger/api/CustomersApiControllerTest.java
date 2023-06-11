package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.UserDTO;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import io.swagger.service.AccountService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith({SpringExtension.class})
@WebMvcTest(CustomersApiController.class)
@Import({CustomersApiController.class, UserService.class})
@ActiveProfiles("test")
class CustomersApiControllerTest {
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

    @MockBean
    private CustomersApiController customersApiController;

    private HttpServletRequest request;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new CustomersApiController(mapper, null, userService, passwordEncoder))
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    void createCustomer_withShortPassword_returnsBadRequest() throws Exception {
        User user = new User();
        user.setFirstName("Bruno");

        when(userService.add(any(User.class))).thenReturn(user);
        when(userService.getUserByUsername(any(String.class))).thenReturn(null);

        MockHttpServletResponse response = mvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "  \"FirstName\": \"firstNameTest\",\n" +
                                "  \"LastName\": \"lastNameTest\",\n" +
                                "  \"BirthDate\": \"2022-06-07\",\n" +
                                "  \"StreetName\": \"Pietersbergweg\",\n" +
                                "  \"City\": \"Amsterdam\",\n" +
                                "  \"Country\": \"Netherlands\",\n" +
                                "  \"ZipCode\": \"0987 MB\",\t\n" +
                                "  \"Email\": \"test@gmail.com\",\n" +
                                "  \"DailyLimit\": 500,\n" +
                                "  \"HouseNumber\": 1234,\n" +
                                "  \"TransactionAmountLimit\": 2000,\n" +
                                "  \"Username\": \"thisAccountDoesNotExist\",\n" +
                                "  \"Password\": \"secr\"\n" +
                                "}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void createCustomer_withEmptyUsername_returnsBadRequest() throws Exception {
        User user = new User();
        user.setFirstName("Bruno");

        when(userService.add(any(User.class))).thenReturn(user);
        when(userService.getUserByUsername(any(String.class))).thenReturn(null);

        MockHttpServletResponse response = mvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "  \"FirstName\": \"\",\n" +
                                "  \"LastName\": \"lastNameTest\",\n" +
                                "  \"BirthDate\": \"2022-06-07\",\n" +
                                "  \"StreetName\": \"Pietersbergweg\",\n" +
                                "  \"City\": \"Amsterdam\",\n" +
                                "  \"Country\": \"Netherlands\",\n" +
                                "  \"ZipCode\": \"0987 MB\",\t\n" +
                                "  \"Email\": \"test@gmail.com\",\n" +
                                "  \"DailyLimit\": 500,\n" +
                                "  \"HouseNumber\": 1234,\n" +
                                "  \"TransactionAmountLimit\": 2000,\n" +
                                "  \"Username\": \"thisAccountDoesNotExist\",\n" +
                                "  \"Password\": \"secr\"\n" +
                                "}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void updateCustomer_withInvalidUserID_returnsBadRequest() throws Exception {
        UUID userID = UUID.randomUUID();

        when(userService.getOneCustomer(userID)).thenReturn(null);

        MockHttpServletResponse response = mvc.perform(put("/customers/{userID}", userID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"FirstName\":\"Silvia\",\"LastName\":\"Coimbra Marques\",\"BirthDate\": \"1997-12-07\",\"StreetName\":\"Pietersbergweg\",\"HouseNumber\":1234,\"ZipCode\":\"0987 MB\",\"City\":\"Amsterdam\",\"Country\":\"Netherlands\",\"Roles\": [\"Customer\"],\"Email\":\"test@gmail.com\",\"Username\":\"dummyTest\",\"Password\":\"secret123\",\"TransactionAmountLimit\":2000,\"DailyLimit\":500}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isNotNull();
    }
    @Test
    void updateCustomer_withCustomerRoleOnly_setsCustomerRole() throws Exception {
        UUID userID = UUID.randomUUID();

        User updatedUser = new User();
        updatedUser.setuserId(userID);
        updatedUser.setUsername("dummy1");
        updatedUser.setFirstName("Silvia");
        updatedUser.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));

        User loggedUser = new User();
        loggedUser.setuserId(userID);
        loggedUser.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));

        when(userService.getOneCustomer(userID)).thenReturn(updatedUser);
        when(userService.getLoggedUser(request)).thenReturn(loggedUser);
        when(userService.save(any(User.class))).thenReturn(updatedUser);

        MockHttpServletResponse response = mvc.perform(put("/customers/{userID}", userID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"FirstName\":\"Silvia\",\"LastName\":\"Coimbra Marques\",\"BirthDate\": \"1997-12-07\",\"StreetName\":\"Pietersbergweg\",\"HouseNumber\":1234,\"ZipCode\":\"0987 MB\",\"City\":\"Amsterdam\",\"Country\":\"Netherlands\",\"Roles\": [\"Customer\"],\"Email\":\"test@gmail.com\",\"Username\":\"dummyTest\",\"Password\":\"secret123\",\"TransactionAmountLimit\":2000,\"DailyLimit\":500}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        UserDTO accountDTO = mapper.readValue(response.getContentAsString(), UserDTO.class);
        assertThat(accountDTO.getRoles()).isEqualTo(Collections.singletonList(Role.ROLE_CUSTOMER));
    }

    @Test
    void updateCustomer_withEmployeeRole_setsEmployeeRole() throws Exception {
        UUID userID = UUID.randomUUID();

        User updatedUser = new User();
        updatedUser.setuserId(userID);
        updatedUser.setUsername("dummy1");
        updatedUser.setFirstName("Silvia");
        updatedUser.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));

        User loggedUser = new User();
        loggedUser.setuserId(userID);
        loggedUser.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));

        when(userService.getOneCustomer(userID)).thenReturn(updatedUser);
        when(userService.getLoggedUser(request)).thenReturn(loggedUser);
        when(userService.save(any(User.class))).thenReturn(updatedUser);

        MockHttpServletResponse response = mvc.perform(put("/customers/{userID}", userID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"FirstName\":\"Silvia\",\"LastName\":\"Coimbra Marques\",\"BirthDate\": \"1997-12-07\",\"StreetName\":\"Pietersbergweg\",\"HouseNumber\":1234,\"ZipCode\":\"0987 MB\",\"City\":\"Amsterdam\",\"Country\":\"Netherlands\",\"Roles\": [\"Employee\"],\"Email\":\"test@gmail.com\",\"Username\":\"dummyTest\",\"Password\":\"secret123\",\"TransactionAmountLimit\":2000,\"DailyLimit\":500}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        UserDTO accountDTO = mapper.readValue(response.getContentAsString(), UserDTO.class);
        assertThat(accountDTO.getRoles()).isEqualTo(Collections.singletonList(Role.ROLE_EMPLOYEE));
    }

    @Test
    void updateCustomer_withWrongRole_setsCustomerRole() throws Exception {
        UUID userID = UUID.randomUUID();

        User updatedUser = new User();
        updatedUser.setuserId(userID);
        updatedUser.setUsername("dummy1");
        updatedUser.setFirstName("Silvia");
        updatedUser.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));

        User loggedUser = new User();
        loggedUser.setuserId(userID);
        loggedUser.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));

        when(userService.getOneCustomer(userID)).thenReturn(updatedUser);
        when(userService.getLoggedUser(request)).thenReturn(loggedUser);
        when(userService.save(any(User.class))).thenReturn(updatedUser);

        MockHttpServletResponse response = mvc.perform(put("/customers/{userID}", userID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"FirstName\":\"Silvia\",\"LastName\":\"Coimbra Marques\",\"BirthDate\": \"1997-12-07\",\"StreetName\":\"Pietersbergweg\",\"HouseNumber\":1234,\"ZipCode\":\"0987 MB\",\"City\":\"Amsterdam\",\"Country\":\"Netherlands\",\"Roles\": [\"Random\"],\"Email\":\"test@gmail.com\",\"Username\":\"dummyTest\",\"Password\":\"secret123\",\"TransactionAmountLimit\":2000,\"DailyLimit\":500}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void getCustomer_withInvalidCustomerID_returnsNotFound() throws Exception {
        UUID userID = UUID.randomUUID();
        User loggedUser = new User();
        loggedUser.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));
        loggedUser.setuserId(userID);

        when(userService.getLoggedUser(request)).thenReturn(loggedUser);
        when(userService.getOneCustomer(userID)).thenReturn(null);

        MockHttpServletResponse response = mvc.perform(get("/customers/{userID}", userID.toString()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isNotNull();
    }
    @Test
    void getCustomers_withoutFilters_returnsAllCustomers() throws Exception {

        User customer1 = new User();
        customer1.setFirstName("John");
        customer1.setFirstName("Doe");

        User customer2 = new User();
        customer1.setFirstName("John");
        customer1.setFirstName("Doe");

        List<User> customers = Arrays.asList(
                customer1,
                customer2
        );

        when(userService.getAll(any(Pageable.class))).thenReturn(customers);

        MockHttpServletResponse response = mvc.perform(get("/customers")
                        .param("noAccounts", "false")
                        .param("skip", "0")
                        .param("limit", "10"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
        assertThat(response.getContentAsString()).contains("Doe");
    }

    @Test
    void getCustomers_withFirstNameFilter_returnsCustomersWithMatchingFirstName() throws Exception {
        String firstName = "John";
        List<User> customers = Arrays.asList(
                new User("John", "Doe", null, null, null, null, null, null, null, null, null, null, null, null),
                new User("John", "Smith", null, null, null, null, null, null, null, null, null, null, null, null),
                new User("Test", "test123", null, null, null, null, null, null, null, null, null, null, null, null)
        );

        when(userService.getAllByName(any(Pageable.class), eq(firstName), isNull())).thenReturn(customers);

        MockHttpServletResponse response = mvc.perform(get("/customers")
                        .param("firstName", firstName)
                        .param("noAccounts", "false")
                        .param("skip", "0")
                        .param("limit", "10"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
        assertThat(response.getContentAsString()).contains("John");
    }

    @Test
    void getCustomers_withNoAccountsFilter_returnsCustomersWithNoAccounts() throws Exception {
        List<User> customers = Arrays.asList(
                new User("John", "Doe", null, null, null, null, null, null, null, null, null, null, null, null),
                new User("John", "Smith", null, null, null, null, null, null, null, null, null, null, null, null),
                new User("Test", "test123", null, null, null, null, null, null, null, null, null, null, null, null)
        );

        when(userService.getAllNoAccounts(any(Pageable.class))).thenReturn(customers);

        MockHttpServletResponse response = mvc.perform(get("/customers")
                        .param("noAccounts", "true")
                        .param("skip", "0")
                        .param("limit", "10"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
        assertThat(response.getContentAsString()).contains("John");
    }

    @Test
    void getCustomers_withFirstNameAndNoAccountsFilters_returnsCustomersWithMatchingFirstNameAndNoAccounts() throws Exception {
        String firstName = "John";
        List<User> customers = Arrays.asList(
                new User("John", "Doe", null, null, null, null, null, null, null, null, null, null, null, null),
                new User("John", "Smith", null, null, null, null, null, null, null, null, null, null, null, null),
                new User("Test", "test123", null, null, null, null, null, null, null, null, null, null, null, null)
        );
        when(userService.getAllNoAccountsByName(any(Pageable.class), eq(firstName), isNull())).thenReturn(customers);

        MockHttpServletResponse response = mvc.perform(get("/customers")
                        .param("firstName", firstName)
                        .param("noAccounts", "true")
                        .param("skip", "0")
                        .param("limit", "10"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
        assertThat(response.getContentAsString()).contains("John");
    }
//
    @Test
    void getCustomer_withInvalidUserID_returnsBadRequest() throws Exception {
        UUID userID = UUID.randomUUID();

        User loggedUser = new User();
        loggedUser.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));
        when(userService.getLoggedUser(request)).thenReturn(loggedUser);

        when(userService.getOneCustomer(userID)).thenReturn(null);

        MockHttpServletResponse response = mvc.perform(get("/customers/{userID}", userID.toString()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void getCustomers_withInvalidPaginationValues_returnsBadRequest() throws Exception {
        int skip = -1; // Invalid value for skip
        int limit = 20; // Valid value for limit
        boolean noAccounts = false;

        MockHttpServletResponse response = mvc.perform(get("/customers")
                        .param("skip", Integer.toString(skip))
                        .param("limit", Integer.toString(limit))
                        .param("noAccounts", Boolean.toString(noAccounts)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isNotNull();
    }
}
