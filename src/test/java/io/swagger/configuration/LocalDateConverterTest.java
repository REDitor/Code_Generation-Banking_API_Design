package io.swagger.configuration;

import org.junit.jupiter.api.Test;
import org.threeten.bp.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateConverterTest {
    @Test
    void convert_shouldConvertValidDateStringToLocalDate() {
        LocalDateConverter converter = new LocalDateConverter("yyyy-MM-dd");
        String dateString = "2023-05-31";
        LocalDate expected = LocalDate.of(2023, 5, 31);

        LocalDate result = converter.convert(dateString);

        assertEquals(expected, result);
    }

    @Test
    void convert_shouldReturnNullForNullString() {
        LocalDateConverter converter = new LocalDateConverter("yyyy-MM-dd");

        LocalDate result = converter.convert(null);

        assertNull(result);
    }
}
