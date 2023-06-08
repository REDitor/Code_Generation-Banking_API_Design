package io.swagger.service;

import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.LoginDTO;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import io.swagger.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    JwtTokenProvider jwtTokenProvider;

    AuthenticationManager authenticationManager;

    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Add new User
    public User add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Get one
    public User getOneCustomer(UUID userID) {
        return userRepository.findUserByUserIdAndRolesContaining(userID, Role.ROLE_CUSTOMER);
    }

    public User getOneEmployee(UUID userID) {
        return userRepository.findUserByUserIdAndRolesContaining(userID, Role.ROLE_EMPLOYEE);
    }

    // Get all
    public List<User> getAll(Pageable page) {
        Page<User> customerPage = userRepository.findAllByRolesContaining(page, Role.ROLE_CUSTOMER);

        return customerPage.getContent();
    }

    public List<User> getAllEmployees(Pageable page) {
        Page<User> customerPage = userRepository.findAllByRolesContaining(page, Role.ROLE_EMPLOYEE);

        return customerPage.getContent();
    }

    //Get all by name
    public List<User> getAllByName(Pageable page, String firstName, String lastName) {
        Page<User> customerPage = userRepository.getAllByFirstNameIsLikeOrLastNameIsLikeAndRolesContaining(page, firstName, lastName, Role.ROLE_CUSTOMER);

        return customerPage.getContent();
    }

    public List<User> getAllEmployeesByName(Pageable page, String firstName, String lastName) {
        Page<User> customerPage = userRepository.getAllByFirstNameIsLikeOrLastNameIsLikeAndRolesContaining(page, firstName, lastName, Role.ROLE_EMPLOYEE);;

        return customerPage.getContent();
    }

    //Get all without accounts
    public List<User> getAllNoAccounts(Pageable page) {
        Page<User> customerPage = userRepository.getAllByAccount_EmptyAndRolesContaining(page, Role.ROLE_CUSTOMER);

        return customerPage.getContent();
    }

    public List<User> getAllNoAccountsByName(Pageable page, String firstName, String lastName) {
        Page<User> customerPage = userRepository.getAllByFirstNameOrLastNameAndAccount_EmptyAndRolesContaining(page, firstName, lastName, Role.ROLE_CUSTOMER);

        return customerPage.getContent();
    }

    // Get by Username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Get by Id
    public User getUserById(UUID id) {
        return userRepository.findUserByUserId(id);
    }

    // Other
    public User put(User user) {
        user = userRepository.save(user);
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public LoginDTO login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            User user = userRepository.findByUsername(username);
            String token = jwtTokenProvider.createToken(username, user.getRoles());

            // Prepare user information and jwt token
            LoginDTO newLoginInformation = new LoginDTO();
            newLoginInformation.setUser(user);
            newLoginInformation.setJwtToken(token);

            return newLoginInformation;

        } catch (AuthenticationException e) {
            return null;
        }
    }

    public User getLoggedUser(HttpServletRequest request) {
        // Get JWT token and the information of the authenticated user
        String receivedToken = jwtTokenProvider.resolveToken(request);
        jwtTokenProvider.validateToken(receivedToken);
        String authenticatedUserUsername = jwtTokenProvider.getUsername(receivedToken);

        return getUserByUsername(authenticatedUserUsername);
    }

    public boolean isEmployee(HttpServletRequest request) {
        return this.getLoggedUser(request).getRoles().contains(Role.ROLE_EMPLOYEE);
    }

    // returns true if logged user is owner of the account passed in the parameter
    public boolean accountOwnerIsLoggedUser(Account account, HttpServletRequest request) {
        return account.getUser() == this.getLoggedUser(request);
    }

}
