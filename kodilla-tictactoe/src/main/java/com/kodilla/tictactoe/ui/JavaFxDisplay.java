package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.model.Board;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.util.Duration;

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
        if (!shouldDisplay(message)) return;

        String hint = "(Type 'q' to quit, 'r' to restart)";
        boolean showHint = !message.equals("Main menu");

        if (message.contains("Congratulations") || message.contains("Draw")) {
            Platform.runLater(() -> controller.setStatus(message, showHint ? hint : ""));

            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        } else if ("(Do you want to play again? Type 'r' to play, 'q' to quit)".equals(message)) {
            Platform.runLater(() -> controller.setStatus(message, ""));
        } else {
            Platform.runLater(() -> controller.setStatus(message, showHint ? hint : ""));
        }
    }

    @Override
    public String getTextInput(String prompt) {

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
        if ("(Type 'q' to quit, 'r' to restart)".equals(message)) return false;

        if ("1 - Player vs player".equals(message) || "2 - Player vs computer".equals(message)) return false;

        return true;
    }
}
