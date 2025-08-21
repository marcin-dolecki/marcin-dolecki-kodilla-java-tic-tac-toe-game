package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.model.Board;
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
        
    }
}
