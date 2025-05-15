package com.kodilla.tictactoe.logic;

import com.kodilla.tictactoe.model.Figure;
import com.kodilla.tictactoe.model.Board;

public class GameLogic {
    private final Board board;
    private final int boardSideSize;
    private final int winMoveLength;
    private int movesMade;

    public GameLogic(int boardSideSize, int winMoveLength) {
        this.boardSideSize = boardSideSize;
        this.winMoveLength = winMoveLength;
        this.board = new Board(boardSideSize);
        this.movesMade = 0;
    }

    public LogicReturn makeMove(int row, int col, Figure figure) {
        if (board.getValue(row, col) != Figure.EMPTY) return LogicReturn.FIELD_TAKEN;
        board.setValue(row, col, figure);
        movesMade++;
        return LogicReturn.MOVE_ADDED;
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

    public Board getBoard() {
        return board;
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

        while (row > 0 && row <= boardSideSize && col > 0 && col <= boardSideSize && board.getValue(row, col) == figure) {
            count++;
            row += rowDirection;
            col += colDirection;
        }
        return count;
    }
}
