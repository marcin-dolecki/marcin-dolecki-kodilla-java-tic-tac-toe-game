package com.kodilla.tictactoe.model;

public enum Figure {
    EMPTY,
    X,
    O;

    public String getStringValue() {
        return this == EMPTY ? " " : name();
    }
}
