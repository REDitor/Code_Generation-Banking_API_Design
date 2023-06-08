package io.swagger.service;

import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import io.swagger.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getOneEmployee_ExistingUserId_ReturnsUserWithRoleEmployee() {

        UUID userId = UUID.randomUUID();
        User expectedUser = new User();
        expectedUser.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));

        when(userRepository.findUserByUserIdAndRolesContaining(eq(userId), eq(Role.ROLE_EMPLOYEE))).thenReturn(expectedUser);

        User result = userService.getOneEmployee(userId);

        assertNotNull(result);
        assertEquals(expectedUser, result);
    }

    @Test
    void getAll_Pageable_ReturnsListOfCustomerUsers() {

        Pageable pageable = mock(Pageable.class);
        Page<User> userPage = mock(Page.class);
        List<User> expectedUsers = new ArrayList<>();

        when(userRepository.findAllByRolesContaining(eq(pageable), eq(Role.ROLE_CUSTOMER))).thenReturn(userPage);
        when(userPage.getContent()).thenReturn(expectedUsers);

        List<User> result = userService.getAll(pageable);

        assertNotNull(result);
        assertEquals(expectedUsers, result);
    }

    @Test
    void getAllByName_PageableAndName_ReturnsListOfCustomerUsersWithMatchingName() {

        Pageable pageable = mock(Pageable.class);
        Page<User> userPage = mock(Page.class);
        List<User> expectedUsers = new ArrayList<>();
        String firstName = "John";
        String lastName = "Doe";

        when(userRepository.getAllByFirstNameIsLikeOrLastNameIsLikeAndRolesContaining(eq(pageable), eq(firstName), eq(lastName), eq(Role.ROLE_CUSTOMER))).thenReturn(userPage);
        when(userPage.getContent()).thenReturn(expectedUsers);

        List<User> result = userService.getAllByName(pageable, firstName, lastName);

        assertNotNull(result);
        assertEquals(expectedUsers, result);
    }

    @Test
    void getAllEmployeesByName_PageableAndName_ReturnsListOfEmployeeUsersWithMatchingName() {

        Pageable pageable = mock(Pageable.class);
        Page<User> userPage = mock(Page.class);
        List<User> expectedUsers = new ArrayList<>();
        String firstName = "John";
        String lastName = "Doe";

        when(userRepository.getAllByFirstNameIsLikeOrLastNameIsLikeAndRolesContaining(eq(pageable), eq(firstName), eq(lastName), eq(Role.ROLE_EMPLOYEE))).thenReturn(userPage);
        when(userPage.getContent()).thenReturn(expectedUsers);

        List<User> result = userService.getAllEmployeesByName(pageable, firstName, lastName);

        assertNotNull(result);
        assertEquals(expectedUsers, result);
    }

    @Test
    void getAllNoAccountsByName_PageableAndName_ReturnsListOfCustomerUsersWithoutAccountsAndMatchingName() {
        Pageable pageable = mock(Pageable.class);
        Page<User> userPage = mock(Page.class);
        List<User> expectedUsers = new ArrayList<>();
        String firstName = "John";
        String lastName = "Doe";

        when(userRepository.getAllByFirstNameOrLastNameAndAccount_EmptyAndRolesContaining(eq(pageable), eq(firstName), eq(lastName), eq(Role.ROLE_CUSTOMER))).thenReturn(userPage);
        when(userPage.getContent()).thenReturn(expectedUsers);

        List<User> result = userService.getAllNoAccountsByName(pageable, firstName, lastName);

        assertNotNull(result);
        assertEquals(expectedUsers, result);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String username = "nonexistentUser";
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(username);
        });
    }
}