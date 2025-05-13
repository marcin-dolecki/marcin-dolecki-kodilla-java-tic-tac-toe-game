package com.kodilla.tictactoe.components;
import java.util.Random;

public interface ComputerPlayer {
    static int[] getRandomMove(Board board, int boardSideSize) {
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(boardSideSize) + 1;
            col = random.nextInt(boardSideSize) + 1;
        } while (board.getValue(row, col) != Figure.EMPTY);
        return new int[]{row, col};
    }
}
