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
            if (isTheGameWon()) {
                return "Won";
            }
            if (counter == 9) {
                return "Draw";
            }
            return "Added move";
        }
    }

    public void printBoard() {
        gameBoard.printBoard();
    }

    public boolean isTheGameWon() {
        List<List<String>> combinations = Arrays.asList(
                Arrays.asList("q", "a", "z"),
                Arrays.asList("w", "s", "x"),
                Arrays.asList("e", "d", "c"),
                Arrays.asList("q", "w", "e"),
                Arrays.asList("a", "s", "d"),
                Arrays.asList("z", "x", "c"),
                Arrays.asList("q", "s", "c"),
                Arrays.asList("e", "s", "z")
        );

        for (List<String> keys : combinations) {
            String firstValue = gameBoard.getValue(keys.get(0));
            boolean allEquals = true;

            for (String key : keys) {
                if (gameBoard.getValue(key) == null || !gameBoard.getValue(key).equals(firstValue)) {
                    allEquals = false;
                    break;
                }
            }

            if (allEquals) {
                return true;
            }
        }
        return false;
    }
}
