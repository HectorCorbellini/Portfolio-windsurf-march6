package com.isla.ecosystem.entity;

import com.isla.ecosystem.config.SimulationConfig;

/**
 * Represents an animal in the ecosystem.
 * Animals can move, eat plants, and reproduce.
 */
public class Animal extends Entity implements IMovable, IReproducible {
    
    public Animal(int x, int y) {
        super(x, y, EntityType.ANIMAL);
    }
    
    /**
     * Constructor with custom symbol for creating animals with unique identifiers
     */
    public Animal(int x, int y, char symbol) {
        super(x, y, EntityType.ANIMAL);
        // Note: We're ignoring the symbol parameter as we're using EntityType now
    }

    @Override
    protected int getMaxEnergy() {
        return SimulationConfig.getMaxAnimalEnergy();
    }

    /**
     * Consumes a plant to gain energy
     * @param plant The plant to eat
     */
    public void eat(Plant plant) {
        addEnergy(SimulationConfig.getEnergyTransfer());
        plant.reduceEnergy(SimulationConfig.getEnergyTransfer());
    }

    /**
     * Creates a new animal with inherited characteristics
     * @param x The x coordinate for the new animal
     * @param y The y coordinate for the new animal
     * @return A new Animal instance
     */
    @Override
    public Animal reproduce(int x, int y) {
        reduceEnergy(SimulationConfig.getEnergyTransfer()); // Reproduction costs energy
        return new Animal(x, y);
    }
    
    @Override
    public void moveTo(int x, int y) {
        setX(x);
        setY(y);
    }
}
