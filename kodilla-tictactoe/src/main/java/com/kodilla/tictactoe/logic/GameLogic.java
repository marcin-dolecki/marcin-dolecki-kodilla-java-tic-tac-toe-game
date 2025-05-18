package com.kodilla.tictactoe.logic;

import com.kodilla.tictactoe.model.Figure;
import com.kodilla.tictactoe.model.Board;

import static com.kodilla.tictactoe.util.ValidationUtils.requireNonNull;

public final class GameLogic {
    private final Board board;
    private final int boardSideSize;
    private final int winMoveLength;

    public GameLogic(final Board board, final int winMoveLength) {
        requireNonNull(board, ErrorReason.NULL_BOARD);
        this.board = board;
        this.winMoveLength = winMoveLength;
        this.boardSideSize = board.getBoardSideSize();
    }

    public LogicReturn makeMove(int row, int col, Figure figure) {
        try {
            board.setValue(row, col, figure);
            return LogicReturn.MOVE_ADDED;
        } catch (GameValidationException e) {
            return mapBoardErrorToLogicReturn(e.getReason());
        }
    }

    public boolean isWin(int row, int col, Figure figure) {
        requireNonNull(figure, ErrorReason.NULL_FIGURE);

        return checkDirection(row, col, figure, 0, 1) ||    // horizontally —
                checkDirection(row, col, figure, 1, 0) ||   // vertically |
                checkDirection(row, col, figure, 1, 1) ||   // obliquely \
                checkDirection(row, col, figure, 1, -1);    // obliquely /
    }

    public boolean isDraw() {
        return board.isFull();
    }

    private boolean checkDirection(int row, int col, Figure figure, int rowDirection, int colDirection) {

        int count = 1;
        count += countInDirection(row, col, figure, rowDirection, colDirection);
        count += countInDirection(row, col, figure, -rowDirection, -colDirection);
        return count >= winMoveLength;
    }

    private int countInDirection(int row, int col, Figure figure, int rowDirection, int colDirection) {
        int count = 0;
        row += rowDirection;
        col += colDirection;

        while (row >= 0 && row < boardSideSize && col >= 0 && col < boardSideSize && board.getValue(row, col) == figure) {
            count++;
            row += rowDirection;
            col += colDirection;
        }
        return count;
    }

    private LogicReturn mapBoardErrorToLogicReturn(ErrorReason reason) {
        switch (reason) {
            case NULL_FIGURE:
                return LogicReturn.NULL_FIGURE;
            case FIELD_TAKEN:
                return LogicReturn.FIELD_TAKEN;
            case OUT_OF_BOUNDS:
                return LogicReturn.OUT_OF_BOUNDS;
            default:
                return LogicReturn.UNKNOWN_ERROR;
        }
    }
}
