package com.kodilla.tictactoe.model;

import com.kodilla.tictactoe.logic.ErrorReason;
import com.kodilla.tictactoe.logic.GameValidationException;
import static com.kodilla.tictactoe.util.ValidationUtils.requireNonNull;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a tic-tac-toe board of any size.
 * The internal representation uses 0-based indices (i.e. [0..size-1]).
 */
public final class Board {
    private final Figure[][] board;
    private final int boardSideSize;
    private int moveCount;

    /**
     * Creates an empty board of the given size.
     * @param boardSideSize board side length (e.g. 3 for 3x3)
     */
    public Board(final int boardSideSize) {
        this.boardSideSize = boardSideSize;
        this.board = new Figure[boardSideSize][boardSideSize];
        this.moveCount = 0;

        for (int row = 0; row < boardSideSize; row++) {
            Arrays.fill(board[row], Figure.EMPTY);
        }
    }

    /**
     * Returns the value of the specified field.
     * @param row row index (0-based)
     * @param col column index (0-based)
     * @return field value (X, O or EMPTY)
     */
    public Figure getValue(int row, int col) {
        validateCoords(row, col);
        return board[row][col];
    }

    /**
     * Returns the value of the board side length.
     * @return board side length (e.g. 3 for 3x3)
     */
    public int getBoardSideSize() {
        return boardSideSize;
    }

    /**
     * Sets the value of a field on the board.
     * Validates that the provided figure is legal, the coordinates are within bounds,
     * and the field is currently empty before setting the value.
     * @param row row index (0-based)
     * @param col column index (0-based)
     * @param figure the figure (X or O) assigned to the player
     * @throws GameValidationException if any of the validations fail
     */
    public void setValue(int row, int col, Figure figure) {
        requireNonNull(figure, ErrorReason.NULL_FIGURE);
        validateCoords(row, col);
        validateEmpty(row, col);
        moveCount++;
        board[row][col] = figure;
    }

    /**
     * Undo sets the value of a field on the board.
     * Validates that the coordinates are within bounds
     * @param row row index (0-based)
     * @param col column index (0-based)
     * @throws GameValidationException if the coordinates are out of bounds
     */
    public void undoSetValue(int row, int col) {
        validateCoords(row, col);
        moveCount--;
        board[row][col] = Figure.EMPTY;
    }

    /**
     * Checks whether the specified field on the board is empty.
     * @param row row index (0-based)
     * @param col column index (0-based)
     * @return true if the field is empty, false otherwise
     * @throws GameValidationException if the coordinates are out of bounds
     */
    public boolean isEmpty(int row, int col) {
        validateCoords(row, col);
        return getValue(row, col) == Figure.EMPTY;
    }

    /**
     * Checks whether the board is fully filled.
     * @return true if the board is full, false otherwise
     */
    public boolean isFull() {
        return moveCount == boardSideSize * boardSideSize;
    }

    /**
     * Validates that the given coordinates are within the bounds of the board.
     * @param row row index (0-based)
     * @param col column index (0-based)
     * @throws GameValidationException if the coordinates are out of bounds
     */
    private void validateCoords(int row, int col) {
        if (row < 0 || row >= boardSideSize || col < 0 || col >= boardSideSize) {
            throw new GameValidationException(ErrorReason.OUT_OF_BOUNDS);
        }
    }

    /**
     * Validates that the specified field is currently empty.
     * @param row row index (0-based)
     * @param col column index (0-based)
     * @throws GameValidationException if the field is already occupied
     */
    private void validateEmpty(int row, int col) {
        if (!isEmpty(row, col)) {
            throw new GameValidationException(ErrorReason.FIELD_TAKEN);
        }
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * Two boards are considered equal if they have the same size and
     * their fields contain the same values.
     * @param o the reference object with which to compare
     * @return true if this board is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        return boardSideSize == board1.boardSideSize && Objects.deepEquals(board, board1.board);
    }

    /**
     * Returns a hash code value for the board.
     * @return a hash code value for this board
     */
    @Override
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(board), boardSideSize);
    }
}
