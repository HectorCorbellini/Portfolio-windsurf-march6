package com.isla.ecosystem.entity;

import com.isla.ecosystem.config.SimulationConfig;

/**
 * Represents an animal in the ecosystem.
 * Animals can move, eat plants, and reproduce.
 */
public class Animal extends Entity {
    
    public Animal(int x, int y, char symbol) {
        super(x, y, symbol);
    }

    @Override
    protected int getMaxEnergy() {
        return SimulationConfig.MAX_ANIMAL_ENERGY;
    }

    /**
     * Consumes a plant to gain energy
     * @param plant The plant to eat
     */
    public void eat(Plant plant) {
        addEnergy(SimulationConfig.ENERGY_TRANSFER);
        plant.reduceEnergy(SimulationConfig.ENERGY_TRANSFER);
    }

    /**
     * Creates a new animal with inherited characteristics
     * @param x The x coordinate for the new animal
     * @param y The y coordinate for the new animal
     * @param symbol The symbol for the new animal
     * @return A new Animal instance
     */
    public Animal reproduce(int x, int y, char symbol) {
        reduceEnergy(SimulationConfig.ENERGY_TRANSFER); // Reproduction costs energy
        return new Animal(x, y, symbol);
    }
}
