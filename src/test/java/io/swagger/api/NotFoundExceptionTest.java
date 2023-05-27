package io.swagger.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NotFoundExceptionTest {

    @Test
    public void testConstructorAndGetters() {
        int code = 404;
        String msg = "Not found";

        NotFoundException exception = new NotFoundException(code, msg);
        assertEquals(msg, exception.getMessage());
    }
}