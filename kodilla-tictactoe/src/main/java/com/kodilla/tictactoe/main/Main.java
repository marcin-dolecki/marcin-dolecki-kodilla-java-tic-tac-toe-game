package com.kodilla.tictactoe.main;

import com.kodilla.tictactoe.classes.ConsoleDisplay;
import com.kodilla.tictactoe.classes.Game;
import com.kodilla.tictactoe.classes.RandomMove;
import com.kodilla.tictactoe.interfaces.*;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new ConsoleDisplay();
        ComputerPlayerInterface cpi = new RandomMove();
        Game game = new Game(ui, cpi);
        game.start();
    }
}
