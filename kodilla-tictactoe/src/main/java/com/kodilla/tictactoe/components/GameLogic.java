package com.kodilla.tictactoe.components;

public class GameLogic {
    GameBoard gameBoard = new GameBoard();
    private String lastFigure;
    private byte counter;


    public GameLogic() {
    }

    public String processMove(String key,String value) {
        if (lastFigure != null && lastFigure.equals(value)) {
            return "Wrong figure";
        } else if (gameBoard.getValue(key) != null) {
            return "Wrong field";
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
