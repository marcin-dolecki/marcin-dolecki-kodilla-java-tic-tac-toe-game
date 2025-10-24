package com.kodilla.tictactoe.core;

import com.kodilla.tictactoe.logic.ExitRequestedException;
import com.kodilla.tictactoe.logic.GameLogic;
import com.kodilla.tictactoe.logic.GameValidationException;
import com.kodilla.tictactoe.model.Board;
import com.kodilla.tictactoe.ui.ComputerPlayerInterface;
import com.kodilla.tictactoe.ui.UserInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameIntegrationTest {
    @Mock private UserInterface ui;
    @Mock private ComputerPlayerInterface computerPlayerInterface;
    @Mock private SaveGameManager saveGameManager;
    @Spy private Board realBoard = new Board(3);
    @Spy private GameLogic realLogic = new GameLogic(realBoard, 3);
    private AtomicInteger counter;
    private Game game;
    private InOrder o;

    @BeforeEach
    void setUp() {
        game = new Game(ui, saveGameManager);
        counter = new AtomicInteger(0);
        o = inOrder(ui);

        doAnswer(invocation -> {
            String msg = invocation.getArgument(0, String.class);
            System.out.println("[GAME MESSAGE] " + msg);
            return null;
        }).when(ui).showMessage(anyString());
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void whenTextInputThenAnswer(List<String> answers) {
        when(ui.getTextInput(anyString())).thenAnswer(invocation -> {
            String prompt = invocation.getArgument(0, String.class);
            int i = counter.getAndIncrement();
            String answer = i < answers.size() ? answers.get(i) : "q";
            System.out.println("[MOCK INPUT REQUEST] " + prompt + " -> " + answer);
            return answer;
        });
    }

    private void runGame() {
        injectDeps();

        try {
            game.start();
        } catch (ExitRequestedException e) {
            // unexpected behavior as game should finish without throwing exception
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void shouldPlayPvPAndWin(boolean hasSave) throws ExitRequestedException {
        when(saveGameManager.loadGame()).thenReturn(hasSave ? Optional.of(mock(GameState.class)) : Optional.empty());

        List<String> answers = new ArrayList<>();
        if (hasSave) {
            answers.add("n");
        }
        answers.add("1");
        answers.add("1");
        answers.add("Marcin");
        answers.add("Radek");
        answers.add("1 1");
        answers.add("2 1");
        answers.add("1 2");
        answers.add("2 2");
        answers.add("1 3");
        answers.add("q");

        whenTextInputThenAnswer(answers);

        runGame();

        // all checks at least in one test to have better overview
        if (hasSave) {
            o.verify(ui).showMessage("Game save found. Would you like to load it?");
            o.verify(ui).getTextInput("Enter 'y' (yes) or 'n' (no): ");
        }
        o.verify(ui).showMessage("=== TIC TAC TOE ===");
        o.verify(ui).showMessage("Select the game mode:");
        o.verify(ui).showMessage("1 - Player vs player");
        o.verify(ui).showMessage("2 - Player vs computer");
        o.verify(ui).showMessage("3 - Show scores");
        o.verify(ui).getTextInput("Enter your choice: ");
        o.verify(ui).showMessage("Select the board size:");
        o.verify(ui).showMessage("1 - 3x3 square - classic");
        o.verify(ui).showMessage("2 - 10x10 square - 5 figures win");
        o.verify(ui).getTextInput("Enter your choice: ");
        o.verify(ui).getTextInput("Enter your name player X: ");
        o.verify(ui).getTextInput("Enter your name player O: ");
        o.verify(ui).showMessage("=== TIC TAC TOE ===");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart, 's' to save)");
        o.verify(ui).getTextInput("Marcin - provide row and column number: ");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart, 's' to save)");
        o.verify(ui).getTextInput("Radek - provide row and column number: ");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart, 's' to save)");
        o.verify(ui).getTextInput("Marcin - provide row and column number: ");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart, 's' to save)");
        o.verify(ui).getTextInput("Radek - provide row and column number: ");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart, 's' to save)");
        o.verify(ui).getTextInput("Marcin - provide row and column number: ");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("Congratulations! Marcin has won!");
        o.verify(ui).showMessage("(Do you want to play again? Type 'r' to play, 'q' to quit)");
        o.verify(ui).getTextInput("Enter your choice: ");
        o.verify(ui).showMessage("Game finished. See you soon!");
        verifyNoMoreInteractions(ui);
    }

    @Test
    void shouldPlayPvPWinRestartAndQuit() throws ExitRequestedException {
        when(saveGameManager.loadGame()).thenReturn(Optional.empty());

        List<String> answers = new ArrayList<>();
        // menu
        answers.add("1");
        answers.add("1");
        answers.add("");
        answers.add("");
        // moves
        answers.add("1 1");
        answers.add("2 1");
        answers.add("1 2");
        answers.add("2 2");
        answers.add("1 3");
        // restart + menu
        answers.add("r");
        answers.add("1");
        answers.add("1");
        answers.add("");
        answers.add("");
        // moves
        answers.add("1 1");
        answers.add("2 1");
        // quit

        whenTextInputThenAnswer(answers);

        runGame();

        // a few checks to verify if won, restart and quit work properly
        o.verify(ui).showMessage("Select the game mode:");
        o.verify(ui).showMessage("Congratulations! Player X has won!");
        o.verify(ui).showMessage("Select the game mode:");
        o.verify(ui).showMessage("Game stopped. See you soon!");
    }

    @Test
    void shouldPlayPvPAndDraw() throws ExitRequestedException{
        when(saveGameManager.loadGame()).thenReturn(Optional.empty());

        List<String> answers = new ArrayList<>();

        answers.addAll(List.of(
                "1", "1", "", "", // menu
                "1 1", "1 2", "2 1", "2 2", "3 2", "3 1", "1 3", "2 3", "3 3", // moves
                "q" // quit
        ));

        whenTextInputThenAnswer(answers);

        runGame();

        // just one message about draw is enough
        o.verify(ui).showMessage("Draw! Better luck next time!");
    }

    @Test
    void shouldPlayPvPDrawRestartAndQuit() throws ExitRequestedException {
        when(saveGameManager.loadGame()).thenReturn(Optional.empty());

        List<String> answers = new ArrayList<>();

        answers.addAll(List.of(
                "1", "1", "", "", // menu
                "1 1", "1 2", "2 1", "2 2", "3 2", "3 1", "1 3", "2 3", "3 3", // moves
                "r", "1", "1", "", "", // restart + menu
                "1 1", "2 1", // moves
                "q" // quit
        ));

        whenTextInputThenAnswer(answers);

        runGame();

        // a few checks to verify if won, restart and quit work properly
        o.verify(ui).showMessage("Select the game mode:");
        o.verify(ui).showMessage("Draw! Better luck next time!");
        o.verify(ui).showMessage("Select the game mode:");
        o.verify(ui).showMessage("Game stopped. See you soon!");
    }

    @Test
    void shouldPlayPvPDoRestartAndQuit() throws ExitRequestedException {
        when(saveGameManager.loadGame()).thenReturn(Optional.empty());

        List<String> answers = new ArrayList<>();

        answers.addAll(List.of(
                "1", "1", "", "", // menu
                "1 1", "1 2", // moves
                "r", "1", "1", "", "", // restart + menu
                "1 1", "1 2", // moves
                "q" // quit
        ));

        whenTextInputThenAnswer(answers);

        runGame();

        // a few checks to verify if restart and quit work properly
        o.verify(ui).showMessage("Select the game mode:");
        o.verify(ui).getTextInput("Player X - provide row and column number: ");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("Select the game mode:");
        o.verify(ui).getTextInput("Player X - provide row and column number: ");
        o.verify(ui).displayBoard(any(Board.class));
        o.verify(ui).showMessage("Game stopped. See you soon!");
    }

