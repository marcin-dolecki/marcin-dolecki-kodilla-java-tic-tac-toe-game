package com.kodilla.tictactoe.core;

import com.kodilla.tictactoe.logic.ExitRequestedException;
import com.kodilla.tictactoe.logic.GameLogic;
import com.kodilla.tictactoe.model.Board;
import com.kodilla.tictactoe.ui.ComputerPlayerInterface;
import com.kodilla.tictactoe.ui.UserInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameIntegrationTest {
    @Mock private UserInterface ui;
    @Mock private ComputerPlayerInterface computerPlayerInterface;
    @Spy private Board realBoard = new Board(3);
    @Spy private GameLogic realLogic = new GameLogic(realBoard, 3);
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(ui, computerPlayerInterface);
    }

    private void injectDeps() {
        try {
            // set board
            var bf = Game.class.getDeclaredField("board"); bf.setAccessible(true); bf.set(game, realBoard);
            // set logic
            var lf = Game.class.getDeclaredField("gameLogic"); lf.setAccessible(true); lf.set(game, realLogic);
            // board size and win length
            var sf = Game.class.getDeclaredField("boardSideSize"); sf.setAccessible(true); sf.setInt(game, 3);
            var wf = Game.class.getDeclaredField("winMoveLength"); wf.setAccessible(true); wf.setInt(game, 3);
            // players
            var init = Game.class.getDeclaredMethod("initializePlayers"); init.setAccessible(true); init.invoke(game);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldPlayPvPAndWin() throws ExitRequestedException {
        when(ui.getTextInput(anyString()))
                // menu
                .thenReturn("1").thenReturn("1")
                // moves
                .thenReturn("1 1")
                .thenReturn("2 1")
                .thenReturn("1 2")
                .thenReturn("2 2")
                .thenReturn("1 3")
                // quit after win
                .thenReturn("q");
        injectDeps();

        try {
            game.start();
        } catch (ExitRequestedException e) {
            // unexpected behavior as game should finish without throwing exception
        }

        InOrder o = inOrder(ui);

        // all checks at least in one test to have better overview
        o.verify(ui).showMessage("=== TIC TAC TOE ===");
        o.verify(ui).showMessage("Select the game mode:");
        o.verify(ui).showMessage("1 - Player vs player");
        o.verify(ui).showMessage("2 - Player vs computer");
        o.verify(ui).getTextInput("Enter your choice: ");
        o.verify(ui).showMessage("Select the board size:");
        o.verify(ui).showMessage("1 - 3x3 square - classic");
        o.verify(ui).showMessage("2 - 10x10 square - 5 figures win");
        o.verify(ui).getTextInput("Enter your choice: ");
        o.verify(ui).showMessage("=== TIC TAC TOE ===");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart)");
        o.verify(ui).getTextInput("Player X - provide row and column number: ");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart)");
        o.verify(ui).getTextInput("Player O - provide row and column number: ");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart)");
        o.verify(ui).getTextInput("Player X - provide row and column number: ");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart)");
        o.verify(ui).getTextInput("Player O - provide row and column number: ");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart)");
        o.verify(ui).getTextInput("Player X - provide row and column number: ");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("Congratulations! Player X has won!");
        o.verify(ui).showMessage("(Do you want to play again? Type 'r' to play, 'q' to quit)");
        o.verify(ui).getTextInput("Enter your choice: ");
        o.verify(ui).showMessage("Game finished. See you soon!");
        verifyNoMoreInteractions(ui);
    }

    @Test
    void shouldPlayPvPAndDraw() {
        when(ui.getTextInput(anyString()))
                // menu
                .thenReturn("1").thenReturn("1")
                // moves
                .thenReturn("1 1")
                .thenReturn("1 2")
                .thenReturn("2 1")
                .thenReturn("2 2")
                .thenReturn("3 2")
                .thenReturn("3 1")
                .thenReturn("1 3")
                .thenReturn("2 3")
                .thenReturn("3 3")
                // quit after draw
                .thenReturn("q");

        injectDeps();

        try {
            game.start();
        } catch (ExitRequestedException e) {
            // unexpected behavior as game should finish without throwing exception
        }

        // just one message about draw is enough
        InOrder o = inOrder(ui);
        o.verify(ui).showMessage("Draw! Better luck next time!");
    }

    @Test
    void shouldPlayPvPDoRestartAndQuit() {
        when(ui.getTextInput(anyString()))
                // menu
                .thenReturn("1").thenReturn("1")
                // moves
                .thenReturn("1 1")
                .thenReturn("1 2")
                // restart
                .thenReturn("r")
                // menu
                .thenReturn("1").thenReturn("1")
                // moves
                .thenReturn("1 1")
                .thenReturn("1 2")
                // quit
                .thenReturn("q");

        injectDeps();

        try {
            game.start();
            fail("Expected ExitRequestedException to be thrown");
        } catch (ExitRequestedException e) {
            // expected behaviour - user clicked q
            assertEquals("Exit requested by the user", e.getMessage());
        }

        InOrder o = inOrder(ui);

        // a few checks to verify if restart and quit work properly
        o.verify(ui).showMessage("Select the game mode:");
        o.verify(ui).getTextInput("Player X - provide row and column number: ");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("Select the game mode:");
        o.verify(ui).getTextInput("Player X - provide row and column number: ");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("Game stopped. See you soon!");
    }
}
