package io.swagger.api;

import io.swagger.api.UniversalExceptionHandler;
import io.swagger.model.ErrorMessageDTO;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class UniversalExceptionHandlerTest {

    @Test
    public void testHandleAuthenticationException() {
        NullPointerException exception = new NullPointerException("Null pointer exception");
        WebRequest request = mock(WebRequest.class);

        UniversalExceptionHandler exceptionHandler = new UniversalExceptionHandler();
        ResponseEntity<Object> responseEntity = exceptionHandler.handleAuthenticationException(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(ErrorMessageDTO.class, responseEntity.getBody().getClass());

        ErrorMessageDTO responseBody = (ErrorMessageDTO) responseEntity.getBody();
        assertEquals("Unauthorized or authorization information is missing or invalid.", responseBody.getReason());
    }
}
