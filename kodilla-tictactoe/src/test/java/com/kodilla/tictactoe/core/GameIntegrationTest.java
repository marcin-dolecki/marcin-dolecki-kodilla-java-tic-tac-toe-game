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
            .thenReturn("1").thenReturn("1") // menu
            .thenReturn("1 1").thenReturn("2 1").thenReturn("1 2").thenReturn("2 2").thenReturn("1 3") // moves
            .thenReturn("q").thenReturn("q"); // end
        injectDeps();
        game.start();
        InOrder o = inOrder(ui);
        o.verify(ui).showMessage("=== TIC TAC TOE ===");
        o.verify(ui, atLeastOnce()).getTextInput(anyString());
        // message after win
        o.verify(ui).showMessage(contains("has won"));
        o.verify(ui).showMessage("Game finished. See you soon!");
    }
}
