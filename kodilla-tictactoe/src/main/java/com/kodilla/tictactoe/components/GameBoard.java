package com.kodilla.tictactoe.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameBoard {
    private Map<String, String> gameBoard;
    private String lastMove;

    public GameBoard() {
        this.gameBoard = new HashMap<>();

        this.gameBoard.put("q", null);
        this.gameBoard.put("w", null);
        this.gameBoard.put("e", null);
        this.gameBoard.put("a", null);
        this.gameBoard.put("s", null);
        this.gameBoard.put("d", null);
        this.gameBoard.put("z", null);
        this.gameBoard.put("x", null);
        this.gameBoard.put("c", null);
    }

    public void printBoard() {
        System.out.println("* * * * * *");
        System.out.printf("* |%s|%s|%s| *\n", formatCell(this.gameBoard.get("q")), formatCell(this.gameBoard.get("w")), formatCell(this.gameBoard.get("e")));
        System.out.printf("* |%s|%s|%s| *\n", formatCell(this.gameBoard.get("a")), formatCell(this.gameBoard.get("s")), formatCell(this.gameBoard.get("d")));
        System.out.printf("* |%s|%s|%s| *\n", formatCell(this.gameBoard.get("z")), formatCell(this.gameBoard.get("x")), formatCell(this.gameBoard.get("c")));
        System.out.println("* * * * * *");
    }

    private String formatCell(String value) {
        return value == null ? " " : value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameBoard gameBoard1 = (GameBoard) o;
        return Objects.equals(gameBoard, gameBoard1.gameBoard) && Objects.equals(lastMove, gameBoard1.lastMove);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameBoard, lastMove);
    }
}
