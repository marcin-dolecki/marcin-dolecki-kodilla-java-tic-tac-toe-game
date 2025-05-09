package com.kodilla.tictactoe.components;

import java.util.Arrays;
import java.util.Objects;

public class Board {
    private final Figure[][] board;
    private final int boardSideSize;

    public Board(final int boardSideSize) {
        this.boardSideSize = boardSideSize;
        this.board = new Figure[boardSideSize][boardSideSize];

        for (int row = 0; row < boardSideSize; row++) {
            for (int col = 0; col < boardSideSize; col++) {
                board[row][col] = Figure.EMPTY;
            }
        }
    }

    public Figure getValue(int row, int col) {
        return board[row][col];
    }

    public int getBoardSideSize() {
        return boardSideSize;
    }

    public void setValue(int row, int col, Figure value) {
        board[row-1][col-1] = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        return boardSideSize == board1.boardSideSize && Objects.deepEquals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(board), boardSideSize);
    }
}
