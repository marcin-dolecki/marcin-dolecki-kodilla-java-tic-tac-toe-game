package com.kodilla.tictactoe.classes;

import com.kodilla.tictactoe.interfaces.*;
import com.kodilla.tictactoe.enums.Figure;
import com.kodilla.tictactoe.enums.InputValidationReturn;
import com.kodilla.tictactoe.enums.LogicReturn;

public class Game {
    private GameLogic gameLogic;
    private Player player1, player2, currentPlayer;
    private UserInterface ui;
    private ComputerPlayerInterface cpi;
    private boolean againstComputer = false;
    private int boardSideSize, winMoveLength;
    private boolean directRestart;

    public Game(UserInterface ui, ComputerPlayerInterface cpi) {
        this.ui = ui;
        this.cpi = cpi;
    }

    public void start() {
        while (true) {
            directRestart = false;
            showMainMenu();
            playGame();
            if (directRestart) {
                continue;
            }
            if (!askPlayAgain()) {
                break;
            }
        }
        ui.showMessage("Game finished. See you soon!");
    }

    private void showMainMenu() {
        ui.showMessage("=== TIC TAC TOE ===");
        ui.showMessage("Select the game mode:");
        ui.showMessage("1 - Player vs player");
        ui.showMessage("2 - Player vs computer");

        while (true) {
            String input = ui.getTextInput("Enter your choice: ");
            switch (input) {
                case "1":
                    againstComputer = false;
                    break;
                case "2":
                    againstComputer = true;
                    break;
                default:
                    ui.showMessage("Invalid choice. Try again.");
                    continue;
            }
            break;
        }

        ui.showMessage("Select the board size:");
        ui.showMessage("1 - 3x3 square - classic");
        ui.showMessage("2 - 10x10 square - 5 figures win");

        while (true) {
            String input = ui.getTextInput("Enter your choice: ");
            switch (input) {
                case "1":
                    boardSideSize = 3;
                    winMoveLength = 3;
                    break;
                case "2":
                    boardSideSize = 10;
                    winMoveLength = 5;
                    break;
                default:
                    ui.showMessage("Invalid choice. Try again.");
                    continue;
            }
            break;
        }

        gameLogic = new GameLogic(boardSideSize, winMoveLength);
        player1 = new Player(Figure.X);
        player2 = new Player(Figure.O);
        currentPlayer = player1;
    }

    private void playGame() {
        ui.showMessage("=== TIC TAC TOE ===");
        ui.displayBoard(gameLogic.getBoard());

        int row = 0;
        int col = 0;

        while (true) {
            ui.showMessage("(Type 'q' to quit, 'r' to restart)");

            int[] move;
            if (againstComputer && currentPlayer == player2) {
                move = cpi.getMove(gameLogic.getBoard(), boardSideSize);
                row = move[0];
                col = move[1];
                // change random move to kind of interface
                ui.showMessage("The computer selects " + row + " " + col);
            }
            else {
                String input = ui.getTextInput("Player " + currentPlayer.getFigure().toString() + " - provide row and column number: ");

                if (input.equalsIgnoreCase("q")) {
                    ui.showMessage("Game stopped. See you soon!");
                    System.exit(0);
                }

                if (input.equalsIgnoreCase("r")) {
                    directRestart = true;
                    // try to do something with try catch
                    // maybe would be good to have class responsible for restart
                    return;
                }

                InputValidationReturn isInputValid = InputValidation.inputValidation(input, boardSideSize);
                switch (isInputValid) {
                    case INVALID_PATTERN:
                        ui.showMessage("Invalid pattern. Try again.");
                        continue;
                    case OUT_OF_BOUNDS:
                        ui.showMessage("Your selection is out of the range. Try again.");
                        continue;
                    case OK:
                        String[] numbers = input.split(" ");
                        row = Integer.parseInt(numbers[0]);
                        col = Integer.parseInt(numbers[1]);
                        break;
                }
            }

            LogicReturn isMoveMade = gameLogic.makeMove(row, col, currentPlayer.getFigure());
            switch (isMoveMade) {
                // move the field taking to the input validation but the whole input validation implement in Board
                case FIELD_TAKEN:
                    ui.showMessage("The field you selected is already taken. Try again.");
                    continue;
                case MOVE_ADDED:
                    break;
            }

            ui.displayBoard(gameLogic.getBoard());

            if (gameLogic.checkWin(row, col, currentPlayer.getFigure())) {
                ui.showMessage("Congratulations! Player " + currentPlayer.getFigure().toString() + " has won!");
                break;
            }

            if (gameLogic.isDraw()) {
                ui.showMessage("Draw! Better luck next time!");
                break;
            }

            switchPlayer();
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private boolean askPlayAgain() {
        ui.showMessage("(Do you want to play again? Type 'r' to play, 'q' to quit)");

        while (true) {
            String input = ui.getTextInput("Enter your choice: ");
            switch (input.toLowerCase()) {
                case "r":
                    return true;
                case "q":
                    return false;
                default:
                    ui.showMessage("Invalid choice. Try again.");
            }
        }
    }
}
