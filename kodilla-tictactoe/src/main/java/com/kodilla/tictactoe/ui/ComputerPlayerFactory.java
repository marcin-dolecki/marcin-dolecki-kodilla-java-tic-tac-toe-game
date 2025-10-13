package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.model.DifficultyLevel;
import com.kodilla.tictactoe.model.Figure;

public class ComputerPlayerFactory {
    public static ComputerPlayerInterface create(DifficultyLevel level, int winMoveLength) {
        return switch (level) {
            case EASY -> new RandomMove();
            case MEDIUM -> new DefensiveMove(Figure.O, Figure.X, winMoveLength);
            case HARD -> new OffensiveMove(Figure.O, Figure.X, winMoveLength);
        };
    }
}
