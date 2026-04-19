/**
 * Student: Mireia Soler Grane
 * Student number: 3238963
*/

package com.ant;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class does multiple things:
 * - initializes the JavaFX application
 * - displays a start menu
 * - allows the user to input the initial number of ants
 * - launches the simulation scene
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // User to input number of ants
        TextField input = new TextField();
        input.setPromptText("Enter number of ants");

        // Button to start the simulation
        Button startButton = new Button("Start Simulation");

        // Ttile container
        VBox menu = new VBox(
                15,
                new Label("Ant Simulation"), // title
                input,
                startButton
        );

        // Center all elements in the window
        menu.setAlignment(Pos.CENTER);

        // Scene for the menu
        Scene menuScene = new Scene(menu, 600, 600);

        startButton.setOnAction(e -> {

            int numAnts;
            // Convert input text to integer
            try {
                numAnts = Integer.parseInt(input.getText());
            } catch (Exception ex) {
                input.setText("Invalid number!");
                return;
            }

            GameController game = new GameController(numAnts);

            // Create new scene for the simulation
            Scene gameScene = new Scene(game.getRoot(), 600, 650);

            // Switch from menu to game
            stage.setScene(gameScene);

            // Start simulation loop
            game.start();
        });

        // Set window title
        stage.setTitle("Ant Simulation");

        // Show menu scene initially
        stage.setScene(menuScene);
        stage.show();
    }

    // launches the JavaFX application.
    public static void main(String[] args) {
        launch();
    }
}