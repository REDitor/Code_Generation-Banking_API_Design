package io.swagger.configuration;

import org.junit.jupiter.api.Test;
import org.threeten.bp.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LocalDateTimeConverterTest {

    @Test
    void convert_shouldConvertValidDateStringToLocalDateTime() {
        // Arrange
        LocalDateTimeConverter converter = new LocalDateTimeConverter("yyyy-MM-dd HH:mm:ss");
        String dateString = "2023-05-31 12:30:00";
        LocalDateTime expected = LocalDateTime.of(2023, 5, 31, 12, 30, 0);
        LocalDateTime result = converter.convert(dateString);
        assertEquals(expected, result);
    }
    @Test
    void convert_shouldReturnNullForNullString() {
        LocalDateTimeConverter converter = new LocalDateTimeConverter("yyyy-MM-dd HH:mm:ss");
        LocalDateTime result = converter.convert(null);
        assertNull(result);
    }

}
