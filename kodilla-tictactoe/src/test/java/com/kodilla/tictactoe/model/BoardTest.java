package com.kodilla.tictactoe.model;

import com.kodilla.tictactoe.logic.ErrorReason;
import com.kodilla.tictactoe.logic.GameValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private final int boardSideSize = 3;

    @BeforeEach
    void setUp() {
        board = new Board(boardSideSize);
    }

    @Test
    void shouldInitializeBoardWithEmptyFields() {
        IntStream.range(0, boardSideSize).forEach(row ->
            IntStream.range(0, boardSideSize).forEach(col ->
                assertEquals(Figure.EMPTY, board.getValue(row, col))
            )
        );
    }

    @Test
    void shouldThrowWhenSettingNullFigure() {
        GameValidationException exception = assertThrows(GameValidationException.class, () ->
                board.setValue(1, 1, null)
        );

        assertEquals(ErrorReason.NULL_FIGURE, exception.getReason());
    }

    @ParameterizedTest
    @CsvSource({
        "-1, 0",    // row below min
        "0, -1",    // col below min
        "3, 0",     // row above max
        "0, 3",     // col above max
    })
    void shouldThrowWhenCoordinatesAreOutOfBounds(int row, int col) {
        GameValidationException exception = assertThrows(GameValidationException.class, () ->
                board.setValue(row, col, Figure.X)
        );

        assertEquals(ErrorReason.OUT_OF_BOUNDS, exception.getReason());
    }

    @Test
    void shouldThrowWhenSettingValueOnTakenField() {
        board.setValue(0, 0, Figure.X);

        GameValidationException exception =  assertThrows(GameValidationException.class, () ->
                board.setValue(0, 0, Figure.X)
        );

        assertEquals(ErrorReason.FIELD_TAKEN, exception.getReason());
    }

    @Test
    void shouldSetValueOnEmptyField() {
        board.setValue(1, 1, Figure.X);

        assertEquals(Figure.X, board.getValue(1, 1));
        assertFalse(board.isEmpty(1, 1));
    }

    @Test
    void shouldValidateEmptyCorrectly() {
        assertTrue(board.isEmpty(1, 1));

        board.setValue(1, 1, Figure.X);

        assertFalse(board.isEmpty(1, 1));
    }

    @Test
    void shouldReturnTrueWhenBoardIsFull() {
        IntStream.range(0, boardSideSize).forEach(row ->
            IntStream.range(0, boardSideSize).forEach(col ->
                board.setValue(row, col, Figure.X)
            )
        );

        assertTrue(board.isFull());
    }

    @Test
    void shouldReturnFalseWhenBoardIsNotFull() {
        board.setValue(0, 0, Figure.X);

        assertFalse(board.isFull());
    }

    @Test
    void boardsWithSameContentsShouldBeEqual() {
        Board board1 = new Board(boardSideSize);
        Board board2 = new Board(boardSideSize);

        board1.setValue(0, 0, Figure.X);
        board2.setValue(0, 0, Figure.X);

        assertEquals(board1, board2);
    }

    @Test
    void boardsWithDifferentContentsShouldNotBeEqual() {
        Board board1 = new Board(boardSideSize);
        Board board2 = new Board(boardSideSize);

        board1.setValue(0, 0, Figure.X);
        board2.setValue(1, 1, Figure.X);

        assertNotEquals(board1, board2);
    }
}
