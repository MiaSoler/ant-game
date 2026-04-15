package com.ant;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {

    //to add the legend in the header
    private BorderPane root = new BorderPane();
    private Pane gamePane = new Pane();

    private Grid grid = new Grid(20, 20);
    private Colony colony = new Colony(5, 10, 10);

    private final int CELL_SIZE = 30;

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

    private HBox createLegend() {

        HBox legend = new HBox(15);
        legend.setStyle("-fx-padding: 10; -fx-background-color: lightgray;");
    
        legend.getChildren().addAll(
                new createLegendItem("Home", "brown"),
                createLegendItem("Food", "green"),
                createLegendItem("Water", "blue"),
                createLegendItem("Poison", "red"),
                createLegendItem("Ant", "black"),
                createLegendItem("Carrying Food", "yellow"),
                createLegendItem("Searching Water", "cyan")
        );
    
        return legend;
    }

    public void start() {
        root.setTop(createLegend());
        root.setCenter(gamePane);
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
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