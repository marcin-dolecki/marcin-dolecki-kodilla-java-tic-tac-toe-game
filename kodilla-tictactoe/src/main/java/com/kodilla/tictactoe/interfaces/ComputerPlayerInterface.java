package com.kodilla.tictactoe.interfaces;

import com.kodilla.tictactoe.classes.Board;

public interface ComputerPlayerInterface {
    int [] getMove(Board board, int boardSideSize);
}
