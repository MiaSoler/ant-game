package com.ant;

import java.util.Random;

// Represents the simulation environment
public class Grid {

    // Dimensions of the grid
    public int width, height;

    // 2D array storing the type of each cell
    public CellType[][] cells;

    // Random generator used to distribute cell types
    private Random rand = new Random();

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;

        // Initialize grid structure
        cells = new CellType[width][height];
        generate();
    }

    // Randomly assigns a type to each cell in the grid.

    private void generate() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                int randomNumber = rand.nextInt(100);

                if (randomNumber < 10)
                    cells[x][y] = CellType.FOOD;

                else if (randomNumber < 15)
                    cells[x][y] = CellType.WATER;

                else if (randomNumber < 18)
                    cells[x][y] = CellType.POISON;

                else
                    cells[x][y] = CellType.EMPTY;
            }
        }
    }
}