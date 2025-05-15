package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.model.Board;

public interface UserInterface {
    void displayBoard(Board board);
    void showMessage(String message);
    String getTextInput(String prompt);
}
