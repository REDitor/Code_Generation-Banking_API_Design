package io.swagger.api;

import io.swagger.model.ExceptionDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ResponseStatusException.class })
    protected ResponseEntity<Object> handleResponseStatusException(ResponseStatusException exc, WebRequest request) {
        ExceptionDTO dto = new ExceptionDTO(exc.getMessage());
        return handleExceptionInternal(exc, dto, new HttpHeaders(), HttpStatus.valueOf(exc.getStatus().value()), request);
    }

    @ExceptionHandler(value = { ArrayIndexOutOfBoundsException.class })
    protected ResponseEntity<Object> handleOutOfBoundsException(ArrayIndexOutOfBoundsException exc, WebRequest request) {
        ExceptionDTO dto = new ExceptionDTO("Array index was out of bounds");
        return handleExceptionInternal(exc, dto, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(value = { NullPointerException.class })
    protected ResponseEntity<Object> handleNullPointerException(NullPointerException exc, WebRequest request) {
        ExceptionDTO dto = new ExceptionDTO(exc.getMessage());
        return handleExceptionInternal(exc, dto, new HttpHeaders(), HttpStatus.valueOf(418), request);
    }
}
