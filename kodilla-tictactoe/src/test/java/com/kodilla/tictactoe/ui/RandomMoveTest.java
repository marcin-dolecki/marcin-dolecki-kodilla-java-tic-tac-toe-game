package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.logic.ErrorReason;
import com.kodilla.tictactoe.logic.GameValidationException;
import com.kodilla.tictactoe.model.Board;
import com.kodilla.tictactoe.model.Figure;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RandomMoveTest {
    private final RandomMove randomMove = new RandomMove();

    @Test
    void shouldThrowExceptionWhenBoardIsNull() {
        assertThatThrownBy(() -> randomMove.getMove(null, 3))
            .isInstanceOf(GameValidationException.class)
            .extracting("reason")
            .isEqualTo(ErrorReason.NULL_BOARD);
    }

    @Test
    void shouldThrownExceptionWhenBoardIsFull() {
        Board mockBoard = mock(Board.class);
        when(mockBoard.isFull()).thenReturn(true);

        assertThatThrownBy(() -> randomMove.getMove(mockBoard, 3))
            .isInstanceOf(GameValidationException.class)
            .extracting("reason")
            .isEqualTo(ErrorReason.BOARD_FULL);
    }

    @Test
    void shouldReturnValidEmptyField() {
        Board mockBoard = mock(Board.class);
        when(mockBoard.isFull()).thenReturn(false);
        when(mockBoard.getValue(anyInt(), anyInt())).thenAnswer(invocation -> {
           int row = invocation.getArgument(0);
           int col = invocation.getArgument(1);
           return (row == 1 & col == 1) ? Figure.EMPTY : Figure.X;
        });

        int[] move = randomMove.getMove(mockBoard, 3);

        assertThat(move).containsExactly(1, 1);
    }
}
