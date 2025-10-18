package com.kodilla.tictactoe.core;

import com.kodilla.tictactoe.model.Board;
import com.kodilla.tictactoe.model.DifficultyLevel;
import com.kodilla.tictactoe.model.Figure;

import java.io.Serializable;

public class GameState implements Serializable {
    private final Board board;
    private final Figure currentPlayer;
    private final boolean againstComputer;
    private final int boardSideSize;
    private final int winMoveLength;
    private final DifficultyLevel difficultyLevel;

    public GameState(Board board, Figure currentPlayer, boolean againstComputer, int boardSideSize, int winMoveLength, DifficultyLevel difficultyLevel) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.againstComputer = againstComputer;
        this.boardSideSize = boardSideSize;
        this.winMoveLength = winMoveLength;
        this.difficultyLevel = difficultyLevel;
    }

    public Board getBoard() {
        return board;
    }

    public Figure getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isAgainstComputer() {
        return againstComputer;
    }

    public int getBoardSideSize() {
        return boardSideSize;
    }

    public int getWinMoveLength() {
        return winMoveLength;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }
}
