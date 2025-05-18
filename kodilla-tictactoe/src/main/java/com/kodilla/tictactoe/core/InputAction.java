package com.kodilla.tictactoe.core;

public final class InputAction {
    public enum Type {MOVE, RESTART, QUIT}

    private final Type type;
    private final int row;
    private final int col;

    private InputAction(final Type type, final int row, final int col) {
        this.type = type;
        this.row = row;
        this.col = col;
    }

    public static InputAction move(int row, int col) {
        return new InputAction(Type.MOVE, row, col);
    }

    public static InputAction restart() {
        return new InputAction(Type.RESTART, -1, -1);
    }

    public static InputAction quit() {
        return new InputAction(Type.QUIT, -1, -1);
    }

    public Type getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
