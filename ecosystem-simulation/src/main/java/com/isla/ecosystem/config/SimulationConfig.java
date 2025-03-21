package com.isla.ecosystem.config;

/**
 * Configuration class containing all simulation parameters.
 * Values are based on the original game rules.
 */
public class SimulationConfig {
    // Time-related configuration
    public static final int MAX_SIMULATION_DURATION = 30;
    public static final int PAUSE_BETWEEN_CYCLES = 1000; // milliseconds

    // Grid dimensions
    public static final int GRID_WIDTH = 30;
    public static final int GRID_HEIGHT = 10;
    public static final int MAX_GRID_SIZE = 1000;

    // Initial populations
    public static final int INITIAL_ANIMAL_COUNT = 3;
    public static final int INITIAL_PLANT_COUNT = 5;

    // Energy configuration
    public static final int INITIAL_ENERGY = 10;
    public static final int MAX_ANIMAL_ENERGY = INITIAL_ENERGY + 1;
    public static final int MAX_PLANT_ENERGY = INITIAL_ENERGY + 4;
    public static final int ENERGY_TRANSFER = 3;

    // Life cycle configuration
    public static final int REPRODUCTIVE_AGE = 1;
    public static final int MAX_AGE = 8;

    // Spawn area configuration
    public static final int SPAWN_AREA_START_X = 2;
    public static final int SPAWN_AREA_START_Y = 1;
    public static final int SPAWN_AREA_END_X = 5;
    public static final int SPAWN_AREA_END_Y = 3;

    // Display configuration
    public static final char EMPTY_CELL = '.';
    public static final char OVERFLOW_SYMBOL = '#';
    public static final char INITIAL_ANIMAL_SYMBOL = 'A';  // Animals will be A, B, C...
    public static final char INITIAL_PLANT_SYMBOL = '*';   // Plants will be *, +, @...

    // Neighborhood types
    public enum NeighborhoodType {
        VON_NEUMANN("Von Neumann"),  // 4 adjacent cells (N, S, E, W)
        MOORE("Moore");             // 8 adjacent cells (N, S, E, W, NE, NW, SE, SW)

        private final String displayName;

        NeighborhoodType(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    private SimulationConfig() {
        // Prevent instantiation
    }
}
