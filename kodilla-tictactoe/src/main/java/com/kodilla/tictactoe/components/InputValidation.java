package com.kodilla.tictactoe.components;

public interface InputValidation {
    static InputValidationReturn inputValidation(String input, int boardSideSize) {
        if (!input.matches("^\\d+ \\d+$")) {
            return InputValidationReturn.INVALID_PATTERN;
        }

        String[] numbers = input.split(" ");
        int row = Integer.parseInt(numbers[0]);
        int col = Integer.parseInt(numbers[1]);

        if (row >= 1 && row <= boardSideSize && col >= 1 && col <= boardSideSize) {
            return InputValidationReturn.OK;
        } else {
            return InputValidationReturn.OUT_OF_BOUNDS;
        }
    }
}
