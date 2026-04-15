package com.ant;

import java.util.Random;

public class Ant {
    
    public int posX, posY;
    public AntState state;
    public boolean carryingFood = false;

    private Random rand = new Random();

    public Ant(int posX, int posY) {
        //initial position in grid
        this.posX = posX;
        this.posY = posY;
        //initial state
        this.state = AntState.SEARCHING_FOOD;
    }

    public void update(Grid grid, Colony colony) {

        switch (state) {

            case SEARCHING_FOOD:
                moveRandom(grid);

                if (grid.cells[posX][posY] == CellType.FOOD) {
                    carryingFood = true;
                    state = AntState.RETURNING_HOME;
                }
                break;

            case RETURNING_HOME:
                moveTowards(colony.homeX, colony.homeY);

                if (posX == colony.homeX && posY == colony.homeY) {
                    carryingFood = false;
                    colony.spawnAnt();
                    //once ant is at home -> and becomes thirsty
                    state = AntState.SEARCHING_WATER;
                }
                break;

            case SEARCHING_WATER:
                moveRandom(grid);
                //After drinking water, ant looks for food again
                if (grid.cells[posX][posY] == CellType.WATER) {
                    state = AntState.SEARCHING_FOOD;
                }
                break;
        }

        // 💀 poison kills
        if (grid.cells[posX][posY] == CellType.POISON) {
            colony.markForRemoval(this);
        }
    }
    //ants move random when they look for food and water
    private void moveRandom(Grid grid) {
        int randX = rand.nextInt(3) - 1;
        int randY = rand.nextInt(3) - 1;

        posX = Math.max(0, Math.min(grid.width - 1, posX + randX));
        posY = Math.max(0, Math.min(grid.height - 1, posY + randY));
    }
    //ants move towards home when they found food
    private void moveTowards(int targetX, int targetY) {
        if (posX < targetX) posX++;
        else if (posX > targetX) posX--;

        if (posY < targetY) posY++;
        else if (posY > targetY) posY--;
    }
}