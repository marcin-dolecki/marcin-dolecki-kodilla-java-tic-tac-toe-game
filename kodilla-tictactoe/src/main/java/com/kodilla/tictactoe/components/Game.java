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

    

}
