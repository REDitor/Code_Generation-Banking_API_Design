package io.swagger.api;

import io.swagger.api.ApiResponseMessage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApiResponseMessageTest {

    @Test
    public void testConstructorAndGetterMethods() {
        int codeError = ApiResponseMessage.ERROR;
        int codeWarning = ApiResponseMessage.WARNING;
        int codeInfo = ApiResponseMessage.INFO;
        int codeOk = ApiResponseMessage.OK;
        int codeTooBusy = ApiResponseMessage.TOO_BUSY;
        int codeUnknown = 10;

        String message = "Error message";

        ApiResponseMessage apiResponseMessageError = new ApiResponseMessage(codeError, message);
        ApiResponseMessage apiResponseMessageWarning = new ApiResponseMessage(codeWarning, message);
        ApiResponseMessage apiResponseMessageInfo = new ApiResponseMessage(codeInfo, message);
        ApiResponseMessage apiResponseMessageOk = new ApiResponseMessage(codeOk, message);
        ApiResponseMessage apiResponseMessageTooBusy = new ApiResponseMessage(codeTooBusy, message);
        ApiResponseMessage apiResponseMessageUnknown = new ApiResponseMessage(codeUnknown, message);

        assertEquals(codeError, apiResponseMessageError.getCode());
        assertEquals("error", apiResponseMessageError.getType());
        assertEquals(message, apiResponseMessageError.getMessage());

        assertEquals(codeWarning, apiResponseMessageWarning.getCode());
        assertEquals("warning", apiResponseMessageWarning.getType());
        assertEquals(message, apiResponseMessageWarning.getMessage());

        assertEquals(codeInfo, apiResponseMessageInfo.getCode());
        assertEquals("info", apiResponseMessageInfo.getType());
        assertEquals(message, apiResponseMessageInfo.getMessage());

        assertEquals(codeOk, apiResponseMessageOk.getCode());
        assertEquals("ok", apiResponseMessageOk.getType());
        assertEquals(message, apiResponseMessageOk.getMessage());

        assertEquals(codeTooBusy, apiResponseMessageTooBusy.getCode());
        assertEquals("too busy", apiResponseMessageTooBusy.getType());
        assertEquals(message, apiResponseMessageTooBusy.getMessage());

        assertEquals(codeUnknown, apiResponseMessageUnknown.getCode());
        assertEquals("unknown", apiResponseMessageUnknown.getType());
        assertEquals(message, apiResponseMessageUnknown.getMessage());
    }

    @Test
    public void testSetters() {
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();

        int code = 2;
        String type = "warning";
        String message = "Warning message";

        apiResponseMessage.setCode(code);
        apiResponseMessage.setType(type);
        apiResponseMessage.setMessage(message);

        assertEquals(code, apiResponseMessage.getCode());
        assertEquals(type, apiResponseMessage.getType());
        assertEquals(message, apiResponseMessage.getMessage());
    }
}
