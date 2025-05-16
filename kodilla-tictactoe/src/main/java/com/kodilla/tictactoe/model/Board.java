package com.kodilla.tictactoe.model;

import com.kodilla.tictactoe.logic.BoardErrorReason;
import com.kodilla.tictactoe.logic.BoardUpdateException;

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
        return board[row-1][col-1];
    }

    public int getBoardSideSize() {
        return boardSideSize;
    }

    public void setValue(int row, int col, Figure value) {
        if (row < 1 || row > boardSideSize || col < 1 || col > boardSideSize) {
            throw new BoardUpdateException(BoardErrorReason.OUT_OF_BOUNDS);
        }
        if (!isEmpty(row, col)) {
            throw new BoardUpdateException(BoardErrorReason.FIELD_TAKEN);
        }
        board[row-1][col-1] = value;
    }

    public boolean isEmpty(int row, int col) {
        return getValue(row, col) == Figure.EMPTY;
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
