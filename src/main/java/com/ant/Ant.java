package com.ant;

import java.util.Random;

public class Ant {

    public int x, y;
    public AntState state;
    public boolean carryingFood = false;

    private Random rand = new Random();

    public Ant(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = AntState.SEARCHING_FOOD;
    }

    public void update(Grid grid, Colony colony) {

        switch (state) {

            case SEARCHING_FOOD:
                moveRandom(grid);

                if (grid.cells[x][y] == CellType.FOOD) {
                    carryingFood = true;
                    state = AntState.RETURNING_HOME;
                }
                break;

            case RETURNING_HOME:
                moveTowards(colony.homeX, colony.homeY);

                if (x == colony.homeX && y == colony.homeY) {
                    carryingFood = false;
                    colony.spawnAnt();
                    state = AntState.SEARCHING_WATER;
                }
                break;

            case SEARCHING_WATER:
                moveRandom(grid);

                if (grid.cells[x][y] == CellType.WATER) {
                    state = AntState.SEARCHING_FOOD;
                }
                break;
        }

        // 💀 poison kills
        if (grid.cells[x][y] == CellType.POISON) {
            colony.markForRemoval(this);
        }
    }

    private void moveRandom(Grid grid) {
        int dx = rand.nextInt(3) - 1;
        int dy = rand.nextInt(3) - 1;

        x = Math.max(0, Math.min(grid.width - 1, x + dx));
        y = Math.max(0, Math.min(grid.height - 1, y + dy));
    }

    private void moveTowards(int targetX, int targetY) {
        if (x < targetX) x++;
        else if (x > targetX) x--;

        if (y < targetY) y++;
        else if (y > targetY) y--;
    }
}