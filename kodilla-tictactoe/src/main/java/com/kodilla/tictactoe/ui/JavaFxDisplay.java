package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.model.Board;
import javafx.application.Platform;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class JavaFxDisplay implements UserInterface {
    private final TicTacToeController controller;
    private CompletableFuture<String> nextInput = new CompletableFuture<>();

    public JavaFxDisplay(TicTacToeController controller) {
        this.controller = controller;
    }

    @Override
    public void displayBoard(Board board) {
        Platform.runLater(() -> controller.renderBoard(board, this));
    }

    @Override
    public void showMessage(String message) {
        final Set<String> VALIDATION_MESSAGES = Set.of(
                "The field you selected is already taken. Try again.",
                "Your selection is out of the range. Try again.",
                "Figure is null. Try again.",
                "Unknown error. Try again."
        );

        if (!shouldDisplay(message)) return;

        String hint = "(Click 'quit' to exit or 'restart' to start again)";

        if (message.contains("Congratulations") || message.contains("Draw")) {
            Platform.runLater(() -> controller.setMessageLabel(message));
            Platform.runLater(() -> controller.setPromptLabel(""));
            Platform.runLater(() -> controller.setHintLabel(hint));

            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        } else if (VALIDATION_MESSAGES.contains(message)) {
            Platform.runLater(() -> controller.setMessageLabel(message));
            Platform.runLater(() -> controller.setHintLabel(hint));

            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        } else if (message.equals("Select the game mode:")) {
            showMainMenu();
            Platform.runLater(() -> controller.setMessageLabel(""));
            Platform.runLater(() -> controller.setPromptLabel(""));
            Platform.runLater(() -> controller.setHintLabel(""));
        } else if (message.equals("Select the board size:")) {
            Platform.runLater(() -> controller.setMessageLabel(""));
            Platform.runLater(() -> controller.setPromptLabel(""));
            Platform.runLater(() -> controller.setHintLabel(""));
        } else if ("(Do you want to play again? Type 'r' to play, 'q' to quit)".equals(message)) {
            String messageFx = "(Do you want to play again? Click 'Restart'. Otherwise, click 'Quit'.)";
            Platform.runLater(() -> controller.setMessageLabel(messageFx));
            Platform.runLater(() -> controller.setPromptLabel(""));
            Platform.runLater(() -> controller.setHintLabel(""));
        } else if (message.contains("The computer selects")) {
            Platform.runLater(() -> controller.setPromptLabel("Computer player's move"));

            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        } else {
            Platform.runLater(() -> controller.setMessageLabel(message));
            Platform.runLater(() -> controller.setPromptLabel(""));
            Platform.runLater(() -> controller.setHintLabel(hint));
        }
    }

    @Override
    public String getTextInput(String prompt) {
        String hint = "(Click 'quit' to exit or 'restart' to start again)";
        String promptFx;

        if (shouldDisplay(prompt)) {
            if (prompt.equals("Player X - provide row and column number: ")) {
                promptFx = "Player X - select a field";
            } else if (prompt.equals("Player O - provide row and column number: ")) {
                promptFx = "Player 0 - select a field";
            } else {
                promptFx = prompt;
            }
            Platform.runLater(() -> controller.setMessageLabel(""));
            Platform.runLater(() -> controller.setPromptLabel(promptFx));
            Platform.runLater(() -> controller.setHintLabel(hint));
        }

        try {
            return nextInput.get();
        } catch (Exception e) {
            return "";
        } finally {
            nextInput = new CompletableFuture<>();
        }
    }

    public void provideInput(String value) {
        if (!nextInput.isDone()) {
            nextInput.complete(value);
        }

        if ("r".equals(value)) {
            Platform.runLater(this::showMainMenu);
        }
    }

    public void showMainMenu() {
        Platform.runLater(() -> controller.renderMainMenu(this));
    }

    private boolean shouldDisplay(String message) {
        final Set<String> BLOCKED_MESSAGES = Set.of(
                "(Type 'q' to quit, 'r' to restart)",
                "1 - Player vs player",
                "2 - Player vs computer",
                "1 - 3x3 square - classic",
                "2 - 10x10 square - 5 figures win",
                "Select difficulty level:",
                "1 - Easy",
                "2 - Medium",
                "3 - Hard",
                "=== TIC TAC TOE ===",
                "Main menu",
                "Enter your choice: "
        );

        if (BLOCKED_MESSAGES.contains(message)) {
            return false;
        }

        return true;
    }
}
