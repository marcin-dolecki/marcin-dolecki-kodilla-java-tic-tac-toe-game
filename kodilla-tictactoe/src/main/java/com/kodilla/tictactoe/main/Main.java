package com.kodilla.tictactoe.main;

import com.kodilla.tictactoe.components.Board;
import com.kodilla.tictactoe.components.Figure;
import com.kodilla.tictactoe.components.ShowBoard;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(3);

        board.setValue(1, 1, Figure.X);
        board.setValue(1, 2, Figure.O);
        board.setValue(2, 2, Figure.X);

//        board.setValue(55, Figure.X);
//        board.setValue(100, Figure.O);

        System.out.println(ShowBoard.showBoard(board));
    }
}
