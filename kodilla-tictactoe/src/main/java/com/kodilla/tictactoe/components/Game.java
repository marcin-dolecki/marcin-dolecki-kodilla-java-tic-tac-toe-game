package com.kodilla.tictactoe.components;

public class Game {
    private GameLogic gameLogic;
    private Player player1, player2, currentPlayer;
    private UserInterface ui;
    private boolean againstComputer = false;
    private int boardSideSize, winMoveLength;

    public Game(UserInterface ui) {
        this.ui = ui;
    }

    public void start() {
        while (true) {

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

        while (true) {
            ui.showMessage("(Type 'q' to quit, 'r' to restart)");

            int[] move;
            if (againstComputer && currentPlayer == player2) {
                move = ComputerPlayer.getRandomMove(gameLogic.getBoard(), boardSideSize);
                ui.showMessage("The computer selects " + move[0] + " " + move[1]);
            }
            else {
                String input = ui.getTextInput("Player " + currentPlayer.getFigure().toString() + " - provide row and column number: ");

                if (input.equalsIgnoreCase("q")) {
                    System.exit(0);
                }

                if (input.equalsIgnoreCase("r")) {
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
                        int row = Integer.parseInt(numbers[0]);
                        int col = Integer.parseInt(numbers[1]);
                        move = new int[]{row, col};
                        break;
                }
            }

            LogicReturn isMoveMade = gameLogic.makeMove(row, col, currentPlayer.getFigure());
            switch (isMoveMade) {
                case FIELD_TAKEN:
                    ui.showMessage("The field you selected is already taken. Try again.");
                    continue;
                case MOVE_ADDED:
                    break;
            }

            ui.displayBoard(gameLogic.getBoard());

            
        }
    }

}
