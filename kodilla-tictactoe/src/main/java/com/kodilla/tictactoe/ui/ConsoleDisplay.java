package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.logic.ErrorReason;
import com.kodilla.tictactoe.model.Board;
import static com.kodilla.tictactoe.util.ValidationUtils.requireNonNull;

import java.util.Scanner;

public final class ConsoleDisplay implements UserInterface {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void displayBoard(Board board) {
        requireNonNull(board, ErrorReason.NULL_BOARD);

        int boardSideSize = board.getBoardSideSize();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("   ");
        for (int col = 0; col < boardSideSize; col++) {
            stringBuilder.append(col + 1).append(" ");
        }
        stringBuilder.append("\n");

        for (int row = 0; row < boardSideSize; row++) {
            stringBuilder.append(row + 1);

            if (row + 1 < 10) stringBuilder.append(" ");

            for (int col = 0; col < boardSideSize; col++) {
                stringBuilder.append("|").append(board.getValue(row, col).getStringValue());
            }
            stringBuilder.append("|\n");
        }
        System.out.println(stringBuilder);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getTextInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
}
