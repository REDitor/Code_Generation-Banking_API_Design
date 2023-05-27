package io.swagger.service;

import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.LoginDTO;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import io.swagger.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void add_ValidUser_ReturnsUserWithEncodedPassword() {
        
        User user = new User();
        user.setPassword("password");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        User result = userService.add(user);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    void getOneCustomer_ExistingUserId_ReturnsUserWithRoleCustomer() {
        UUID userId = UUID.randomUUID();
        User expectedUser = new User();
        expectedUser.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));

        when(userRepository.findUserByUserIdAndRolesContaining(eq(userId), eq(Role.ROLE_CUSTOMER))).thenReturn(expectedUser);

        User result = userService.getOneCustomer(userId);

        assertNotNull(result);
        assertEquals(expectedUser, result);
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
    void getAllEmployees_Pageable_ReturnsListOfEmployeeUsers() {
        
        Pageable pageable = mock(Pageable.class);
        Page<User> userPage = mock(Page.class);
        List<User> expectedUsers = new ArrayList<>();

        when(userRepository.findAllByRolesContaining(eq(pageable), eq(Role.ROLE_EMPLOYEE))).thenReturn(userPage);
        when(userPage.getContent()).thenReturn(expectedUsers);

        List<User> result = userService.getAllEmployees(pageable);

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
    void getAllNoAccounts_Pageable_ReturnsListOfCustomerUsersWithoutAccounts() {
        
        Pageable pageable = mock(Pageable.class);
        Page<User> userPage = mock(Page.class);
        List<User> expectedUsers = new ArrayList<>();

        when(userRepository.getAllByAccount_EmptyAndRolesContaining(eq(pageable), eq(Role.ROLE_CUSTOMER))).thenReturn(userPage);
        when(userPage.getContent()).thenReturn(expectedUsers);

        List<User> result = userService.getAllNoAccounts(pageable);

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
    void getUserByUsername_ExistingUsername_ReturnsUser() {
        String username = "john.doe";
        User expectedUser = new User();

        when(userRepository.findByUsername(eq(username))).thenReturn(expectedUser);

        User result = userService.getUserByUsername(username);

        assertNotNull(result);
        assertEquals(expectedUser, result);
    }

    @Test
    void getUserById_ExistingUserId_ReturnsUser() {
        UUID userId = UUID.randomUUID();
        User expectedUser = new User();

        when(userRepository.findUserByUserId(eq(userId))).thenReturn(expectedUser);
        
        User result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(expectedUser, result);
    }

    @Test
    void put_ValidUser_ReturnsUpdatedUser() {
        User user = new User();
        user.setuserId(UUID.randomUUID());

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.put(user);

        assertNotNull(result);
        assertEquals(user.getuserId(), result.getuserId());
    }

    @Test
    void save_ValidUser_ReturnsSavedUser() {
        User user = new User();

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.save(user);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void loadUserByUsername_ExistingUsername_ReturnsUserDetails() {
        String username = "john.doe";
        User user = new User();
        user.setPassword("password");
        user.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));

        when(userRepository.findByUsername(eq(username))).thenReturn(user);
        
        UserDetails result = userService.loadUserByUsername(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
        assertTrue(result.isAccountNonExpired());
        assertTrue(result.isAccountNonLocked());
        assertTrue(result.isCredentialsNonExpired());
    }

    @Test
    void getLoggedInUser_ValidToken_ReturnsUser() {
        String token = "jwtToken";
        String username = "john.doe";
        User expectedUser = new User();

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtTokenProvider.resolveToken(eq(request))).thenReturn(token);
        when(jwtTokenProvider.getUsername(eq(token))).thenReturn(username);
        when(userRepository.findByUsername(eq(username))).thenReturn(expectedUser);

        User result = userService.getLoggedUser(request);

        assertNotNull(result);
        assertEquals(expectedUser, result);
    }

    @Test
    void login_ValidUsernameAndPassword_ReturnsLoginDTO() {
        String username = "john.doe";
        String password = "password";

        User user = new User();
        user.setRoles(Collections.singletonList(Role.ROLE_CUSTOMER));
        user.setUsername(username);

        String token = "jwtToken";

        when(userRepository.findByUsername(eq(username))).thenReturn(user);
        when(jwtTokenProvider.createToken(eq(username), eq(user.getRoles()))).thenReturn(token);
        
        LoginDTO result = userService.login(username, password);

        assertNotNull(result);
        assertEquals(username, result.getUser().getUsername());
        assertEquals(token, result.getJwtToken());
    }

    @Test
    void isEmployee_UserWithEmployeeRole_ReturnsTrue() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        User user = new User();
        user.setRoles(Collections.singletonList(Role.ROLE_EMPLOYEE));

        UserService userServiceSpy = Mockito.spy(userService);
        doReturn(user).when(userServiceSpy).getLoggedUser(request);

        boolean result = userServiceSpy.isEmployee(request);

        assertTrue(result);
    }

    @Test
    void accountOwnerIsLoggedUser_SameUser_ReturnsTrue() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        User user = new User();
        Account account = new Account();
        account.setUser(user);

        UserService userServiceSpy = Mockito.spy(userService);
        doReturn(user).when(userServiceSpy).getLoggedUser(request);

        boolean result = userServiceSpy.accountOwnerIsLoggedUser(account, request);

        assertTrue(result);
    }



}