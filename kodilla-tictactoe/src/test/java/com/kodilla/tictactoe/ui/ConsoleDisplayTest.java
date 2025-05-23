package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.logic.ErrorReason;
import com.kodilla.tictactoe.logic.GameValidationException;
import com.kodilla.tictactoe.model.Board;
import com.kodilla.tictactoe.model.Figure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ConsoleDisplayTest {
    private final InputStream originalIn = System.in;

    @AfterEach
    void restoreSystemInput() {
        System.setIn(originalIn);
    }

    @Test
    void shouldThrowExceptionWhenBoardIsNull() {
        ConsoleDisplay consoleDisplay = new ConsoleDisplay();
        GameValidationException exception = assertThrows(
                GameValidationException.class, () -> consoleDisplay.displayBoard(null)
        );
        assertEquals(ErrorReason.NULL_BOARD, exception.getReason());
    }

    @Test
    void shouldThrowExceptionWhenMessageIsNull() {
        ConsoleDisplay consoleDisplay = new ConsoleDisplay();
        GameValidationException exception = assertThrows(
                GameValidationException.class, () -> consoleDisplay.showMessage(null)
        );
        assertEquals(ErrorReason.NULL_MESSAGE, exception.getReason());
    }

    @Test
    void shouldThrowExceptionWhenPromptIsNull() {
        ConsoleDisplay consoleDisplay = new ConsoleDisplay();
        GameValidationException exception = assertThrows(
                GameValidationException.class, () -> consoleDisplay.getTextInput(null)
        );
        assertEquals(ErrorReason.NULL_PROMPT, exception.getReason());
    }

    @Test
    void shouldReturnUserInput() {
        String input = "user input";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ConsoleDisplay consoleDisplay = new ConsoleDisplay();
        String result = consoleDisplay.getTextInput("Enter your input:");
        assertEquals(input, result);
    }

    @Test
    void shouldDisplayBoardWithoutThrowingException() {
        Board mockBoard = Mockito.mock(Board.class);
        when(mockBoard.getBoardSideSize()).thenReturn(3);
        when(mockBoard.getValue(anyInt(), anyInt())).thenReturn(Figure.EMPTY);

        ConsoleDisplay consoleDisplay = new ConsoleDisplay();

        assertDoesNotThrow(() -> consoleDisplay.displayBoard(mockBoard));
    }

    @Test
    void shouldCallGetValueForEveryCellOnDisplayBoard() {
        Board mockBoard = Mockito.mock(Board.class);
        when(mockBoard.getBoardSideSize()).thenReturn(3);
        when(mockBoard.getValue(anyInt(), anyInt())).thenReturn(Figure.EMPTY);

        ConsoleDisplay consoleDisplay = new ConsoleDisplay();
        consoleDisplay.displayBoard(mockBoard);

        verify(mockBoard, times(3 * 3)).getValue(anyInt(), anyInt());
    }
}
