package com.kodilla.tictactoe.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        return boardSize == board1.boardSize && Objects.equals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, boardSize);
    }
}
