package com.kodilla.tictactoe.logic;

public class InputValidator {
    public static InputValidationReturn validateInput(String input, int boardSideSize) {
        if (!input.matches("^\\d+ \\d+$")) {
            return InputValidationReturn.INVALID_PATTERN;
        }
        return InputValidationReturn.OK;
    }
}
