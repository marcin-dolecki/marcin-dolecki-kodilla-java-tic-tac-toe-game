package com.kodilla.tictactoe.core;

import com.kodilla.tictactoe.logic.GameLogic;
import com.kodilla.tictactoe.logic.InputValidator;
import com.kodilla.tictactoe.model.Board;
import com.kodilla.tictactoe.model.Figure;
import com.kodilla.tictactoe.logic.InputValidationReturn;
import com.kodilla.tictactoe.logic.LogicReturn;
import com.kodilla.tictactoe.model.Player;
import com.kodilla.tictactoe.ui.ComputerPlayerInterface;
import com.kodilla.tictactoe.ui.UserInterface;

public class Game {
    private Board board;
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

        while (true) {
            ui.showMessage("Select the game mode:");
            ui.showMessage("1 - Player vs player");
            ui.showMessage("2 - Player vs computer");

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

        while (true) {
            ui.showMessage("Select the board size:");
            ui.showMessage("1 - 3x3 square - classic");
            ui.showMessage("2 - 10x10 square - 5 figures win");

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

        board = new Board(boardSideSize);
        gameLogic = new GameLogic(board, winMoveLength);
        player1 = new Player(Figure.X);
        player2 = new Player(Figure.O, true);
        currentPlayer = player1;
    }

    private void playGame() {
        ui.showMessage("=== TIC TAC TOE ===");

        int row = 0;
        int col = 0;

        while (true) {
            ui.displayBoard(board);
            ui.showMessage("(Type 'q' to quit, 'r' to restart)");

            int[] move;
            if (againstComputer && currentPlayer.isComputerPlayer()) {
                move = cpi.getMove(board, boardSideSize);
                row = move[0];
                col = move[1];
                ui.showMessage("The computer selects " + row + " " + col);
            } else {
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

                InputValidationReturn isInputValid = InputValidator.validateInput(input);

                switch (isInputValid) {
                    case INVALID_PATTERN:
                        ui.showMessage("Invalid pattern. Try again.");
                        continue;
                    case OK:
                        String[] numbers = input.split(" ");
                        row = Integer.parseInt(numbers[0]) - 1;
                        col = Integer.parseInt(numbers[1]) - 1;
                        break;
                }
            }

            LogicReturn isMoveMade = gameLogic.makeMove(row, col, currentPlayer.getFigure());

            switch (isMoveMade) {
                case NULL_FIGURE:
                    ui.showMessage("Figure is null. Try again.");
                    continue;
                case FIELD_TAKEN:
                    ui.showMessage("The field you selected is already taken. Try again.");
                    continue;
                case OUT_OF_BOUNDS:
                    ui.showMessage("Your selection is out of the range. Try again.");
                    continue;
                case UNKNOWN_ERROR:
                    ui.showMessage("Unknown error. Try again.");
                    continue;
                case MOVE_ADDED:
                    break;
            }

            if (gameLogic.isWin(row, col, currentPlayer.getFigure())) {
                ui.displayBoard(board);
                ui.showMessage("Congratulations! Player " + currentPlayer.getFigure().toString() + " has won!");
                break;
            }

            if (gameLogic.isDraw()) {
                ui.displayBoard(board);
                ui.showMessage("Draw! Better luck next time!");
                break;
            }

            switchPlayer();
        }
    }

    private int[] handleHumanMove() {
        while (true) {
            String input = ui.getTextInput("Player " + currentPlayer.getFigure().toString() + " - provide row and column number: ");

            if (input.equalsIgnoreCase("q")) {
                ui.showMessage("Game stopped. See you soon!");
                System.exit(0);
            }

            if (input.equalsIgnoreCase("r")) {
                directRestart = true;
                // try to do something with try catch
                // maybe would be good to have class responsible for restart
                return null;
            }

            InputValidationReturn validation = InputValidator.validateInput(input);

            if (validation == InputValidationReturn.INVALID_PATTERN) {
                ui.showMessage("Invalid pattern. Try again.");
                continue;
            }

            String[] numbers = input.split(" ");
            int row = Integer.parseInt(numbers[0]) - 1;
            int col = Integer.parseInt(numbers[1]) - 1;

            return new int[]{row, col};
        }
    }

    private int[] handleComputerMove() {
        int[] move = cpi.getMove(board, boardSideSize);
        ui.showMessage("The computer selects " + move[0] + " " + move[1]);

        return move;
    }

    private boolean isMoveValidAndMade(int row, int col) {
        LogicReturn result = gameLogic.makeMove(row, col, currentPlayer.getFigure());

        switch (result) {
            case MOVE_ADDED:
                return true;
            case FIELD_TAKEN:
                ui.showMessage("The field you selected is already taken. Try again.");
                break;
            case OUT_OF_BOUNDS:
                ui.showMessage("Your selection is out of the range. Try again.");
                break;
            case NULL_FIGURE:
                ui.showMessage("Figure is null. Try again.");
                break;
            case UNKNOWN_ERROR:
                ui.showMessage("Unknown error. Try again.");
                break;
        }
        return false;
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
