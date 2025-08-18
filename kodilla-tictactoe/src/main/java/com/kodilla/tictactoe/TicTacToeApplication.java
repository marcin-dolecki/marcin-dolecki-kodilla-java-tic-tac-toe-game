package com.kodilla.tictactoe;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TicTacToeApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300, Color.BLACK);

        Rectangle rectangle = new Rectangle(25, 25, 250, 250);
        rectangle.setFill(Color.BLUE);

        root.getChildren().add(rectangle);
        stage.setTitle("TicTacToe");
        stage.setScene(scene);
        stage.show();
    }
}
