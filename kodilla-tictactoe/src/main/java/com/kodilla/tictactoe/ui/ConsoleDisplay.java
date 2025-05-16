package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.model.Figure;
import com.kodilla.tictactoe.model.Board;

import java.util.Scanner;

public class ConsoleDisplay implements UserInterface {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void displayBoard(Board board) {
        int boardSideSize = board.getBoardSideSize();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("    ");
        for (int col = 0; col < boardSideSize; col++) {
            stringBuilder.append(col + 1).append(" ");
        }
        stringBuilder.append("\n");

        for (int row = 0; row < boardSideSize; row++) {
            stringBuilder.append(row + 1).append(" ");

            if (row + 1 < 10) stringBuilder.append(" ");

            for (int col = 0; col < boardSideSize; col++) {
                stringBuilder.append("|").append(formatCell(board.getValue(row, col)));
            }
            stringBuilder.append("|\n");
        }
        System.out.println(stringBuilder.toString());
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

    private String formatCell(Figure value) {
        return value == Figure.EMPTY ? " " : value.name();
    }
}
