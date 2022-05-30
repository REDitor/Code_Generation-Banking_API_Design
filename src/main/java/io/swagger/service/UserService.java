package io.swagger.service;

import io.swagger.model.entity.Account;
import io.swagger.model.entity.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public User add(User user) {
        return userRepository.save(user);
    }

    public User getOne(UUID userID) {
        return userRepository.getOne(userID);
    }

    public User getOneEmployee(UUID userID) {
        return userRepository.getOneEmployee(userID);
    }

    public List<User> getAll(Pageable page) {
        Page<User> customerPage = userRepository.getAllCustomers(page);

        return customerPage.getContent();
    }

    public List<User> getAllEmployees(Pageable page) {
        Page<User> customerPage = userRepository.getAllEmployees(page);

        return customerPage.getContent();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return null;
    }
}
