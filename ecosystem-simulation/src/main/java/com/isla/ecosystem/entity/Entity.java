package com.isla.ecosystem.entity;

import com.isla.ecosystem.config.SimulationConfig;

/**
 * Base class for all entities in the ecosystem (animals and plants).
 */
public abstract class Entity {
    private int x;
    private int y;
    private char symbol;
    private int energy;
    private int age;

    protected Entity(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.energy = SimulationConfig.getInitialEnergy();
        this.age = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getEnergy() {
        return energy;
    }

    public void addEnergy(int amount) {
        this.energy = Math.min(getMaxEnergy(), this.energy + amount);
    }

    public void reduceEnergy(int amount) {
        this.energy = Math.max(0, this.energy - amount);
    }

    public int getAge() {
        return age;
    }

    public void incrementAge() {
        this.age++;
    }

    public boolean isDead() {
        return energy <= 0 || age >= SimulationConfig.getMaxAge();
    }

    public boolean canReproduce() {
        return age >= SimulationConfig.getReproductiveAge();
    }

    protected abstract int getMaxEnergy();
}
