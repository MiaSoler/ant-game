package com.ant;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        //initiate game controller
        GameController game = new GameController();

        //set front-end
        Scene scene = new Scene(game.getRoot(), 600, 600);

        stage.setTitle("Ant Simulation");
        stage.setScene(scene);
        stage.show();

        game.start();
    }

    public static void main(String[] args) {
        launch();
    }
}