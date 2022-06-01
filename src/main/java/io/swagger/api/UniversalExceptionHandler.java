package io.swagger.api;

import io.swagger.model.ErrorMessageDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class UniversalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { NullPointerException.class })
    protected ResponseEntity<Object> handleAuthenticationException(NullPointerException ex,
                                                                   WebRequest request) {
        ErrorMessageDTO dto = new ErrorMessageDTO("Unauthorized or authorization information is missing or invalid.");
        return handleExceptionInternal(ex, dto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
