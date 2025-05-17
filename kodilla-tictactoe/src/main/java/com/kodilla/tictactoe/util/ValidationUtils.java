package com.kodilla.tictactoe.util;

import com.kodilla.tictactoe.logic.ErrorReason;
import com.kodilla.tictactoe.logic.GameValidationException;
import com.kodilla.tictactoe.model.Figure;

public final class ValidationUtils {
    private ValidationUtils() {
    }

    public static <T> T requireNonNull(T object, ErrorReason reason) {
        if (object == null) {
            throw new GameValidationException(reason);
        }
        return object;
    }
}
