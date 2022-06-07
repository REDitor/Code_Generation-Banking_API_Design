package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import io.swagger.service.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.threeten.bp.LocalDate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomersApiControllerTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void testCreateCustomer() throws Exception {

        User userToBeAdded = new User("Bruno", "Coimbra Marques", LocalDate.of(1999, 12, 10), "Pietersbergweg", 123, "1234 BM", "Amsterdam", "Netherlands", 1000, 500, Collections.singletonList(Role.ROLE_CUSTOMER), "brumarq", "test..123");
        User userToBeReceived = new User(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), "Bruno", "Coimbra Marques", LocalDate.of(1999, 12, 10), "Pietersbergweg", 123, "1234 BM", "Amsterdam", "Netherlands", 1000, 500, Collections.singletonList(Role.ROLE_CUSTOMER), "brumarq", "test..123");

        when(userService.add(userToBeAdded)).thenReturn(userToBeReceived);
        this.mockMvc.perform(post("/customers"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].brand.name").value("Fender"));
    }

    public void testGetCustomer() {
    }

    public void testGetCustomers() {
    }

    public void testUpdateCustomer() {
    }
}