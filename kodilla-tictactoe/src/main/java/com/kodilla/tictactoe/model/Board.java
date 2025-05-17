package com.kodilla.tictactoe.model;

import com.kodilla.tictactoe.logic.ErrorReason;
import com.kodilla.tictactoe.logic.GameValidationException;
import static com.kodilla.tictactoe.util.ValidationUtils.validateFigure;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a tic-tac-toe board of any size.
 * The internal representation uses 0-based indices (i.e. [0..size-1]).
 */
public class Board {
    private final Figure[][] board;
    private final int boardSideSize;

    /**
     * Creates an empty board of the given size.
     *
     * @param boardSideSize board side length (e.g. 3 for 3x3)
     */
    public Board(final int boardSideSize) {
        this.boardSideSize = boardSideSize;
        this.board = new Figure[boardSideSize][boardSideSize];

        for (int row = 0; row < boardSideSize; row++) {
            Arrays.fill(board[row], Figure.EMPTY);
        }
    }

    /**
     * Returns the value of the specified field.
     *
     * @param row row index (0-based)
     * @param col column index (0-based)
     * @return field value (X, O or EMPTY)
     */
    public Figure getValue(int row, int col) {
        return board[row][col];
    }

    public int getBoardSideSize() {
        return boardSideSize;
    }

    public void setValue(int row, int col, Figure figure) {
        validateFigure(figure);
        validateCoords(row, col);
        validateEmpty(row, col);
        board[row][col] = figure;
    }

    public boolean isEmpty(int row, int col) {
        return getValue(row, col) == Figure.EMPTY;
    }

    private void validateCoords(int row, int col) {
        if (row < 0 || row >= boardSideSize || col < 0 || col >= boardSideSize) {
            throw new GameValidationException(ErrorReason.OUT_OF_BOUNDS);
        }
    }

    private void validateEmpty(int row, int col) {
        if (!isEmpty(row, col)) {
            throw new GameValidationException(ErrorReason.FIELD_TAKEN);
        }
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
