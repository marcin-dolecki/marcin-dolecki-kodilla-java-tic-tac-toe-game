package com.kodilla.tictactoe.model;

import java.io.Serializable;

public enum Figure implements Serializable {
    EMPTY,
    X,
    O;

    public String getStringValue() {
        return this == EMPTY ? " " : name();
    }
}
