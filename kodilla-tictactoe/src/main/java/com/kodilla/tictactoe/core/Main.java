package com.kodilla.tictactoe.core;

import com.kodilla.tictactoe.ui.ComputerPlayerInterface;
import com.kodilla.tictactoe.ui.ConsoleDisplay;
import com.kodilla.tictactoe.logic.RandomMove;
import com.kodilla.tictactoe.ui.UserInterface;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new ConsoleDisplay();
        ComputerPlayerInterface cpi = new RandomMove();
        Game game = new Game(ui, cpi);
        game.start();
    }
}
