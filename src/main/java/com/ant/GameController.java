package com.ant;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// It connects the simulation logic (Grid + Colony) with the visual representation.
public class GameController {

    // Legend layout
    private BorderPane root = new BorderPane();

    // Draw the grid and ants
    private Pane gamePane = new Pane();

    // Simulation components
    private Grid grid = new Grid(20, 20);
    private Colony colony;

    // Size of each cell in pixels
    private final int CELL_SIZE = 30;

    // Label used to display live statistics
    private Label statsLabel = new Label();
    
    public GameController(int initialAnts) {
        this.colony = new Colony(initialAnts, 10, 10);

        // Add legend (top) and game area (center)
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
         
        return new HBox(5, box, label);
    }

    private VBox createLegend() {

        HBox legend = new HBox(15);
        legend.setStyle("-fx-padding: 10; -fx-background-color: lightgray;");
    
        // First line: color legend
        legend.getChildren().addAll(
                new Label("Ant Simulation |"),
                createLegendItem("Home", "brown"),
                createLegendItem("Food", "green"),
                createLegendItem("Water", "blue"),
                createLegendItem("Poison", "red"),
                createLegendItem("Ant", "black"),
                createLegendItem("Carrying Food", "yellow"),
                createLegendItem("Searching Water", "cyan")
        );

        // Second line: statistics
        statsLabel.setStyle("-fx-padding: 5; -fx-font-size: 13px;");

        return new VBox(legend, statsLabel);
    }

    // Updates the statistics label with real-time data

    private void updateStats() {

        int alive = colony.ants.size();
        int dead = colony.deadAnts;
    
        int searchingFood = colony.countByState(AntState.SEARCHING_FOOD);
        int returning = colony.countByState(AntState.RETURNING_HOME);
        int searchingWater = colony.countByState(AntState.SEARCHING_WATER);
    
        statsLabel.setText(
                "Alive: " + alive + "/2000" +
                " | Dead: " + dead +
                " | Searching Food: " + searchingFood +
                " | Returning Home: " + returning +
                " | Searching Water: " + searchingWater
        );
    }

    //  Updates simulation logic, statistics, renders the scene
    public void start() {

        // Ensure layout is set
        root.setTop(createLegend());
        root.setCenter(gamePane);

        new AnimationTimer() {

            private long lastUpdate = 0;

            // Interval between updates 
            private final long INTERVAL = 200_000_000;
    
            @Override
            public void handle(long now) {

                // Only update when interval is reached
                if (now - lastUpdate >= INTERVAL) {
                    update();
                    updateStats(); 
                    render();
                    lastUpdate = now;
                }
            }
        }.start();
    }

    // Updates the simulation logic
    private void update() {
        colony.update(grid);
    }

    // Renders the grid and ants on screen.
    private void render() {

        // Clear previous frame
        gamePane.getChildren().clear();

        // Draw grid cells
        for (int celX = 0; celX < grid.width; celX++) {
            for (int celY = 0; celY < grid.height; celY++) {

                Rectangle rect = new Rectangle(
                    celX * CELL_SIZE,
                    celY * CELL_SIZE,
                    CELL_SIZE,
                    CELL_SIZE
                );

                // Set color based on cell type
                switch (grid.cells[celX][celY]) {
                    case EMPTY -> rect.setFill(Color.BEIGE);
                    case FOOD -> rect.setFill(Color.GREEN);
                    case WATER -> rect.setFill(Color.BLUE);
                    case POISON -> rect.setFill(Color.RED);
                }

                // Override color if this is the colony home
                if (celX == colony.homeX && celY == colony.homeY) 
                    rect.setFill(Color.BROWN);

                rect.setStroke(Color.BLACK);
                gamePane.getChildren().add(rect);
            }
        }

        // Draw ants
        colony.ants.forEach(ant -> {
            Rectangle rectAnt = new Rectangle(
                    ant.posX * CELL_SIZE,
                    ant.posY * CELL_SIZE,
                    CELL_SIZE,
                    CELL_SIZE
            );

            // Color depends on ant state
            if (ant.state == AntState.RETURNING_HOME)
                rectAnt.setFill(Color.YELLOW);        
            else if (ant.state == AntState.SEARCHING_WATER)
                rectAnt.setFill(Color.CYAN);          
            else
                rectAnt.setFill(Color.BLACK);         

            gamePane.getChildren().add(rectAnt);
        });
    }
}