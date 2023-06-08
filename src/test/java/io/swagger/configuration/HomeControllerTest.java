package io.swagger.configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerTest {

    @Test
    void index_shouldRedirectToSwaggerUI() {
        // Arrange
        HomeController homeController = new HomeController();

        // Act
        String result = homeController.index();

        // Assert
        assertEquals("redirect:/swagger-ui/", result);
    }
}
