package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.model.Board;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TicTacToeController {
    private final BorderPane root;
    private final GridPane grid;

    private final Label messageLabel = new Label("");
    private final Label promptLabel = new Label("");
    private final Label hintLabel = new Label("");

    public TicTacToeController(BorderPane root, GridPane grid) {
        this.root = root;
        this.grid = grid;

        root.setStyle(
                "-fx-background-image: url('static/images/old_paper_2.jpg');" +
                "-fx-background-size: cover;" +
                "-fx-background-position: center center;" +
                "-fx-background-repeat: no-repeat;"
        );

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

        Canvas canvas = new Canvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane boardView = new StackPane(canvas, grid);
        boardView.setAlignment(Pos.CENTER);

        canvas.widthProperty().bind(root.widthProperty().multiply(0.75));
        canvas.heightProperty().bind(root.heightProperty().multiply(0.75));

        canvas.widthProperty().addListener((observable, oldValue, newValue) -> {
            drawBoard(gc, size, canvas.getWidth(), canvas.getHeight());
        });
        canvas.heightProperty().addListener((observable, oldValue, newValue) -> {
            drawBoard(gc, size, canvas.getWidth(), canvas.getHeight());
        });

        // Control panel (restart and quit)
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.TOP_RIGHT);
        controls.setStyle("-fx-padding: 10;");

        Button restart = new Button("Restart");
        restart.setOnAction(event -> ui.provideInput("r"));

        Button quit = new Button("Quit");
        quit.setOnAction(event -> {
            ui.provideInput("q");
            Platform.exit();
        });

        controls.getChildren().addAll(restart, quit);

        root.setTop(controls);
        root.setCenter(boardView);
    }

    private void drawBoard(GraphicsContext gc, int size, double width, double height) {
        gc.clearRect(0, 0, width, height);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        double cellWidth = width / size;
        double cellHeight = height / size;

        for (int i = 1; i < size; i++) {
            double x = i * cellWidth;
            gc.strokeLine(x, 20, x, height - 20);
        }

        for (int i = 1; i < size; i++) {
            double y = i * cellHeight;
            gc.strokeLine(20, y, width - 20, y);
        }
    }
}
