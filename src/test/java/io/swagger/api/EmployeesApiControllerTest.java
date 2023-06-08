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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith({SpringExtension.class})
@WebMvcTest(EmployeesApiController.class)
@Import({EmployeesApiController.class, UserService.class})
@ActiveProfiles("test")
class EmployeesApiControllerTest {
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
    private EmployeesApiController employeesApiController;

    private HttpServletRequest request;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new EmployeesApiController(mapper, null, userService, passwordEncoder))
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    void createEmployee_withValidRequestBody_returnsUserDTO() throws Exception {
        // Arrange
        User user = new User();
        user.setFirstName("Bruno");

        when(userService.add(any(User.class))).thenReturn(user);
        when(userService.getUserByUsername(any(String.class))).thenReturn(null);

        // Act
        MockHttpServletResponse response = mvc.perform(post("/employees")
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
                                "  \"Password\": \"secret123\"\n" +
                                "}"))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isNotNull();

        UserDTO userDTO = mapper.readValue(response.getContentAsString(), UserDTO.class);
        assertThat(userDTO.getFirstName()).isEqualTo("Bruno");
    }

    @Test
    void updateEmployee_withValidData_returnsUser() throws Exception {
        // Arrange
        UUID userID = UUID.randomUUID();

        User updatedUser = new User();
        updatedUser.setuserId(userID);
        updatedUser.setUsername("dummy1");
        updatedUser.setFirstName("Silvia");

        User loggedUser = new User();
        loggedUser.setuserId(UUID.randomUUID()); // Different user ID than the one being updated
        loggedUser.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));

        when(userService.getOneEmployee(userID)).thenReturn(updatedUser);
        when(userService.getLoggedUser(request)).thenReturn(loggedUser);
        when(userService.save(any(User.class))).thenReturn(updatedUser);

        MockHttpServletResponse response = mvc.perform(put("/employees/{userID}", userID.toString())
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
    void updateEmployee_withInvalidUserID_returnsBadRequest() throws Exception {
        // Arrange
        // Arrange
        UUID userID = UUID.randomUUID();

        User updatedUser = new User();
        updatedUser.setuserId(userID);
        updatedUser.setUsername("dummy1");
        updatedUser.setFirstName("Silvia");

        User loggedUser = new User();
        loggedUser.setuserId(UUID.randomUUID()); // Different user ID than the one being updated
        loggedUser.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));

        when(userService.getOneEmployee(userID)).thenReturn(updatedUser);
        when(userService.getLoggedUser(request)).thenReturn(loggedUser);
        when(userService.save(any(User.class))).thenReturn(null);

        MockHttpServletResponse response = mvc.perform(put("/employees/{userID}", userID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"FirstName\":\"Silvia\",\"LastName\":\"Coimbra Marques\",\"BirthDate\": \"1997-12-07\",\"StreetName\":\"Pietersbergweg\",\"HouseNumber\":1234,\"ZipCode\":\"0987 MB\",\"City\":\"Amsterdam\",\"Country\":\"Netherlands\",\"Roles\": [\"Customer\"],\"Email\":\"test@gmail.com\",\"Username\":\"dummyTest\",\"Password\":\"secret123\",\"TransactionAmountLimit\":2000,\"DailyLimit\":500}"))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void getEmployee_asEmployee_withValidEmployeeID_returnsUser() throws Exception {
        // Arrange
        UUID userID = UUID.randomUUID();

        User loggedUser = new User();
        loggedUser.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));

        User requestedUser = new User();
        requestedUser.setuserId(userID);
        requestedUser.setFirstName("John");

        when(userService.getLoggedUser(request)).thenReturn(loggedUser);
        when(userService.getOneEmployee(userID)).thenReturn(requestedUser);

        // Act
        MockHttpServletResponse response = mvc.perform(get("/employees/{employeeId}", userID.toString()))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        UserDTO userDTO = mapper.readValue(response.getContentAsString(), UserDTO.class);
        assertThat(userDTO.getFirstName()).isEqualTo("John");
    }

    @Test
    void getEmployee_asEmployee_withMatchingID_returnsUser() throws Exception {
        // Arrange
        UUID userID = UUID.randomUUID();

        User loggedUser = new User();
        loggedUser.setuserId(userID);
        loggedUser.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));

        User requestedUser = new User();
        requestedUser.setuserId(userID);
        requestedUser.setFirstName("John");

        when(userService.getLoggedUser(request)).thenReturn(loggedUser);
        when(userService.getOneEmployee(userID)).thenReturn(requestedUser);

        // Act
        MockHttpServletResponse response = mvc.perform(get("/employees/{employeeId}", userID.toString()))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        UserDTO userDTO = mapper.readValue(response.getContentAsString(), UserDTO.class);
        assertThat(userDTO.getFirstName()).isEqualTo("John");
    }

    @Test
    void getEmployee_withInvalidEmployeeID_returnsNotFound() throws Exception {
        // Arrange
        UUID userID = UUID.randomUUID();
        User loggedUser = new User();
        loggedUser.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));
        loggedUser.setuserId(userID);

        when(userService.getLoggedUser(request)).thenReturn(loggedUser);
        when(userService.getOneEmployee(userID)).thenReturn(null);

        // Act
        MockHttpServletResponse response = mvc.perform(get("/employees/{employeeId}", userID.toString()))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void getEmployee_withInvalidEmployeeId_returnsBadRequest() throws Exception {
        // Arrange
        String invalidEmployeeId = "invalid-employee-id";

        MockHttpServletResponse response = mvc.perform(get("/employees/{employeeId}", invalidEmployeeId))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void getEmployees_withoutFilters_returnsAllEmployees() throws Exception {

        User employees1 = new User();
        employees1.setFirstName("John");
        employees1.setFirstName("Doe");

        User employees2 = new User();
        employees2.setFirstName("John");
        employees2.setFirstName("Doe");

        // Arrange
        List<User> employees = Arrays.asList(
                employees1,
                employees2
        );

        when(userService.getAllEmployees(any(Pageable.class))).thenReturn(employees);

        // Act
        MockHttpServletResponse response = mvc.perform(get("/employees")
                        .param("limit", "10")
                        .param("offset", "0"))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
        assertThat(response.getContentAsString()).contains("Doe");
    }

    @Test
    void getEmployees_withFirstNameFilter_returnsEmployeesWithMatchingFirstName() throws Exception {
        // Arrange
        String firstName = "John";
        List<User> customers = Arrays.asList(
                new User("John", "Doe", null, null, null, null, null, null, null, null, null, null, null, null),
                new User("John", "Smith", null, null, null, null, null, null, null, null, null, null, null, null),
                new User("Test", "test123", null, null, null, null, null, null, null, null, null, null, null, null)
        );

        when(userService.getAllEmployeesByName(any(Pageable.class), eq(firstName), isNull())).thenReturn(customers);

        // Act
        MockHttpServletResponse response = mvc.perform(get("/employees")
                        .param("firstName", firstName)
                        .param("limit", "10")
                        .param("offset", "0"))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
        assertThat(response.getContentAsString()).contains("John");
    }

    @Test
    void getEmployees_withLastNameFilter_returnsEmployeesWithMatchingLastName() throws Exception {
        // Arrange
        String lastName = "Doe";
        List<User> customers = Arrays.asList(
                new User("John", "Doe", null, null, null, null, null, null, null, null, null, null, null, null),
                new User("John", "Smith", null, null, null, null, null, null, null, null, null, null, null, null),
                new User("Test", "test123", null, null, null, null, null, null, null, null, null, null, null, null)
        );

        when(userService.getAllEmployeesByName(any(Pageable.class), isNull(), eq(lastName))).thenReturn(customers);

        // Act
        MockHttpServletResponse response = mvc.perform(get("/employees")
                        .param("lastName", lastName)
                        .param("limit", "10")
                        .param("offset", "0"))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
        assertThat(response.getContentAsString()).contains("Doe");
    }

    @Test
    void getEmployees_withInvalidPaginationValues_returnsBadRequest() throws Exception {
        // Arrange
        Integer offset = -1;
        Integer limit = 10;

        MockHttpServletResponse response = mvc.perform(get("/employees")
                        .param("offset", offset.toString())
                        .param("limit", limit.toString()))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isNotNull();
    }
}
