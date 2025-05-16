package com.kodilla.tictactoe.logic;

public class BoardUpdateException extends RuntimeException {
    private final BoardErrorReason reason;

    public BoardUpdateException(BoardErrorReason reason) {
        super(reason.toString());
        this.reason = reason;
    }

    public BoardErrorReason getReason() {
        return reason;
    }
}
