package com.kodilla.tictactoe.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputValidatorTest {
    @ParameterizedTest(name = "\"{0}\" should return OK")
    @CsvSource({
        "1 1",
        "10 5",
        "100 200"
    })
    void shouldReturnOkForValidInput(String input) {
        assertThat(InputValidator.validateInput(input)).isEqualTo(InputValidationReturn.OK);
    }

    @ParameterizedTest(name = "\"{0}\" should return INVALID_PATTERN")
    @CsvSource({
        "1",
        "a b",
        "0 1",
        "-1 2",
        "1, 1",
        "1  2",
        "' 1 2'",
        "'1 2 '"
    })
    void shouldReturnInvalidPatternForInvalidInput(String input) {
        assertThat(InputValidator.validateInput(input)).isEqualTo(InputValidationReturn.INVALID_PATTERN);
    }

    @Test
    void shouldThrowExceptionForNullInput() {
        GameValidationException exception = assertThrows(
                GameValidationException.class, () -> InputValidator.validateInput(null)
        );
        assertEquals(ErrorReason.NULL_INPUT, exception.getReason());
    }
}
