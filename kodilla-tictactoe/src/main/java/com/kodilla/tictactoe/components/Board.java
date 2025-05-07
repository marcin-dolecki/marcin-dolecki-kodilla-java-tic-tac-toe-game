package com.kodilla.tictactoe.components;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Integer, Figure> board;
    private final int boardSize;

    public Board(final int boardSideSize) {
        this.boardSize = boardSideSize * boardSideSize;
        this.board = new HashMap<>();

        for (int i = 1; i < boardSize; i++) {
            board.put(i, Figure.EMPTY);
        }
    }

    public Figure getValue(int key) {
        return board.get(key);
    }

    public void setValue(int key, Figure value) {
        board.put(key, value);
    }
}
