package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.logic.ErrorReason;
import com.kodilla.tictactoe.logic.GameValidationException;
import com.kodilla.tictactoe.model.Figure;
import com.kodilla.tictactoe.model.Board;

import java.util.concurrent.ThreadLocalRandom;

import static com.kodilla.tictactoe.util.ValidationUtils.requireNonNull;

public class RandomMove implements ComputerPlayerInterface {

    @Override
    public int[] getMove(Board board, int boardSideSize) {
        requireNonNull(board, ErrorReason.NULL_BOARD);

        if (board.isFull()) {
            throw new GameValidationException(ErrorReason.BOARD_FULL);
        }

        return getRandomMove(board, boardSideSize);
    }

    private int[] getRandomMove(Board board, int boardSideSize) {
        int row, col;
        do {
            row = ThreadLocalRandom.current().nextInt(boardSideSize);
            col = ThreadLocalRandom.current().nextInt(boardSideSize);
        } while (board.getValue(row, col) != Figure.EMPTY);

        return new int[]{row, col};
    }
}
