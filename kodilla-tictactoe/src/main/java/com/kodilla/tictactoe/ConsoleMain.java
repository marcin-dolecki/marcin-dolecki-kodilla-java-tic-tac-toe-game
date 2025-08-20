package com.kodilla.tictactoe;

import com.kodilla.tictactoe.core.Game;
import com.kodilla.tictactoe.logic.ExitRequestedException;
import com.kodilla.tictactoe.ui.ComputerPlayerInterface;
import com.kodilla.tictactoe.ui.ConsoleDisplay;
import com.kodilla.tictactoe.ui.RandomMove;
import com.kodilla.tictactoe.ui.UserInterface;

public class ConsoleMain {
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
