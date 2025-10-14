package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.logic.ErrorReason;
import com.kodilla.tictactoe.logic.GameLogic;
import com.kodilla.tictactoe.logic.GameValidationException;
import com.kodilla.tictactoe.model.Board;
import com.kodilla.tictactoe.model.Figure;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.kodilla.tictactoe.util.ValidationUtils.requireNonNull;

public class DefensiveMove implements ComputerPlayerInterface {

    private final Figure playerFigure;
    private final GameLogic gameLogic;

    public DefensiveMove(Figure playerFigure, GameLogic gameLogic) {
        this.playerFigure = playerFigure;
        this.gameLogic = gameLogic;
    }

    @Override
    public int[] getMove(Board board, int boardSideSize) {
        requireNonNull(board, ErrorReason.NULL_BOARD);

        if (board.isFull()) {
            throw new GameValidationException(ErrorReason.BOARD_FULL);
        }

        Optional<int[]> blockingMove = findWinningMove(board, playerFigure, gameLogic);
        if (blockingMove.isPresent()) {
            return blockingMove.get();
        }

        return getRandomMove(board, boardSideSize);
    }

    private Optional<int[]> findWinningMove(Board board, Figure figure, GameLogic gameLogic) {
        int size = board.getBoardSideSize();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board.getValue(row, col) == Figure.EMPTY) {
                    board.setValue(row, col, figure);
                    boolean win = gameLogic.isWin(row, col, figure);
                    board.undoSetValue(row, col);
                    if (win) return Optional.of(new int[]{row, col});
                }
            }
        }
        return Optional.empty();
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
