package com.kodilla.tictactoe.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLogic {
    GameBoard gameBoard = new GameBoard();
    private String lastFigure;
    private List<String> allowedKeys = new ArrayList<>(Arrays.asList("q", "w", "e", "a", "s", "d", "z", "x", "c"));
    private byte counter;

    public GameLogic() {
    }

    public String processMove(String key,String value) {
        if (lastFigure != null && lastFigure.equals(value)) {
            return "Wrong figure";
        } else if (gameBoard.getValue(key) != null) {
            return "Wrong field";
        } else if (!allowedKeys.contains(key)) {
            return "Wrong key";
        } else {
            gameBoard.addMove(key, value);
            lastFigure = value;
            counter++;
            if (counter == 9) {
                return "Game completed";
            }
            return "Added move";
        }
    }

    public void printBoard() {
        gameBoard.printBoard();
    }
}
