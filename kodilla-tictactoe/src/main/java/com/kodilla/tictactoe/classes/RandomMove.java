package com.kodilla.tictactoe.classes;

import com.kodilla.tictactoe.enums.Figure;
import com.kodilla.tictactoe.interfaces.ComputerPlayerInterface;

import java.util.Random;

public class RandomMove implements ComputerPlayerInterface {
    Random random = new Random();

    @Override
    public int[] getMove(Board board, int boardSideSize) {
        int row, col;

        do {
            row = random.nextInt(boardSideSize) + 1;
            col = random.nextInt(boardSideSize) + 1;
        } while (board.getValue(row, col) != Figure.EMPTY);

        return new int[]{row, col};
    }
}
