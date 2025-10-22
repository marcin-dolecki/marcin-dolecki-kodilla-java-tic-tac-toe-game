package com.kodilla.tictactoe.core;

import com.kodilla.tictactoe.logic.ExitRequestedException;
import com.kodilla.tictactoe.ui.ComputerPlayerInterface;
import com.kodilla.tictactoe.ui.JavaFxDisplay;
import com.kodilla.tictactoe.ui.RandomMove;
import com.kodilla.tictactoe.ui.TicTacToeController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FxApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();

        TicTacToeController controller = new TicTacToeController(root, grid);
        JavaFxDisplay ui = new JavaFxDisplay(controller);
        SaveGameManager saveGameManager = new SaveGameManager();

        Game game = new Game(ui, saveGameManager);

        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.setTitle("Tic Tac Toe");
        stage.show();

        new Thread(() -> {
            try {
                game.start();
            } catch (ExitRequestedException ignored) {
                // do nothing
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
