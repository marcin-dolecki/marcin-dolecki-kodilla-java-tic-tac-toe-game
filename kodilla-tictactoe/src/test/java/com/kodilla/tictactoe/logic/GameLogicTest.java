package com.kodilla.tictactoe.logic;

import com.kodilla.tictactoe.model.Board;
import com.kodilla.tictactoe.model.Figure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameLogicTest {
    private Board mockBoard;
    private GameLogic gameLogic;

    @BeforeEach
    void setUp() {
        mockBoard = mock(Board.class);
        when(mockBoard.getBoardSideSize()).thenReturn(3);
        gameLogic = new GameLogic(mockBoard, 3);
    }

    @Test
    void shouldReturnMoveAddedWhenSetValueSucceeds() {
        LogicReturn result = gameLogic.makeMove(0, 0, Figure.X);
        verify(mockBoard).setValue(0, 0, Figure.X);
        assertThat(result).isEqualTo(LogicReturn.MOVE_ADDED);
    }

    @Test
    void shouldReturnFieldTakenWhenSetValueThrows() {
        doThrow(new GameValidationException(ErrorReason.FIELD_TAKEN))
            .when(mockBoard).setValue(1, 1, Figure.X);

        LogicReturn result = gameLogic.makeMove(1, 1, Figure.X);

        assertThat(result).isEqualTo(LogicReturn.FIELD_TAKEN);
    }

    @Test
    void shouldThrowWhenFigureIsNull() {
        assertThatThrownBy(() -> gameLogic.isWin(1, 1, null))
            .isInstanceOf(GameValidationException.class)
            .extracting("reason")
            .isEqualTo(ErrorReason.NULL_FIGURE);
    }

    @Test
    void shouldReturnFalseIfNotEnoughSymbolsToWin() {
        when(mockBoard.getBoardSideSize()).thenReturn(3);
        when(mockBoard.getValue(anyInt(), anyInt())).thenReturn(Figure.EMPTY);

        GameLogic gameLogic = new GameLogic(mockBoard, 3);

        assertThat(gameLogic.isWin(1, 1, Figure.X)).isFalse();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("winScenarios")
    void shouldDetectWinInAllDirections(String description, int row, int col, Figure figure, BoardSetup setup) {
        setup.apply(mockBoard);
        GameLogic gameLogic = new GameLogic(mockBoard, 3);
        assertThat(gameLogic.isWin(row, col, figure)).isTrue();
    }

    static Stream<Object[]> winScenarios() {
        return Stream.of(
            new Object[]{"Horizontal win", 1, 1, Figure.X, (BoardSetup) board ->
                when(board.getValue(anyInt(), anyInt())).thenAnswer(invocation -> {
                    int row = invocation.getArgument(0);
                    int col = invocation.getArgument(1);
                    return (row == 1 && col >= 0 && col < 3) ? Figure.X : Figure.EMPTY;
                })
            },
            new Object[]{"Vertical win", 1, 2, Figure.X, (BoardSetup) board ->
                when(board.getValue(anyInt(), anyInt())).thenAnswer(invocation -> {
                    int row = invocation.getArgument(0);
                    int col = invocation.getArgument(1);
                    return (col == 2 && row >= 0 && row < 3) ? Figure.X : Figure.EMPTY;
                })
            },
            new Object[]{"Diagonal \\ win", 1, 1, Figure.X, (BoardSetup) board ->
                when(board.getValue(anyInt(), anyInt())).thenAnswer(invocation -> {
                    int row = invocation.getArgument(0);
                    int col = invocation.getArgument(1);
                    return (row == col) ? Figure.X : Figure.EMPTY;
                })
            },
            new Object[]{"Diagonal / win", 1, 1, Figure.X, (BoardSetup) board ->
                when(board.getValue(anyInt(), anyInt())).thenAnswer(invocation -> {
                    int row = invocation.getArgument(0);
                    int col = invocation.getArgument(1);
                    return (row + col == 2) ? Figure.X : Figure.EMPTY;
                })
            }
        );
    }

    @Test
    void shouldReturnTrueIfBoardIsFull() {
        when(mockBoard.isFull()).thenReturn(true);
        assertThat(gameLogic.isDraw()).isTrue();
    }

    @Test
    void shouldReturnFalseIfBoardIsNotFull() {
        when(mockBoard.isFull()).thenReturn(false);
        assertThat(gameLogic.isDraw()).isFalse();
    }

    @FunctionalInterface
    interface BoardSetup {
        void apply(Board board);
    }
}
