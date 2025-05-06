package com.kodilla.tictactoe.main;
import java.util.Scanner;
import com.kodilla.tictactoe.components.GameLogic;

public class GameMain {
    public static void main(String[] args) throws Exception {
        boolean endTheGame = false;
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!endTheGame) {
            System.out.println("Welcome to TicTacToe Game!");
            GameLogic gameLogic = new GameLogic();
            String lastMoveStatus;
            boolean isGameFinished = false;
            boolean isPlayer1 = true;

            while (!isGameFinished) {
                String player = isPlayer1 ? "Player 1" : "Player 2";
                String symbol = isPlayer1 ? "x" : "o";

                System.out.println(player + " -> " + symbol);
                gameLogic.printBoard();
                System.out.println("Choose your move and press enter to continue");
                input = scanner.nextLine();
                lastMoveStatus = gameLogic.processMove(input, symbol);

                if (lastMoveStatus.equals("Draw")) {
                    System.out.println(player + " -> " + symbol);
                    System.out.println("No one won, draw!");
                    gameLogic.printBoard();
                    isGameFinished = true;
                } else if (lastMoveStatus.equals("Added move")) {
                    isPlayer1 = !isPlayer1;
                } else if (lastMoveStatus.equals("Won")) {
                    System.out.println(player + " -> " + symbol);
                    System.out.println("Congratulations, you won!");
                    gameLogic.printBoard();
                    isGameFinished = true;
                } else {
                    System.out.println(lastMoveStatus);
                }
            }
            System.out.println("Would you like to play again? (y/n)");
            input = scanner.nextLine();
            endTheGame = !input.equalsIgnoreCase("y");
        }
        System.out.println("Bye bye!");
    }
}
