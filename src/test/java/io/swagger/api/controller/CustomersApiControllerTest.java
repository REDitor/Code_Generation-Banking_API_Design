package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.CustomersApiController;
import io.swagger.api.UserApiController;
import io.swagger.model.UserDTO;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import io.swagger.repository.UserRepository;
import io.swagger.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.engine.JupiterTestEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.threeten.bp.LocalDate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserApiController.class)
@ContextConfiguration(classes = {UserService.class, JpaRepository.class})
class CustomersApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void createCustomer() throws Exception {
        UserDTO dto = new UserDTO();
        User user = new User("Bruno", "Coimbra Marques", LocalDate.of(1999, 12, 10), "Pietersbergweg", 123, "1234 BM", "Amsterdam", "Netherlands", 1000, 500, Collections.singletonList(Role.ROLE_CUSTOMER), "brumarq", "test..123");
        when(userService.add(user)).thenReturn(user);
        this.mockMvc.perform(post("/customers")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getCustomer() {
    }

    @Test
    void getCustomers() {
    }

    @Test
    void updateCustomer() {
    }
}