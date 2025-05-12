package com.kodilla.tictactoe.main;

import com.kodilla.tictactoe.components.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new ConsoleInterface();
        Game game = new Game(ui);
        game.start();
    }
}
