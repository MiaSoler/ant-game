package com.ant;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {

    private Pane root = new Pane();

    private Grid grid = new Grid(20, 20);
    private Colony colony = new Colony(5, 10, 10);

    private final int CELL_SIZE = 30;

    public Pane getRoot() {
        return root;
    }

    public void start() {
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
        root.getChildren().clear();

        // draw grid
        for (int x = 0; x < grid.width; x++) {
            for (int y = 0; y < grid.height; y++) {

                Rectangle rect = new Rectangle(
                        x * CELL_SIZE,
                        y * CELL_SIZE,
                        CELL_SIZE,
                        CELL_SIZE
                );

                switch (grid.cells[x][y]) {
                    case EMPTY -> rect.setFill(Color.BEIGE);
                    case FOOD -> rect.setFill(Color.GREEN);
                    case WATER -> rect.setFill(Color.BLUE);
                    case POISON -> rect.setFill(Color.RED);
                    case HOME -> rect.setFill(Color.BROWN);
                }

                rect.setStroke(Color.BLACK);
                root.getChildren().add(rect);
            }
        }

        // draw ants
        colony.ants.forEach(ant -> {
            Rectangle a = new Rectangle(
                    ant.x * CELL_SIZE,
                    ant.y * CELL_SIZE,
                    CELL_SIZE,
                    CELL_SIZE
            );

            if (ant.state == AntState.RETURNING_HOME)
                a.setFill(Color.YELLOW);
            else if (ant.state == AntState.SEARCHING_WATER)
                a.setFill(Color.CYAN);
            else
                a.setFill(Color.BLACK);

            root.getChildren().add(a);
        });
    }
}