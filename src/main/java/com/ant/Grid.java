package com.ant;

import java.util.Random;

public class Grid {

    public int width, height;
    public CellType[][] cells;
    private Random rand = new Random();

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new CellType[width][height];
        generate();
    }

    private void generate() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                int r = rand.nextInt(100);

                if (r < 10) cells[x][y] = CellType.FOOD;
                else if (r < 15) cells[x][y] = CellType.WATER;
                else if (r < 18) cells[x][y] = CellType.POISON;
                else cells[x][y] = CellType.EMPTY;
            }
        }
    }
}