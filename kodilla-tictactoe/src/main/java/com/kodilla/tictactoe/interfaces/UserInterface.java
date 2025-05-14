package com.kodilla.tictactoe.interfaces;

import com.kodilla.tictactoe.classes.Board;

public interface UserInterface {
    void displayBoard(Board board);
    void showMessage(String message);
    String getTextInput(String prompt);
}
