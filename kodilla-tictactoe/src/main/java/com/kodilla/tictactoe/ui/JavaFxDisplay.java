package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.model.Board;
import javafx.application.Platform;
import javafx.scene.control.Alert;

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
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    @Override
    public String getTextInput(String prompt) {
        // In JavaFX prompt is only information. We use buttons.
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
    }

    public void showMainMenu() {
        Platform.runLater(() -> controller.renderMainMenu(this));
    }
}
