package io.swagger.service;

import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.LoginDTO;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import io.swagger.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void add_ValidUser_ReturnsUserWithEncodedPassword() {
        // Arrange
        User user = new User();
        user.setPassword("password");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User result = userService.add(user);

        // Assert
        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    void getOneCustomer_ExistingUserId_ReturnsUserWithRoleCustomer() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User expectedUser = new User();
        expectedUser.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));

        when(userRepository.findUserByUserIdAndRolesContaining(eq(userId), eq(Role.ROLE_CUSTOMER))).thenReturn(expectedUser);

        // Act
        User result = userService.getOneCustomer(userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser, result);
    }

    @Test
    void getOneEmployee_ExistingUserId_ReturnsUserWithRoleEmployee() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User expectedUser = new User();
        expectedUser.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));

        when(userRepository.findUserByUserIdAndRolesContaining(eq(userId), eq(Role.ROLE_EMPLOYEE))).thenReturn(expectedUser);

        // Act
        User result = userService.getOneEmployee(userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser, result);
    }

    @Test
    void getAll_Pageable_ReturnsListOfCustomerUsers() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<User> userPage = mock(Page.class);
        List<User> expectedUsers = new ArrayList<>();

        when(userRepository.findAllByRolesContaining(eq(pageable), eq(Role.ROLE_CUSTOMER))).thenReturn(userPage);
        when(userPage.getContent()).thenReturn(expectedUsers);

        // Act
        List<User> result = userService.getAll(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUsers, result);
    }

    @Test
    void getAllEmployees_Pageable_ReturnsListOfEmployeeUsers() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<User> userPage = mock(Page.class);
        List<User> expectedUsers = new ArrayList<>();

        when(userRepository.findAllByRolesContaining(eq(pageable), eq(Role.ROLE_EMPLOYEE))).thenReturn(userPage);
        when(userPage.getContent()).thenReturn(expectedUsers);

        // Act
        List<User> result = userService.getAllEmployees(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUsers, result);
        verify(userRepository).findAllByRolesContaining(pageable, Role.ROLE_EMPLOYEE);
        verify(userPage).getContent();
    }

    @Test
    void getAllByName_PageableAndName_ReturnsListOfCustomerUsersWithMatchingName() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<User> userPage = mock(Page.class);
        List<User> expectedUsers = new ArrayList<>();
        String firstName = "John";
        String lastName = "Doe";

        when(userRepository.getAllByFirstNameIsLikeOrLastNameIsLikeAndRolesContaining(eq(pageable), eq(firstName), eq(lastName), eq(Role.ROLE_CUSTOMER))).thenReturn(userPage);
        when(userPage.getContent()).thenReturn(expectedUsers);

        // Act
        List<User> result = userService.getAllByName(pageable, firstName, lastName);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUsers, result);
        verify(userRepository).getAllByFirstNameIsLikeOrLastNameIsLikeAndRolesContaining(pageable, firstName, lastName, Role.ROLE_CUSTOMER);
        verify(userPage).getContent();
    }

    @Test
    void getAllEmployeesByName_PageableAndName_ReturnsListOfEmployeeUsersWithMatchingName() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<User> userPage = mock(Page.class);
        List<User> expectedUsers = new ArrayList<>();
        String firstName = "John";
        String lastName = "Doe";

        when(userRepository.getAllByFirstNameIsLikeOrLastNameIsLikeAndRolesContaining(eq(pageable), eq(firstName), eq(lastName), eq(Role.ROLE_EMPLOYEE))).thenReturn(userPage);
        when(userPage.getContent()).thenReturn(expectedUsers);

        // Act
        List<User> result = userService.getAllEmployeesByName(pageable, firstName, lastName);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUsers, result);
        verify(userRepository).getAllByFirstNameIsLikeOrLastNameIsLikeAndRolesContaining(pageable, firstName, lastName, Role.ROLE_EMPLOYEE);
        verify(userPage).getContent();
    }

    @Test
    void getAllNoAccounts_Pageable_ReturnsListOfCustomerUsersWithoutAccounts() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<User> userPage = mock(Page.class);
        List<User> expectedUsers = new ArrayList<>();

        when(userRepository.getAllByAccount_EmptyAndRolesContaining(eq(pageable), eq(Role.ROLE_CUSTOMER))).thenReturn(userPage);
        when(userPage.getContent()).thenReturn(expectedUsers);

        // Act
        List<User> result = userService.getAllNoAccounts(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUsers, result);
        verify(userRepository).getAllByAccount_EmptyAndRolesContaining(pageable, Role.ROLE_CUSTOMER);
        verify(userPage).getContent();
    }

    @Test
    void getAllNoAccountsByName_PageableAndName_ReturnsListOfCustomerUsersWithoutAccountsAndMatchingName() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Page<User> userPage = mock(Page.class);
        List<User> expectedUsers = new ArrayList<>();
        String firstName = "John";
        String lastName = "Doe";

        when(userRepository.getAllByFirstNameOrLastNameAndAccount_EmptyAndRolesContaining(eq(pageable), eq(firstName), eq(lastName), eq(Role.ROLE_CUSTOMER))).thenReturn(userPage);
        when(userPage.getContent()).thenReturn(expectedUsers);

        // Act
        List<User> result = userService.getAllNoAccountsByName(pageable, firstName, lastName);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUsers, result);
        verify(userRepository).getAllByFirstNameOrLastNameAndAccount_EmptyAndRolesContaining(pageable, firstName, lastName, Role.ROLE_CUSTOMER);
        verify(userPage).getContent();
    }

    @Test
    void getUserByUsername_ExistingUsername_ReturnsUser() {
        // Arrange
        String username = "john.doe";
        User expectedUser = new User();

        when(userRepository.findByUsername(eq(username))).thenReturn(expectedUser);

        // Act
        User result = userService.getUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser, result);
        verify(userRepository).findByUsername(username);
    }

    @Test
    void getUserById_ExistingUserId_ReturnsUser() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User expectedUser = new User();

        when(userRepository.findUserByUserId(eq(userId))).thenReturn(expectedUser);

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser, result);
        verify(userRepository).findUserByUserId(userId);
    }

    @Test
    void put_ValidUser_ReturnsUpdatedUser() {
        // Arrange
        User user = new User();
        user.setuserId(UUID.randomUUID());

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User result = userService.put(user);

        // Assert
        assertNotNull(result);
        assertEquals(user.getuserId(), result.getuserId());
        verify(userRepository).save(user);
    }

    @Test
    void save_ValidUser_ReturnsSavedUser() {
        // Arrange
        User user = new User();

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User result = userService.save(user);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository).save(user);
    }

    @Test
    void loadUserByUsername_ExistingUsername_ReturnsUserDetails() {
        // Arrange
        String username = "john.doe";
        User user = new User();
        user.setPassword("password");
        user.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));

        when(userRepository.findByUsername(eq(username))).thenReturn(user);

        // Act
        UserDetails result = userService.loadUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
        assertTrue(result.isAccountNonExpired());
        assertTrue(result.isAccountNonLocked());
        assertTrue(result.isCredentialsNonExpired());
    }


}