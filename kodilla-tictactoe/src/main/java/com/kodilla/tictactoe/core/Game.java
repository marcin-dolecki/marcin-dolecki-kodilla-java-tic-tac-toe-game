package com.kodilla.tictactoe.core;

import com.kodilla.tictactoe.logic.*;
import com.kodilla.tictactoe.model.Board;
import com.kodilla.tictactoe.model.DifficultyLevel;
import com.kodilla.tictactoe.model.Figure;
import com.kodilla.tictactoe.model.Player;
import com.kodilla.tictactoe.ui.ComputerPlayerFactory;
import com.kodilla.tictactoe.ui.ComputerPlayerInterface;
import com.kodilla.tictactoe.ui.UserInterface;

public final class Game {
    private Board board;
    private ComputerPlayerInterface computerPlayerInterface;
    private GameLogic gameLogic;
    private Player player1, player2, currentPlayer;
    private final UserInterface ui;
    private boolean againstComputer = false;
    private int boardSideSize, winMoveLength;
    private boolean directRestart;
    private static final String QUIT = "q";
    private static final String RESTART = "r";

    public Game(UserInterface ui) {
        this.ui = ui;
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
        selectGameMode();
        selectBoardSize();
        board = new Board(boardSideSize);
        gameLogic = new GameLogic(board, winMoveLength);
        if (againstComputer) {
            selectDifficultyLevel();
        }
        initializePlayers();
    }

    private void selectGameMode() {
        while (true) {
            ui.showMessage("Select the game mode:");
            ui.showMessage("1 - Player vs player");
            ui.showMessage("2 - Player vs computer");

            String input = ui.getTextInput("Enter your choice: ");

            switch (input) {
                case "1":
                    againstComputer = false;
                    return;
                case "2":
                    againstComputer = true;
                    return;
                default:
                    ui.showMessage("Invalid choice. Try again.");
            }
        }
    }

    private void selectBoardSize() {
        while (true) {
            ui.showMessage("Select the board size:");
            ui.showMessage("1 - 3x3 square - classic");
            ui.showMessage("2 - 10x10 square - 5 figures win");

            String input = ui.getTextInput("Enter your choice: ");

            switch (input) {
                case "1":
                    boardSideSize = 3;
                    winMoveLength = 3;
                    return;
                case "2":
                    boardSideSize = 10;
                    winMoveLength = 5;
                    return;
                default:
                    ui.showMessage("Invalid choice. Try again.");
            }
        }
    }

    private void selectDifficultyLevel() {
        DifficultyLevel difficultyLevel;

        while (true) {
            ui.showMessage("Select difficulty level:");
            ui.showMessage("1 - Easy");
            ui.showMessage("2 - Medium");
            ui.showMessage("3 - Hard");

            String input = ui.getTextInput("Enter your choice: ");

            switch (input) {
                case "1":
                    difficultyLevel = DifficultyLevel.EASY;
                    computerPlayerInterface = ComputerPlayerFactory.create(difficultyLevel, gameLogic);
                    return;
                case "2":
                    difficultyLevel = DifficultyLevel.MEDIUM;
                    computerPlayerInterface = ComputerPlayerFactory.create(difficultyLevel, gameLogic);
                    return;
                case "3":
                    difficultyLevel = DifficultyLevel.HARD;
                    computerPlayerInterface = ComputerPlayerFactory.create(difficultyLevel, gameLogic);
                    return;
                default:
                    ui.showMessage("Invalid choice. Try again.");
            }
        }
    }

    private void initializePlayers() {
        String nameXInput = ui.getTextInput("Enter your name player X: ");
        String nameX = nameXInput.isEmpty() ? "Player X" : nameXInput;

        String nameOInput = againstComputer ? "Computer" : ui.getTextInput("Enter your name player O: ");
        String nameO = nameOInput.isEmpty() ? "Player O" : nameOInput;

        player1 = new Player(Figure.X, nameX);
        player2 = new Player(Figure.O, nameO, true);
        currentPlayer = player1;
    }

    private void playGame() throws ExitRequestedException {
        ui.showMessage("=== TIC TAC TOE ===");

        while (true) {
            InputAction action = (againstComputer && currentPlayer.isComputerPlayer())
                    ? getComputerAction()
                    : getHumanAction();

            switch (action.getType()) {
                case QUIT:
                    ui.showMessage("Game stopped. See you soon!");
                    throw new ExitRequestedException();
                case RESTART:
                    directRestart = true;
                    return;
                case MOVE:
                    int row = action.getRow();
                    int col = action.getCol();
                    if (!isMoveValidAndMade(row, col)) {
                        continue;
                    }
                    if (isGameOver(row, col)) {
                        return;
                    }
                    switchPlayer();
            }
        }
    }

    private InputAction getHumanAction() {
        while (true) {
            ui.displayBoard(board);
            ui.showMessage("(Type 'q' to quit, 'r' to restart)");

            String input = ui.getTextInput(currentPlayer.getName() + " - provide row and column number: ");

            InputValidationReturn validation = InputValidator.validateInput(input);

            if (input.equalsIgnoreCase(QUIT)) {
                return InputAction.quit();
            }

            if (input.equalsIgnoreCase(RESTART)) {
                return InputAction.restart();
            }

//            InputValidationReturn validation = InputValidator.validateInput(input);

            if (validation == InputValidationReturn.INVALID_PATTERN) {
                ui.showMessage("Invalid pattern. Try again.");
                continue;
            }

            String[] numbers = input.split(" ");
            int row = Integer.parseInt(numbers[0]) - 1;
            int col = Integer.parseInt(numbers[1]) - 1;

            return InputAction.move(row, col);
        }
    }

    private InputAction getComputerAction() {
        ui.displayBoard(board);
        ui.showMessage("(Type 'q' to quit, 'r' to restart)");
        
        int[] move = computerPlayerInterface.getMove(board, boardSideSize);
        ui.showMessage("The computer selects " + move[0] + " " + move[1]);

        return InputAction.move(move[0], move[1]);
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

    private boolean isGameOver(int row, int col) {
        if (gameLogic.isWin(row, col, currentPlayer.getFigure())) {
            ui.displayBoard(board);
            ui.showMessage("Congratulations! " + currentPlayer.getName() + " has won!");
            return true;
        }

        if (gameLogic.isDraw()) {
            ui.displayBoard(board);
            ui.showMessage("Draw! Better luck next time!");
            return true;
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
                case RESTART:
                    return true;
                case QUIT:
                    return false;
                default:
                    ui.showMessage("Invalid choice. Try again.");
            }
        }
    }
}
