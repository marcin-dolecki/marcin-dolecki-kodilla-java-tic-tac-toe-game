package com.kodilla.tictactoe.logic;

import com.kodilla.tictactoe.model.Figure;
import com.kodilla.tictactoe.model.Board;

public class GameLogic {
    private final Board board;
    private final int boardSideSize;
    private final int winMoveLength;
    private int movesMade;

    public GameLogic(Board board, int winMoveLength) {
        this.board = board;
        this.winMoveLength = winMoveLength;
        this.boardSideSize = board.getBoardSideSize();
        this.movesMade = 0;
    }

    public LogicReturn makeMove(int row, int col, Figure figure) {
        try {
            board.setValue(row, col, figure);
            movesMade++;
            return LogicReturn.MOVE_ADDED;
        } catch (BoardUpdateException e) {
            return mapBoardErrorToLogicReturn(e.getReason());
        }
    }

    public boolean checkWin(int row, int col, Figure figure) {
        return checkDirection(row, col, figure, 0, 1) ||
                checkDirection(row, col, figure, 1, 0) ||
                checkDirection(row, col, figure, 1, 1) ||
                checkDirection(row, col, figure, 1, -1);
    }

    public boolean isDraw() {
        return movesMade == boardSideSize * boardSideSize;
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

    private LogicReturn mapBoardErrorToLogicReturn(BoardErrorReason reason) {
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
