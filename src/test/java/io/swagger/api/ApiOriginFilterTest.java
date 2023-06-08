package io.swagger.api;

import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ApiOriginFilterTest {

    @Test
    public void testDoFilter() throws IOException, ServletException {
        // Create mock objects
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        // Create the filter and invoke the doFilter method
        ApiOriginFilter filter = new ApiOriginFilter();
        filter.doFilter(request, response, chain);

        // Verify that the headers are added and chain.doFilter is called
        assertEquals("*", response.getHeader("Access-Control-Allow-Origin"));
        assertEquals("GET, POST, DELETE, PUT", response.getHeader("Access-Control-Allow-Methods"));
        assertEquals("Content-Type", response.getHeader("Access-Control-Allow-Headers"));
    }

    @Test
    public void testDestroy() {
        ApiOriginFilter filter = new ApiOriginFilter();
        filter.destroy();
    }

    @Test
    public void testInit() throws ServletException {
        ApiOriginFilter filter = new ApiOriginFilter();
        FilterConfig filterConfig = mock(FilterConfig.class);
        filter.init(filterConfig);
    }
}