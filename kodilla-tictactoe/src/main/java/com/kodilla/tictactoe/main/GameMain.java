package com.kodilla.tictactoe.main;

import com.kodilla.tictactoe.components.GameBoard;
import com.kodilla.tictactoe.components.GameLogic;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class GameMain {
    public static void main(String[] args) throws Exception{
//        GameLogic gameLogic = new GameLogic();
//        String lastMoveStatus;
//
//        lastMoveStatus = gameLogic.processMove("q","x");
//        //            System.out.println("Now it's the other player's turn.");
//        //            System.out.println("This field is already taken.");
//        System.out.println(lastMoveStatus);
//        lastMoveStatus = gameLogic.processMove("s","o");
//        System.out.println(lastMoveStatus);
//        lastMoveStatus = gameLogic.processMove("d","o");
//        System.out.println(lastMoveStatus);
//        lastMoveStatus = gameLogic.processMove("a","x");
//        System.out.println(lastMoveStatus);
//        lastMoveStatus = gameLogic.processMove("a","o");
//        System.out.println(lastMoveStatus);
//        gameLogic.printBoard();

        Terminal terminal = TerminalBuilder.terminal();
        System.out.println("Naciśnij dowolny klawisz. Wciśnij 'q', aby wyjść.");

        while (true) {
            int ch = terminal.reader().read(); // odczyt pojedynczego znaku

            if (ch == -1) continue;

            System.out.println("Naciśnięto: " + (char) ch);

            if (ch == 'q') break;
        }

        terminal.close();
    }
}
