package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.model.DifficultyLevel;
import com.kodilla.tictactoe.model.Figure;

public class ComputerPlayerFactory {
    public static ComputerPlayerInterface create(DifficultyLevel level) {
        return switch (level) {
            case EASY -> new RandomMove();
            case MEDIUM -> new DefensiveMove(Figure.O, Figure.X);
            case HARD -> new OffensiveMove(Figure.O, Figure.X);
        };
    }
}
