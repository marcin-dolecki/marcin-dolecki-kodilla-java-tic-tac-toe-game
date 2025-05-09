package com.kodilla.tictactoe.components;

public interface ShowBoard {
    static String showBoard(Board board) {
        int boardSideSize = board.getBoardSideSize();
        int key = 1;
        int legendIndex = 1;

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 1; i <= boardSideSize; i++) {
            for (int j = 1; j <= boardSideSize; j++) {
                stringBuilder.append("|").append(formatCell(board.getValue(key)));
                key++;
            }

            stringBuilder.append("|    ");

            for (int j = 1; j <= boardSideSize; j++) {
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
