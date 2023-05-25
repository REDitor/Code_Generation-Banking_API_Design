package io.swagger.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.model.AccountDTO;
import io.swagger.model.NewUserDTO;
import io.swagger.model.UpdateUserDTO;
import io.swagger.model.UserDTO;
import io.swagger.model.entity.User;
import io.swagger.service.AccountService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import io.swagger.model.entity.Role;

import org.threeten.bp.format.DateTimeFormatter;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

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
    void updateCustomer_withValidData_returnsUser() throws Exception {
        // Arrange
        UUID userID = UUID.randomUUID();

        User updatedUser = new User();
        updatedUser.setuserId(userID);
        updatedUser.setUsername("dummy1");
        updatedUser.setFirstName("Silvia");

        User loggedUser = new User();
        loggedUser.setuserId(userID);
        loggedUser.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));

        when(userService.getOneCustomer(userID)).thenReturn(updatedUser);
        when(userService.getLoggedUser(request)).thenReturn(loggedUser);
        when(userService.save(any(User.class))).thenReturn(updatedUser);

        MockHttpServletResponse response = mvc.perform(put("/customers/{userID}", userID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"FirstName\":\"Silvia\",\"LastName\":\"Coimbra Marques\",\"BirthDate\": \"1997-12-07\",\"StreetName\":\"Pietersbergweg\",\"HouseNumber\":1234,\"ZipCode\":\"0987 MB\",\"City\":\"Amsterdam\",\"Country\":\"Netherlands\",\"Roles\": [\"Customer\"],\"Email\":\"test@gmail.com\",\"Username\":\"dummyTest\",\"Password\":\"secret123\",\"TransactionAmountLimit\":2000,\"DailyLimit\":500}"))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        UserDTO accountDTO = mapper.readValue(response.getContentAsString(), UserDTO.class);
        assertThat(accountDTO.getFirstName()).isEqualTo("Silvia");
    }
    @Test
    void createCustomer() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void getCustomer() {
    }

    @Test
    void getCustomers() {
    }

    // Helper method to convert an object to JSON string
    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*@Test
    void createCustomer_withValidRequestBody_returnsUserDTO() throws Exception {
        String birthdayString = "1990-01-01";
        LocalDate birthday = LocalDate.parse(birthdayString, DateTimeFormatter.ISO_DATE);

        // Arrange
        NewUserDTO newUserDTO = new NewUserDTO();
        newUserDTO.setFirstName("Bruno");
        newUserDTO.setLastName("Coimbra Marques");
        newUserDTO.setStreetName("Pietersbergweg");
        newUserDTO.setHouseNumber(1234);
        newUserDTO.setZipCode("0987 MB");
        newUserDTO.setCity("Amsterdam");
        newUserDTO.setCountry("Netherlands");
        newUserDTO.setEmail("test@gmail.com");
        newUserDTO.setUsername("dummyTest");
        newUserDTO.setPassword("secret123");
        newUserDTO.setBirthDate("2002-01-01");
        newUserDTO.setTransactionAmountLimit(2000);
        newUserDTO.setDailyLimit(500);

        User user = new User();
        user.setFirstName("Bruno");
        // Set the properties of the user object as needed

        when(userService.add(any(User.class))).thenReturn(user);
        when(userService.getUserByUsername(any(String.class))).thenReturn(user);

        // Act

        String newUserDTOString = asJsonString(newUserDTO);

        MockHttpServletResponse response = mvc.perform(post("/users/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \n" +
                                "  \"FirstName\": \"firstNameTest\",\n" +
                                "  \"LastName\": \"lastNameTest\",\n" +
                                "\t\"BirthDate\": \"2022-06-07\",\n" +
                                "  \"StreetName\": \"Pietersbergweg\",\n" +
                                "  \"City\": \"Amsterdam\",\n" +
                                "  \"Country\": \"Netherlands\",\n" +
                                "  \"ZipCode\": \"0987 MB\",\t\n" +
                                "  \"Email\": \"test@gmail.com\",\n" +
                                "  \"DailyLimit\": 500,\n" +
                                "  \"HouseNumber\": 1234,\n" +
                                "  \"TransactionAmountLimit\": 2000,\n" +
                                "  \"Username\": \"brumarq\",\n" +
                                "  \"Password\": \"secret123\"\n" +
                                "}\n"))
                .andReturn().getResponse();

        String message = response.getContentAsString();
        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getContentAsString()).isNotNull();

        UserDTO userDTO = new ObjectMapper().readValue(response.getContentAsString(), UserDTO.class);
        assertThat(userDTO.getFirstName()).isEqualTo("Bruno");
    }*/
}
