package com.kodilla.tictactoe.logic;

public class GameValidationException extends RuntimeException {
    private final ErrorReason reason;

    public GameValidationException(ErrorReason reason) {
        super(reason.toString());
        this.reason = reason;
    }

    public ErrorReason getReason() {
        return reason;
    }
}
