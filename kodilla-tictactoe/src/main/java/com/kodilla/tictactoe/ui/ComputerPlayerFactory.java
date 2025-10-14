package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.logic.GameLogic;
import com.kodilla.tictactoe.model.DifficultyLevel;
import com.kodilla.tictactoe.model.Figure;

public class ComputerPlayerFactory {
    public static ComputerPlayerInterface create(DifficultyLevel level, GameLogic gameLogic) {
        return switch (level) {
            case EASY -> new RandomMove();
            case MEDIUM -> new DefensiveMove(Figure.X, gameLogic);
            case HARD -> new OffensiveMove(Figure.O, Figure.X, gameLogic);
        };
    }
}
