package com.ant;

import java.util.ArrayList;
import java.util.List;

// Manages the ant colony

public class Colony {

    // List of all alive ants in the simulation
    public List<Ant> ants = new ArrayList<>();

    // Temporary list storing ants that must be removed (dead)
    private List<Ant> toRemove = new ArrayList<>();

    // Coordinates of the colony (home position)
    public int homeX, homeY;

    // Counter for total number of dead ants
    public int deadAnts = 0;
    
    // Maximum allowed number of ants (prevents performance issues)
    private static final int MAX_ANTS = 2000;

    public Colony(int initialAnts, int homeX, int homeY) {
        // Sets home
        this.homeX = homeX;
        this.homeY = homeY;

        // Create initial ants
        for (int i = 0; i < initialAnts; i++) {
            ants.add(new Ant(homeX, homeY));
        }
    }

    //creates new ants
    public void spawnAnt() {
        if (ants.size() < MAX_ANTS) {
            ants.add(new Ant(homeX, homeY));
        }
    }

    
    // Marks dead ants. Actual removal is done later to avoid modifying the list during iteration.
    public void markForRemoval(Ant ant) {
        toRemove.add(ant);
    }

    public void update(Grid grid) {

        // Create a safe copy of the ants list
        List<Ant> snapshot = new ArrayList<>(ants);

        // Update each ant's behavior
        for (Ant ant : snapshot) {
            ant.update(grid, this);
        }

        // Update death statistics
        deadAnts += toRemove.size();

        // Remove dead ants from the main list
        ants.removeAll(toRemove);

        // Clear temporary removal list
        toRemove.clear();
    }

    // Counts how many ants are currently in a specific state.
    public int countByState(AntState state) {
        return (int) ants.stream()
                .filter(a -> a.state == state)
                .count();
    }
}