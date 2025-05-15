package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.model.Board;

public interface ComputerPlayerInterface {
    int [] getMove(Board board, int boardSideSize);
}
