package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.model.Board;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TicTacToeController {
    private final BorderPane root;
    private final GridPane grid;

    private final Label messageLabel = new Label("");
    private final Label promptLabel = new Label("");
    private final Label hintLabel = new Label("");

    public TicTacToeController(BorderPane root, GridPane grid) {
        this.root = root;
        this.grid = grid;

        HBox messageBar = new HBox(messageLabel);
        messageBar.setAlignment(Pos.CENTER);
        messageBar.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 5;");

        HBox promptBar = new HBox(promptLabel);
        promptBar.setAlignment(Pos.CENTER);
        promptBar.setStyle("-fx-background-color: #e0e0ff; -fx-padding: 5;");

        HBox hintBar = new HBox(hintLabel);
        hintBar.setAlignment(Pos.CENTER);
        hintBar.setStyle("-fx-background-color: #ffe0e0; -fx-padding: 5;");

        VBox statusBars = new VBox(5, messageBar, promptBar, hintBar);
        statusBars.setAlignment(Pos.CENTER);

        root.setBottom(statusBars);
    }

    public void setMessageLabel(String message) {
        messageLabel.setText(message);
    }

    public void setPromptLabel(String prompt) {
        promptLabel.setText(prompt);
    }

    public void setHintLabel(String hint) {
        hintLabel.setText(hint);
    }

    // MAIN MENU
    public void renderMainMenu(JavaFxDisplay ui) {
        renderGameModeMenu(ui);
    }

    private void renderGameModeMenu(JavaFxDisplay ui) {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);

        Label gameMode = new Label("Select the game mode:");
        Button pvp = new Button("Player vs Player");
        pvp.setOnAction(event -> {
            ui.provideInput("1");
            renderBoardSizeMenu(ui);
        });

        Button pvc = new Button("Player vs Computer");
        pvc.setOnAction(event -> {
            ui.provideInput("2");
            renderBoardSizeMenu(ui);
        });

        menu.getChildren().addAll(gameMode, pvp, pvc);
        root.setCenter(menu);
    }

    private void renderBoardSizeMenu(JavaFxDisplay ui) {
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);

        Label sizeLabel = new Label("Select the board size:");
        Button size3 = new Button("Board 3x3");
        size3.setOnAction(event -> ui.provideInput("1"));

        Button size10 = new Button("Board 10x10");
        size10.setOnAction(event -> ui.provideInput("2"));

        menu.getChildren().addAll(sizeLabel, size3, size10);
        root.setCenter(menu);
    }

    // BOARD
    public void renderBoard(Board board, JavaFxDisplay ui) {
        grid.getChildren().clear();
        int size = board.getBoardSideSize();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                String value = board.getValue(row, col).toString();
                Button btn = new Button(value.equals("EMPTY") ? " " : value);
                btn.setMinSize(50, 50);

                int r = row + 1, c = col + 1;
                btn.setOnAction(event -> ui.provideInput(r + " " + c));

                grid.add(btn, col, row);
            }
        }

        // Control panel (restart and quit)
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);

        Button restart = new Button("Restart");
        restart.setOnAction(event -> ui.provideInput("r"));

        Button quit = new Button("Quit");
        quit.setOnAction(event -> {
            ui.provideInput("q");
            Platform.exit();
        });

        controls.getChildren().addAll(restart, quit);

        VBox wrapper = new VBox(15, grid, controls);
        wrapper.setAlignment(Pos.CENTER);

        root.setCenter(wrapper);
    }
}
