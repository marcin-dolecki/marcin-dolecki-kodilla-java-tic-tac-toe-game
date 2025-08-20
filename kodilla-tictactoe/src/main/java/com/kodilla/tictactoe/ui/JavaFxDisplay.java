package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.model.Board;

import java.util.concurrent.CompletableFuture;

public class JavaFxDisplay implements UserInterface {
    private final TicTacToeController controller;
    private CompletableFuture<String> nextInput = new CompletableFuture<>();

    @Override
    public void displayBoard(Board board) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public String getTextInput(String prompt) {
        return "";
    }

    public void provideInput(String value) {
        if (!nextInput.isDone()) {
            nextInput.complete(value);
        }
    }
}
