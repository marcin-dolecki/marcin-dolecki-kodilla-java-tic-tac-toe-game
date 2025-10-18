package com.kodilla.tictactoe.core;

import com.kodilla.tictactoe.logic.GameLogic;
import com.kodilla.tictactoe.model.Board;
import com.kodilla.tictactoe.model.DifficultyLevel;
import com.kodilla.tictactoe.model.Player;
import com.kodilla.tictactoe.ui.ComputerPlayerInterface;

import java.io.Serializable;

public class GameState implements Serializable {
    private final Board board;
    private final ComputerPlayerInterface computerPlayerInterface;
    private final DifficultyLevel difficultyLevel;
    private final GameLogic gameLogic;
    private final Player player1;
    private final Player player2;
    private final Player currentPlayer;
    private final boolean againstComputer;
    private final int boardSideSize;
    private final int winMoveLength;

    public GameState(Board board, ComputerPlayerInterface computerPlayerInterface, DifficultyLevel difficultyLevel,
                     GameLogic gameLogic, Player player1, Player player2, Player currentPlayer, boolean againstComputer,
                     int boardSideSize, int winMoveLength) {
        this.board = board;
        this.computerPlayerInterface = computerPlayerInterface;
        this.difficultyLevel = difficultyLevel;
        this.gameLogic = gameLogic;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = currentPlayer;
        this.againstComputer = againstComputer;
        this.boardSideSize = boardSideSize;
        this.winMoveLength = winMoveLength;
    }

    public Board getBoard() {
        return board;
    }

    public ComputerPlayerInterface getComputerPlayerInterface() {
        return computerPlayerInterface;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getCurrentPlayer() {
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
}
