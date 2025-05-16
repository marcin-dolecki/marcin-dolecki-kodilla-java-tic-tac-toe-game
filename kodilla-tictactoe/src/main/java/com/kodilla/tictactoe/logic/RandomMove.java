package com.kodilla.tictactoe.logic;

import com.kodilla.tictactoe.model.Figure;
import com.kodilla.tictactoe.ui.ComputerPlayerInterface;
import com.kodilla.tictactoe.model.Board;

import java.util.Random;

public class RandomMove implements ComputerPlayerInterface {
    Random random = new Random();

    @Override
    public int[] getMove(Board board, int boardSideSize) {
        int row, col;

        do {
            row = random.nextInt(boardSideSize);
            col = random.nextInt(boardSideSize);
        } while (board.getValue(row, col) != Figure.EMPTY);

        return new int[]{row, col};
    }
}
