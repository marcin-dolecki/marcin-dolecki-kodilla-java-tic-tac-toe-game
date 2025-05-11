package com.kodilla.tictactoe.components;

public interface UserInterface {
    void displayBoard(Board board);
    void showMessage(String message);
    String getTextInput(String prompt);
}
