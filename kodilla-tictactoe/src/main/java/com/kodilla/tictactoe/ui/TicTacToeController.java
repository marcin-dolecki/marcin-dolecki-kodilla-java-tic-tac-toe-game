package com.kodilla.tictactoe.ui;

import com.kodilla.tictactoe.model.Board;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TicTacToeController {
    private final BorderPane root;
    private final GridPane grid;
    private boolean isAgainstComputer = false;

    private final Label messageLabel = new Label("");
    private final Label promptLabel = new Label("");
    private final Label hintLabel = new Label("");

    private final Image xImage = new Image(getClass().getResource("/static/images/x_figure.png").toExternalForm());
    private final Image oImage = new Image(getClass().getResource("/static/images/o_figure.png").toExternalForm());

    private final Background background;

    public TicTacToeController(BorderPane root, GridPane grid) {
        this.root = root;
        this.grid = grid;

        Image bgImg = new Image(getClass().getResource("/static/images/old_paper_2.jpg").toExternalForm());
        BackgroundSize bgSize = new BackgroundSize(
                BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true
        );
        this.background = new Background(new BackgroundImage(
                bgImg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bgSize
        ));

        root.setBackground(background);

        HBox messageBar = new HBox(messageLabel);
        messageBar.setAlignment(Pos.CENTER);
        messageBar.setStyle("-fx-font-size: 20px; -fx-padding: 5; -fx-font-weight: bold;");

        HBox promptBar = new HBox(promptLabel);
        promptBar.setAlignment(Pos.CENTER);
        promptBar.setStyle("-fx-font-size: 20px; -fx-padding: 5; -fx-font-weight: bold;");

        HBox hintBar = new HBox(hintLabel);
        hintBar.setAlignment(Pos.CENTER);
        hintBar.setStyle("-fx-padding: 5;");

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

    // ==================== MAIN MENU =====================
    public void renderMainMenu(JavaFxDisplay ui) {
        renderGameModeMenu(ui);
    }

    private void renderGameModeMenu(JavaFxDisplay ui) {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setBackground(background);

        root.setTop(null);

        Label gameMode = new Label("Select the game mode:");
        gameMode.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-font-weight: bold;");

        Button pvp = createMenuButton("Player vs Player");
        pvp.setOnAction(event -> {
            ui.provideInput("1");
            renderBoardSizeMenu(ui);
        });

        Button pvc = createMenuButton("Player vs Computer");
        pvc.setOnAction(event -> {
            ui.provideInput("2");
            isAgainstComputer = true;
            renderBoardSizeMenu(ui);
        });

        menu.getChildren().addAll(gameMode, pvp, pvc);
        root.setCenter(menu);
    }

    private void renderBoardSizeMenu(JavaFxDisplay ui) {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setBackground(background);

        root.setTop(null);

        Label sizeLabel = new Label("Select the board size:");
        sizeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-font-weight: bold;");

        Button size3 = createMenuButton("Board 3x3");
        size3.setOnAction(event -> {
            ui.provideInput("1");
            if (isAgainstComputer) {
                renderDifficultyLevelMenu(ui);
            }
        });

        Button size10 = createMenuButton("Board 10x10");
        size10.setOnAction(event -> {
            ui.provideInput("2");
            if (isAgainstComputer) {
                renderDifficultyLevelMenu(ui);
            }
        });

        menu.getChildren().addAll(sizeLabel, size3, size10);
        root.setCenter(menu);
    }

    private void renderDifficultyLevelMenu(JavaFxDisplay ui) {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setBackground(background);

        root.setTop(null);

        Label difficultyMode = new Label("Select difficulty level:");
        difficultyMode.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-font-weight: bold;");

        Button easy = createMenuButton("EASY");
        easy.setOnAction(event -> {
            ui.provideInput("1");
        });

        Button medium = createMenuButton("MEDIUM");
        medium.setOnAction(event -> {
            ui.provideInput("2");
        });

        Button hard = createMenuButton("HARD");
        hard.setOnAction(event -> {
            ui.provideInput("3");
        });

        menu.getChildren().addAll(difficultyMode, easy, medium, hard);
        root.setCenter(menu);
    }

    private void renderNameInput(JavaFxDisplay ui) {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setBackground(background);

        root.setTop(null);

        Label prompt = new Label("Enter your name: ");
        prompt.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-font-weight: bold;");

//        Button easy = createMenuButton("EASY");
//        easy.setOnAction(event -> {
//            ui.provideInput("1");
//        });
//
//        Button medium = createMenuButton("MEDIUM");
//        medium.setOnAction(event -> {
//            ui.provideInput("2");
//        });
//
//        Button hard = createMenuButton("HARD");
//        hard.setOnAction(event -> {
//            ui.provideInput("3");
//        });

//        menu.getChildren().addAll(difficultyMode, easy, medium, hard);
//        root.setCenter(menu);
    }

    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 10;" +
                        "-fx-background-color: linear-gradient(to bottom, #f5deb3, #d2b48c);" +
                        "-fx-text-fill: black;");
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 10;" +
                        "-fx-background-color: linear-gradient(to bottom, #e6d0a3, #bfa76f);" +
                        "-fx-text-fill: black; -fx-font-weight: bold;"));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 10;" +
                        "-fx-background-color: linear-gradient(to bottom, #f5deb3, #d2b48c);" +
                        "-fx-text-fill: black;"));
        return btn;
    }

    // ==================== BOARD =====================
    public void renderBoard(Board board, JavaFxDisplay ui) {
        grid.getChildren().clear();
        root.setBackground(background);

        int size = board.getBoardSideSize();

        DoubleBinding boardSizeBinding = Bindings.createDoubleBinding(
                () -> Math.min(root.getWidth(), root.getHeight()) * 0.75,
                root.widthProperty(), root.heightProperty()
        );

        Canvas canvas = new Canvas();
        canvas.widthProperty().bind(boardSizeBinding);
        canvas.heightProperty().bind(boardSizeBinding);

        canvas.widthProperty().addListener((obs, oldVal, newVal) -> drawBoardLines(canvas, size));
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> drawBoardLines(canvas, size));

        drawBoardLines(canvas, size);

        GridPane overlay = new GridPane();
        overlay.setAlignment(Pos.CENTER);
        overlay.maxWidthProperty().bind(canvas.widthProperty());
        overlay.maxHeightProperty().bind(canvas.heightProperty());
        overlay.prefWidthProperty().bind(canvas.widthProperty());
        overlay.prefHeightProperty().bind(canvas.heightProperty());

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Button btn = new Button();
                btn.setStyle("-fx-background-color: transparent;");
                btn.setMinSize(0, 0);
                btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                int r = row + 1, c = col + 1;
                btn.setOnAction(event -> ui.provideInput(r + " " + c));

                String value = board.getValue(row, col).toString();
                if (value.equals("X")) {
                    setButtonImage(btn, xImage);
                } else if (value.equals("O")) {
                    setButtonImage(btn, oImage);
                }

                overlay.add(btn, col, row);
            }
        }

        for (int i = 0; i < size; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / size);
            overlay.getColumnConstraints().add(cc);

            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100.0 / size);
            overlay.getRowConstraints().add(rc);
        }

        StackPane stack = new StackPane(canvas, overlay);
        stack.setAlignment(Pos.CENTER);
        root.setCenter(stack);

        HBox controls = new HBox(15);
        controls.setAlignment(Pos.TOP_RIGHT);
        controls.setStyle("-fx-padding: 10;");

        Button restart = createMenuButton("Restart");
        restart.setOnAction(event -> ui.provideInput("r"));

        Button quit = createMenuButton("Quit");
        quit.setOnAction(event -> {
            ui.provideInput("q");
            Platform.exit();
        });

        controls.getChildren().addAll(restart, quit);
        root.setTop(controls);
    }

    private void drawBoardLines(Canvas canvas, int size) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double canvasSize = Math.min(canvas.getWidth(), canvas.getHeight());
        double cellSize = canvasSize / size;

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(10);

        for (int i = 1; i < size; i++) {
            double x = i * cellSize;
            gc.strokeLine(x, 0, x, canvasSize);
        }

        for (int i = 1; i < size; i++) {
            double y = i * cellSize;
            gc.strokeLine(0, y, canvasSize, y);
        }
    }

    private void setButtonImage(Button btn, Image img) {
        ImageView iv = new ImageView(img);
        iv.setPreserveRatio(true);

        iv.fitWidthProperty().bind(btn.widthProperty().multiply(0.8));
        iv.fitHeightProperty().bind(btn.heightProperty().multiply(0.8));

        btn.setGraphic(iv);
    }
}
