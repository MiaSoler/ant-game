package com.ant;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {

    //to add the legend in the header
    private BorderPane root = new BorderPane();
    private Pane gamePane = new Pane();

    private Grid grid = new Grid(20, 20);
    private Colony colony;

    private final int CELL_SIZE = 30;
    private Label statsLabel = new Label();
    
    public GameController(int initialAnts) {
        this.colony = new Colony(initialAnts, 10, 10);

        root.setTop(createLegend());
        root.setCenter(gamePane);
    }

    public BorderPane getRoot() {
        return root;
    }

    private HBox createLegendItem(String text, String color) {
        Rectangle box = new Rectangle(15, 15);
        box.setStyle("-fx-fill: " + color + ";");
    
        Label label = new Label(text);
         
        HBox item = new HBox(5, box, label);
        return item;
    }
    //create a legend to explain the cell colors
    //add ant status

    private VBox createLegend() {

        HBox legend = new HBox(15);
        legend.setStyle("-fx-padding: 10; -fx-background-color: lightgray;");
    
        legend.getChildren().addAll(
                new Label(), createLegendItem("Home", "brown"),
                createLegendItem("Food", "green"),
                createLegendItem("Water", "blue"),
                createLegendItem("Poison", "red"),
                createLegendItem("Ant", "black"),
                createLegendItem("Carrying Food", "yellow"),
                createLegendItem("Searching Water", "cyan")
        );

            // second line (stats)
        statsLabel.setStyle("-fx-padding: 5; -fx-font-size: 13px;");

        VBox container = new VBox(legend, statsLabel);
        return container;
    }

    private void updateStats() {

        int alive = colony.ants.size();
        int dead = colony.deadAnts;
    
        int searchingFood = colony.countByState(AntState.SEARCHING_FOOD);
        int returning = colony.countByState(AntState.RETURNING_HOME);
        int searchingWater = colony.countByState(AntState.SEARCHING_WATER);
    
        statsLabel.setText(
                "Alive: " + alive +
                " | Dead: " + dead +
                " | Searching Food: " + searchingFood +
                " | Returning Home: " + returning +
                " | Searching Water: " + searchingWater
        );
    }

    public void start() {
        root.setTop(createLegend());
        root.setCenter(gamePane);
        //modify speed animation
        new AnimationTimer() {

            private long lastUpdate = 0;
            private final long INTERVAL = 200_000_000; // 200ms
    
            @Override
            public void handle(long now) {
    
                if (now - lastUpdate >= INTERVAL) {
                    update();
                    updateStats(); 
                    render();
                    lastUpdate = now;
                }
            }
        }.start();
    }

    private void update() {
        colony.update(grid);
    }

    private void render() {
        gamePane.getChildren().clear();

        // draw grid
        for (int celX = 0; celX < grid.width; celX++) {
            for (int celY = 0; celY < grid.height; celY++) {

                Rectangle rect = new Rectangle(
                    celX * CELL_SIZE,
                    celY * CELL_SIZE,
                    CELL_SIZE,
                    CELL_SIZE
                );

                switch (grid.cells[celX][celY]) {
                    case EMPTY -> rect.setFill(Color.BEIGE);
                    case FOOD -> rect.setFill(Color.GREEN);
                    case WATER -> rect.setFill(Color.BLUE);
                    case POISON -> rect.setFill(Color.RED);
                    case HOME -> rect.setFill(Color.BROWN);
                }

                rect.setStroke(Color.BLACK);
                gamePane.getChildren().add(rect);
            }
        }

        // draw ants
        colony.ants.forEach(ant -> {
            Rectangle a = new Rectangle(
                    ant.posX * CELL_SIZE,
                    ant.posY * CELL_SIZE,
                    CELL_SIZE,
                    CELL_SIZE
            );
            //ant color based on and state
            if (ant.state == AntState.RETURNING_HOME)
                a.setFill(Color.YELLOW);
            else if (ant.state == AntState.SEARCHING_WATER)
                a.setFill(Color.CYAN);
            else
                //ant looking for food
                a.setFill(Color.BLACK);

            gamePane.getChildren().add(a);
        });
    }
}