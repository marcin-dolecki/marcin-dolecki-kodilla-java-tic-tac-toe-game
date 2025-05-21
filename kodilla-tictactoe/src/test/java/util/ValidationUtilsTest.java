package util;

import com.kodilla.tictactoe.logic.ErrorReason;
import com.kodilla.tictactoe.logic.GameValidationException;
import com.kodilla.tictactoe.model.Figure;
import com.kodilla.tictactoe.util.ValidationUtils;
import org.junit.jupiter.api.Test;

import static com.kodilla.tictactoe.model.Figure.*;
import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {
    @Test
    void shouldReturnSameObjectWhenNotNull() {
        Figure input = X;
        Figure result = ValidationUtils.requireNonNull(input, ErrorReason.NULL_FIGURE);

        assertSame(input, result);
    }

    @Test
    void shouldThrowWhenObjectIsNull() {
        GameValidationException exception = assertThrows(GameValidationException.class, () ->
            ValidationUtils.requireNonNull(null, ErrorReason.NULL_FIGURE)
        );

        assertEquals(ErrorReason.NULL_FIGURE, exception.getReason());
    }
}
