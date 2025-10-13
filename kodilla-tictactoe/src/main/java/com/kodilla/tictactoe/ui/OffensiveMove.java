package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.logic.ErrorReason;
import com.kodilla.tictactoe.logic.GameValidationException;
import com.kodilla.tictactoe.model.Board;
import com.kodilla.tictactoe.model.Figure;

import java.util.concurrent.ThreadLocalRandom;

import static com.kodilla.tictactoe.util.ValidationUtils.requireNonNull;

public class OffensiveMove implements ComputerPlayerInterface {

    private final Figure computerFigure;
    private final Figure playerFigure;

    public OffensiveMove(Figure computerFigure, Figure playerFigure) {
        this.computerFigure = computerFigure;
        this.playerFigure = playerFigure;
    }

    /*
    FOR NOW THE SAME AS RANDOM MOVE
    */

    @Override
    public int[] getMove(Board board, int boardSideSize) {
        requireNonNull(board, ErrorReason.NULL_BOARD);

        if (board.isFull()) {
            throw new GameValidationException(ErrorReason.BOARD_FULL);
        }

        int row, col;
        do {
            row = ThreadLocalRandom.current().nextInt(boardSideSize);
            col = ThreadLocalRandom.current().nextInt(boardSideSize);
        } while (board.getValue(row, col) != Figure.EMPTY);

        return new int[]{row, col};
    }
}
