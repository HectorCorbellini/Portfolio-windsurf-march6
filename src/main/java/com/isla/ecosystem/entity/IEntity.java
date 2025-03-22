package com.isla.ecosystem.entity;

/**
 * Base interface for all entities in the ecosystem.
 */
public interface IEntity {
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
    EntityType getType();
    int getEnergy();
    void addEnergy(int amount);
    void reduceEnergy(int amount);
    int getAge();
    void incrementAge();
    boolean isDead();
    boolean canReproduce();
}