//    @Test
//    void shouldPlayPvPFieldTakenOutOfBoundsInvalidPattern() throws GameValidationException {
//        when(ui.getTextInput(anyString())).thenReturn(
//                "1","1", // menu
//                "1 1", "1 2", // moves
//                "1 1", // fields taken
//                "1 3", // move
//                "1 5", // out of bounds
//                "k", // invalid pattern
//                null // null - game is finished as NULL_INPUT error is thrown
//        );
//
//        injectDeps();
//
//        try {
//            game.start();
//            fail("Expected GameValidationException to be thrown");
//        } catch (GameValidationException e) {
//            assertEquals("NULL_INPUT", e.getMessage());
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        InOrder o = inOrder(ui);
//
//        // just one message about draw is enough
//        o.verify(ui).showMessage("The field you selected is already taken. Try again.");
//        o.verify(ui).showMessage("Your selection is out of the range. Try again.");
//        o.verify(ui).showMessage("Invalid pattern. Try again.");
//    }
//
//    @Test
//    void shouldPlayPvCAndWin() throws ExitRequestedException {
//        when(ui.getTextInput(anyString())).thenReturn(
//                "2","1", // menu
//                "1 1", "1 2", "1 3", // moves
//                "q" // quit after win
//        );
//
//        when(computerPlayerInterface.getMove(any(), eq(3))).thenReturn(
//                new int[]{2,1}, new int[]{2,2} // moves
//        );
//
//        injectDeps();
//
//        try {
//            game.start();
//        } catch (ExitRequestedException e) {
//            // unexpected behavior as game should finish without throwing exception
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        InOrder o = inOrder(ui);
//
//        // all checks to have better overview
//        o.verify(ui).showMessage("=== TIC TAC TOE ===");
//        o.verify(ui).showMessage("Select the game mode:");
//        o.verify(ui).showMessage("1 - Player vs player");
//        o.verify(ui).showMessage("2 - Player vs computer");
//        o.verify(ui).getTextInput("Enter your choice: ");
//        o.verify(ui).showMessage("Select the board size:");
//        o.verify(ui).showMessage("1 - 3x3 square - classic");
//        o.verify(ui).showMessage("2 - 10x10 square - 5 figures win");
//        o.verify(ui).getTextInput("Enter your choice: ");
//        o.verify(ui).showMessage("=== TIC TAC TOE ===");
//        o.verify(ui).displayBoard(any(Board.class));
//        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart, 's' to save)");
//        o.verify(ui).getTextInput("Player X - provide row and column number: ");
//        o.verify(ui).displayBoard(any(Board.class));
//        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart, 's' to save)");
//        o.verify(ui).showMessage("The computer selects 2 1");
//        o.verify(ui).displayBoard(any(Board.class));
//        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart, 's' to save)");
//        o.verify(ui).getTextInput("Player X - provide row and column number: ");
//        o.verify(ui).displayBoard(any(Board.class));
//        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart, 's' to save)");
//        o.verify(ui).showMessage("The computer selects 2 2");
//        o.verify(ui).displayBoard(any(Board.class));
//        o.verify(ui).showMessage("(Type 'q' to quit, 'r' to restart, 's' to save)");
//        o.verify(ui).getTextInput("Player X - provide row and column number: ");
//        o.verify(ui).displayBoard(any(Board.class));
//        o.verify(ui).showMessage("Congratulations! Player X has won!");
//        o.verify(ui).showMessage("(Do you want to play again? Type 'r' to play, 'q' to quit)");
//        o.verify(ui).getTextInput("Enter your choice: ");
//        o.verify(ui).showMessage("Game finished. See you soon!");
//        verifyNoMoreInteractions(ui);
//    }
}
