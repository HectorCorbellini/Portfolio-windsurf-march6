package com.isla.ecosystem.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration class containing all simulation parameters.
 * Values are based on the original game rules but can be modified at runtime.
 */
public class SimulationConfig {
    private static final Logger logger = LoggerFactory.getLogger(SimulationConfig.class);
    
    // Time-related configuration (non-configurable)
    public static final int MAX_SIMULATION_DURATION = 30;
    public static final int PAUSE_BETWEEN_CYCLES = 1000; // milliseconds

    // Grid dimensions (non-configurable)
    public static final int GRID_WIDTH = 30;
    public static final int GRID_HEIGHT = 10;
    public static final int MAX_GRID_SIZE = 1000;

    // Initial populations (now configurable)
    private static int initialAnimalCount = 6;
    private static int initialPlantCount = 10;
    
    // Ranges for initial populations
    public static final int MIN_ANIMAL_COUNT = 1;
    public static final int MAX_ANIMAL_COUNT = 20;
    public static final int MIN_PLANT_COUNT = 1;
    public static final int MAX_PLANT_COUNT = 30;

    // Default values for configurable parameters
    private static final int DEFAULT_INITIAL_ENERGY = 10;
    private static final int DEFAULT_MAX_ANIMAL_ENERGY = DEFAULT_INITIAL_ENERGY + 1;
    private static final int DEFAULT_MAX_PLANT_ENERGY = DEFAULT_INITIAL_ENERGY + 4;
    private static final int DEFAULT_ENERGY_TRANSFER = 3;
    private static final int DEFAULT_REPRODUCTIVE_AGE = 1;
    private static final int DEFAULT_MAX_AGE = 8;
    
    // Parameter ranges
    public static final int MIN_INITIAL_ENERGY = 5;
    public static final int MAX_INITIAL_ENERGY = 20;
    public static final int MIN_MAX_ANIMAL_ENERGY = 5;
    public static final int MAX_MAX_ANIMAL_ENERGY = 30;
    public static final int MIN_MAX_PLANT_ENERGY = 5;
    public static final int MAX_MAX_PLANT_ENERGY = 30;
    public static final int MIN_ENERGY_TRANSFER = 1;
    public static final int MAX_ENERGY_TRANSFER = 10;
    public static final int MIN_REPRODUCTIVE_AGE = 1;
    public static final int MAX_REPRODUCTIVE_AGE = 5;
    public static final int MIN_MAX_AGE = 3;
    public static final int MAX_MAX_AGE = 20;
    
    // Configurable parameters (modifiable at runtime)
    private static int initialEnergy = DEFAULT_INITIAL_ENERGY;
    private static int maxAnimalEnergy = DEFAULT_MAX_ANIMAL_ENERGY;
    private static int maxPlantEnergy = DEFAULT_MAX_PLANT_ENERGY;
    private static int energyTransfer = DEFAULT_ENERGY_TRANSFER;
    private static int reproductiveAge = DEFAULT_REPRODUCTIVE_AGE;
    private static int maxAge = DEFAULT_MAX_AGE;

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
    
    // Getters and setters for configurable parameters
    public static int getInitialAnimalCount() {
        return initialAnimalCount;
    }
    
    public static void setInitialAnimalCount(int count) {
        if (validateParameter(count, MIN_ANIMAL_COUNT, MAX_ANIMAL_COUNT, "Initial animal count")) {
            initialAnimalCount = count;
        }
    }
    
    public static int getInitialPlantCount() {
        return initialPlantCount;
    }
    
    public static void setInitialPlantCount(int count) {
        if (validateParameter(count, MIN_PLANT_COUNT, MAX_PLANT_COUNT, "Initial plant count")) {
            initialPlantCount = count;
        }
    }
    
    public static int getInitialEnergy() {
        return initialEnergy;
    }
    
    public static int getMaxAnimalEnergy() {
        return maxAnimalEnergy;
    }
    
    public static int getMaxPlantEnergy() {
        return maxPlantEnergy;
    }
    
    public static int getEnergyTransfer() {
        return energyTransfer;
    }
    
    public static int getReproductiveAge() {
        return reproductiveAge;
    }
    
    public static int getMaxAge() {
        return maxAge;
    }
    
    // Helper method for parameter validation
    private static boolean validateParameter(int value, int min, int max, String paramName) {
        if (value >= min && value <= max) {
            logger.info("{} set to: {}", paramName, value);
            return true;
        } else {
            logger.warn("Invalid {} value: {}", paramName, value);
            return false;
        }
    }
    
    // Setters for configurable parameters with validation
    public static void setInitialEnergy(int value) {
        if (validateParameter(value, MIN_INITIAL_ENERGY, MAX_INITIAL_ENERGY, "Initial energy")) {
            initialEnergy = value;
        }
    }
    
    public static void setMaxAnimalEnergy(int value) {
        if (validateParameter(value, MIN_MAX_ANIMAL_ENERGY, MAX_MAX_ANIMAL_ENERGY, "Max animal energy")) {
            maxAnimalEnergy = value;
        }
    }
    
    public static void setMaxPlantEnergy(int value) {
        if (validateParameter(value, MIN_MAX_PLANT_ENERGY, MAX_MAX_PLANT_ENERGY, "Max plant energy")) {
            maxPlantEnergy = value;
        }
    }
    
    public static void setEnergyTransfer(int value) {
        if (validateParameter(value, MIN_ENERGY_TRANSFER, MAX_ENERGY_TRANSFER, "Energy transfer")) {
            energyTransfer = value;
        }
    }
    
    public static void setReproductiveAge(int value) {
        if (validateParameter(value, MIN_REPRODUCTIVE_AGE, MAX_REPRODUCTIVE_AGE, "Reproductive age")) {
            reproductiveAge = value;
        }
    }
    
    public static void setMaxAge(int value) {
        if (validateParameter(value, MIN_MAX_AGE, MAX_MAX_AGE, "Max age")) {
            maxAge = value;
        }
    }
    
    // Reset all parameters to default values
    public static void resetToDefaults() {
        initialEnergy = DEFAULT_INITIAL_ENERGY;
        maxAnimalEnergy = DEFAULT_MAX_ANIMAL_ENERGY;
        maxPlantEnergy = DEFAULT_MAX_PLANT_ENERGY;
        energyTransfer = DEFAULT_ENERGY_TRANSFER;
        reproductiveAge = DEFAULT_REPRODUCTIVE_AGE;
        maxAge = DEFAULT_MAX_AGE;
        logger.info("All configuration parameters reset to defaults");
    }
    
    private SimulationConfig() {
        // Prevent instantiation
    }
}
