package com.kodilla.tictactoe.util;

import com.kodilla.tictactoe.logic.ErrorReason;
import com.kodilla.tictactoe.logic.GameValidationException;
import com.kodilla.tictactoe.model.Figure;

public final class ValidationUtils {
    private ValidationUtils() {
    }

    public static void validateFigure(Figure figure) {
        if (figure == null) {
            throw new GameValidationException(ErrorReason.NULL_FIGURE);
        }
    }
}
