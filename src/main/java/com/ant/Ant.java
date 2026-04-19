package com.ant;

import java.util.Random;

public class Ant {
    
    // Current position of the ant in the grid
    public int posX, posY;

    // Current ant state 
    public AntState state;

    // Indicates whether the ant is carrying food
    public boolean carryingFood = false;

    // Random generator for movement
    private Random rand = new Random();

    public Ant(int posX, int posY) {
        // Initial position is colony home
        this.posX = posX;
        this.posY = posY;

        // Initial state is searching for food
        this.state = AntState.SEARCHING_FOOD;
    }

    public void update(Grid grid, Colony colony) {

        // FSM logic: behavior depends on current state
        switch (state) {

            //Searching for food
            case SEARCHING_FOOD:
                moveRandom(grid); // move randomly

                // If food is found → pick it up and go home
                if (grid.cells[posX][posY] == CellType.FOOD) {
                    carryingFood = true;
                    state = AntState.RETURNING_HOME;
                }
                break;

            // Returning home with food
            case RETURNING_HOME:
                moveTowards(colony.homeX, colony.homeY); 

                // If reached home:
                if (posX == colony.homeX && posY == colony.homeY) {
                     // drop food
                    carryingFood = false;   
                    // new ant is born   
                    colony.spawnAnt();          
                    // ant becomes thirsty
                    state = AntState.SEARCHING_WATER;
                }
                break;

            // Searching for water
            case SEARCHING_WATER:
                moveRandom(grid); 

                // If water is found go back to searching food
                if (grid.cells[posX][posY] == CellType.WATER) {
                    state = AntState.SEARCHING_FOOD;
                }
                break;
        }

        // ant dies
        if (grid.cells[posX][posY] == CellType.POISON) {
            colony.markForRemoval(this);
        }
    }


    private void moveRandom(Grid grid) {
        // move ant ramdonly  -1, 0, or +1
        int randX = rand.nextInt(3) - 1; 
        int randY = rand.nextInt(3) - 1;

        // Ensure ant stays inside grid
        posX = Math.max(0, Math.min(grid.width - 1, posX + randX));
        posY = Math.max(0, Math.min(grid.height - 1, posY + randY));
    }

  //moves ant cell by cell towards a target
    private void moveTowards(int targetX, int targetY) {
        if (posX < targetX) posX++;
        else if (posX > targetX) posX--;

        if (posY < targetY) posY++;
        else if (posY > targetY) posY--;
    }
}