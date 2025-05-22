package com.kodilla.tictactoe.core;

import com.kodilla.tictactoe.logic.ExitRequestedException;
import com.kodilla.tictactoe.ui.ComputerPlayerInterface;
import com.kodilla.tictactoe.ui.ConsoleDisplay;
import com.kodilla.tictactoe.ui.RandomMove;
import com.kodilla.tictactoe.ui.UserInterface;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new ConsoleDisplay();
        ComputerPlayerInterface computerPlayerInterface = new RandomMove();
        Game game = new Game(ui, computerPlayerInterface);

        try {
            game.start();
        } catch (ExitRequestedException ignored) {
            // user requested exit - nothing to do
        } finally {
            ui.shutdown();
        }
    }
}
