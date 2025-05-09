package com.kodilla.tictactoe.components;

public interface ShowBoard {
    static String showBoard(Board board) {
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
        return stringBuilder.toString();
    }

    static String formatCell(Figure value) {
        return value == Figure.EMPTY ? " " : value.name();
    }
}
