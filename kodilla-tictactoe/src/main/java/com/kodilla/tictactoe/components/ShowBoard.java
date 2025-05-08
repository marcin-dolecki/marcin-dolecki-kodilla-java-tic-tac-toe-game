package com.kodilla.tictactoe.components;

public interface ShowBoard {
    static void showBoard(Board board) {
        int boardSideSize = board.getBoardSideSize();
        int key = 1;
        int legendIndex = 1;

        for (int i = 1; i <= boardSideSize; i++) {
            for (int j = 1; j <= boardSideSize; j++) {
                System.out.printf("|%s", formatCell(board.getValue(key)));
                key++;
            }

            System.out.print("|    ");

            for (int j = 1; j <= boardSideSize; j++) {
                System.out.printf("|%d", legendIndex);
                legendIndex++;
            }

            System.out.println("|");
        }
    }

    static String formatCell(Figure value) {
        return value == Figure.EMPTY ? " " : value.name();
    }
}
