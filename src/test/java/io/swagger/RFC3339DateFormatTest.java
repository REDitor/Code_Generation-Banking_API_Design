package io.swagger;

import org.junit.jupiter.api.Test;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RFC3339DateFormatTest {

    @Test
    void format_shouldFormatDateWithMilliseconds() {
        // Set the time zone to UTC
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        TimeZone.setDefault(timeZone);

        RFC3339DateFormat dateFormat = new RFC3339DateFormat();
        Date date = new Date(1234567890123L);
        StringBuffer stringBuffer = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(0);
        SimpleDateFormat expectedFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        expectedFormat.setTimeZone(timeZone);

        StringBuffer result = dateFormat.format(date, stringBuffer, fieldPosition);

        assertEquals(expectedFormat.format(date), result.toString());
    }
}
