package com.isla.ecosystem.entity;

import com.isla.ecosystem.config.SimulationConfig;

/**
 * Base class for all entities in the ecosystem (animals and plants).
 */
public abstract class Entity implements IEntity {
    private int x;
    private int y;
    private EntityType type;
    private int energy;
    private int age;

    protected Entity(int x, int y, EntityType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.energy = SimulationConfig.getInitialEnergy();
        this.age = 0;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public EntityType getType() {
        return type;
    }

    public char getSymbol() {
        return type.getSymbol();
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public void addEnergy(int amount) {
        this.energy = Math.min(getMaxEnergy(), this.energy + amount);
    }

    @Override
    public void reduceEnergy(int amount) {
        this.energy = Math.max(0, this.energy - amount);
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void incrementAge() {
        this.age++;
    }

    @Override
    public boolean isDead() {
        return energy <= 0 || age >= SimulationConfig.getMaxAge();
    }

    @Override
    public boolean canReproduce() {
        return age >= SimulationConfig.getReproductiveAge();
    }

    protected abstract int getMaxEnergy();
}
