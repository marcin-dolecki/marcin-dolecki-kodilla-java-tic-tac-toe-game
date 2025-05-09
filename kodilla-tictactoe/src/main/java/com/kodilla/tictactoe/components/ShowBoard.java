package com.kodilla.tictactoe.components;

public interface ShowBoard {
    static String showBoard(Board board) {
        int boardSideSize = board.getBoardSideSize();
        int legendIndex = 1;

        StringBuilder stringBuilder = new StringBuilder();

        for (int row = 0; row < boardSideSize; row++) {
            for (int col = 0; col < boardSideSize; col++) {
                stringBuilder.append("|").append(formatCell(board.getValue(row, col)));
            }

            stringBuilder.append("|    ");

            for (int col = 0; col < boardSideSize; col++) {
                stringBuilder.append("|").append(legendIndex);
                legendIndex++;
            }

            stringBuilder.append("|\n");
        }
        return stringBuilder.toString();
    }

    static String formatCell(Figure value) {
        return value == Figure.EMPTY ? " " : value.name();
    }
}
