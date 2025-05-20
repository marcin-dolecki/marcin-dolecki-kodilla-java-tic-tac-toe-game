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
    void shouldSetValueOnEmptyField() {
        board.setValue(1, 1, Figure.X);

        assertEquals(Figure.X, board.getValue(1, 1));
        assertFalse(board.isEmpty(1, 1));
    }

    @Test
    void shouldThrowWhenSettingValueOnTakenField() {
        board.setValue(0, 0, Figure.X);

        GameValidationException exception =  assertThrows(GameValidationException.class, () ->
                board.setValue(0, 0, Figure.X)
        );

        assertEquals(ErrorReason.FIELD_TAKEN, exception.getReason());
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
}
