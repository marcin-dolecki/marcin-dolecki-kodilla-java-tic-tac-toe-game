package com.kodilla.tictactoe.main;

import com.kodilla.tictactoe.components.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Board board = new Board(10);
//
//        board.setValue(1, 1, Figure.X);
//        board.setValue(1, 2, Figure.O);
//        board.setValue(2, 2, Figure.X);
//        board.setValue(10, 10, Figure.O);

//        board.setValue(55, Figure.X);
//        board.setValue(100, Figure.O);

        Scanner scanner = new Scanner(System.in);



        GameLogic gameLogic = new GameLogic(10, 5);

        gameLogic.makeMove(1, 1, Figure.X);
        gameLogic.makeMove(2, 2, Figure.O);

        System.out.println(ShowBoard.showBoard(gameLogic.getBoard()));
        String input = scanner.nextLine();
        InputValidationReturn validationReturn = InputValidation.inputValidation(input, 10);
        System.out.println(validationReturn);
    }
}
