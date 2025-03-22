package com.isla.ecosystem.entity;

import com.isla.ecosystem.config.SimulationConfig;

/**
 * Represents a plant in the ecosystem.
 * Plants are stationary and can be eaten by animals.
 * Plants do not reproduce - their population only changes when eaten by animals.
 */
public class Plant extends Entity {
    
    public Plant(int x, int y) {
        super(x, y, EntityType.PLANT);
    }
    
    /**
     * Constructor with custom symbol for backward compatibility
     */
    public Plant(int x, int y, char symbol) {
        super(x, y, EntityType.PLANT);
        // Note: We're ignoring the symbol parameter as we're using EntityType now
    }

    @Override
    protected int getMaxEnergy() {
        return SimulationConfig.getMaxPlantEnergy();
    }
}
