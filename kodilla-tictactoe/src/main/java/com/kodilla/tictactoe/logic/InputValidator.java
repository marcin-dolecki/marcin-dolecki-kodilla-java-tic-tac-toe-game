package com.kodilla.tictactoe.logic;

import static com.kodilla.tictactoe.util.ValidationUtils.requireNonNull;

public final class InputValidator {
    public static InputValidationReturn validateInput(String input) {
        requireNonNull(input, ErrorReason.NULL_INPUT);

        if (!input.matches("^[1-9]\\d* [1-9]\\d*$")) {
            return InputValidationReturn.INVALID_PATTERN;
        }
        return InputValidationReturn.OK;
    }
}
