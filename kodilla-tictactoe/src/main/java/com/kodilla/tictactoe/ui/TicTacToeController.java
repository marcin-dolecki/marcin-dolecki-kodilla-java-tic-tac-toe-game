package com.kodilla.tictactoe.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class TicTacToeController {
    private final BorderPane root;
    private final GridPane grid;

    public TicTacToeController(BorderPane root, GridPane grid) {
        this.root = root;
        this.grid = grid;
    }

    // MAIN MENU
    public void renderMainMenu(JavaFxDisplay ui) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);

        Button pvp = new Button("Player vs Player");
        pvp.setOnAction(event -> ui.provideInput("1"));
    }
}
