package com.kodilla.tictactoe.logic;

public class ExitRequestedException extends RuntimeException {
    public ExitRequestedException() {
        super("Exit requested by the user");
    }
}
