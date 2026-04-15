package com.ant;

import java.util.ArrayList;
import java.util.List;

public class Colony {

    // alive ants
    public List<Ant> ants = new ArrayList<>();
    // dead ants
    private List<Ant> toRemove = new ArrayList<>();

    public int homeX, homeY;

    public Colony(int initialAnts, int homeX, int homeY) {
        this.homeX = homeX;
        this.homeY = homeY;

        for (int i = 0; i < initialAnts; i++) {
            ants.add(new Ant(homeX, homeY));
        }
    }
    //create enw ant
    public void spawnAnt() {
        ants.add(new Ant(homeX, homeY));
    }
    //delete dead ants
    public void markForRemoval(Ant ant) {
        toRemove.add(ant);
    }
    //update grid with ant status
    public void update(Grid grid) {
        for (Ant ant : ants) {
            ant.update(grid, this);
        }

        ants.removeAll(toRemove);
        toRemove.clear();
    }
}