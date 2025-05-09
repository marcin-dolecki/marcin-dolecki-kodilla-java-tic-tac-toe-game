package com.kodilla.tictactoe.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


}
