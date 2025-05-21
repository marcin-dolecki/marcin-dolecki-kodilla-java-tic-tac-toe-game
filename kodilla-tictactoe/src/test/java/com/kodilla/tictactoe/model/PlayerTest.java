package com.kodilla.tictactoe.model;

import com.kodilla.tictactoe.logic.ErrorReason;
import com.kodilla.tictactoe.logic.GameValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void shouldThrowWhenSettingNullFigure() {
        GameValidationException exception = assertThrows(GameValidationException.class, () ->
            new Player(null)
        );

        assertEquals(ErrorReason.NULL_FIGURE, exception.getReason());
    }

    @Test
    void shouldReturnCorrectFigure() {
        Player player = new Player(Figure.X);

        assertEquals(Figure.X, player.getFigure());
    }

    @Test
    void shouldReturnIsComputerPlayerCorrectly() {
        Player player1 = new Player(Figure.O, true);
        Player player2 = new Player(Figure.O);

        assertTrue(player1.isComputerPlayer());
        assertFalse(player2.isComputerPlayer());
    }

    @Test
    void playersWithSameFiguresAreEqual() {
        Player player1 = new Player(Figure.O);
        Player player2 = new Player(Figure.O);

        assertEquals(player1, player2);
    }

    @Test
    void playersWithSameFiguresWhenOneMarkedIsNotComputerPlayerAreEqual() {
        Player player1 = new Player(Figure.O);
        Player player2 = new Player(Figure.O, false);

        assertEquals(player1, player2);
    }

    @Test
    void playersWithSameFiguresWhenOneIsComputerPlayerAreNotEqual() {
        Player player1 = new Player(Figure.X);
        Player player2 = new Player(Figure.X, true);

        assertNotEquals(player1, player2);
    }

    @Test
    void playersWithDifferentFiguresAreNotEqual() {
        Player player1 = new Player(Figure.X);
        Player player2 = new Player(Figure.O);

        assertNotEquals(player1, player2);
    }
}
