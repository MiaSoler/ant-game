package com.ant;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

       // Input field
        TextField input = new TextField();
        input.setPromptText("Enter number of ants");

        Button startButton = new Button("Start Simulation");

        VBox menu = new VBox(15, new Label("Ant Simulation"), input, startButton);
        menu.setAlignment(Pos.CENTER);

        Scene menuScene = new Scene(menu, 600, 600);

        startButton.setOnAction(e -> {
            int numAnts;

            try {
                numAnts = Integer.parseInt(input.getText());
            } catch (Exception ex) {
                input.setText("Invalid number!");
                return;
            }

            // start game
            GameController game = new GameController(numAnts);

            Scene gameScene = new Scene(game.getRoot(), 600, 650);
            stage.setScene(gameScene);

            game.start();
        });

        stage.setTitle("Ant Simulation");
        stage.setScene(menuScene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}