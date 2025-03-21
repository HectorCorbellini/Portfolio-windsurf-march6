package com.isla.ecosystem.grid;

import com.isla.ecosystem.config.SimulationConfig;
import com.isla.ecosystem.entity.Entity;
import com.isla.ecosystem.entity.Animal;
import com.isla.ecosystem.entity.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Represents a single cell in the grid.
 * Thread-safe implementation using ReadWriteLock.
 */
public class Cell {
    private final int x;
    private final int y;
    private final List<Entity> entities;
    private final ReadWriteLock lock;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.entities = new ArrayList<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public void addEntity(Entity entity) {
        lock.writeLock().lock();
        try {
            entities.add(entity);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void removeEntity(Entity entity) {
        lock.writeLock().lock();
        try {
            entities.remove(entity);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<Entity> getEntities() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(entities);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Checks if this cell is empty
     * @return true if the cell contains no entities
     */
    public boolean isEmpty() {
        lock.readLock().lock();
        try {
            return entities.isEmpty();
        } finally {
            lock.readLock().unlock();
        }
    }

    public char getDisplaySymbol() {
        lock.readLock().lock();
        try {
            if (entities.isEmpty()) {
                return SimulationConfig.EMPTY_CELL;
            }
            
            // Count animals and plants
            int animalCount = 0;
            int plantCount = 0;
            for (Entity entity : entities) {
                if (entity instanceof Animal) animalCount++;
                else if (entity instanceof Plant) plantCount++;
            }
            
            // If there are too many entities, show overflow symbol
            if (animalCount + plantCount > 1) {
                return SimulationConfig.OVERFLOW_SYMBOL;
            }
            
            // Show the single entity
            Entity entity = entities.get(0);
            if (entity instanceof Animal) {
                return SimulationConfig.INITIAL_ANIMAL_SYMBOL;
            } else {
                return SimulationConfig.INITIAL_PLANT_SYMBOL;
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
