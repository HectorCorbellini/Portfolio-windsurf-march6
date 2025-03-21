package com.isla.ecosystem.grid;

import com.isla.ecosystem.config.SimulationConfig;
import com.isla.ecosystem.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Represents the 2D grid environment of the ecosystem.
 * Thread-safe implementation using ReadWriteLock.
 */
public class Grid {
    private final Cell[][] cells;
    private final int width;
    private final int height;
    private final ReadWriteLock lock;

    public Grid() {
        this(SimulationConfig.GRID_WIDTH, SimulationConfig.GRID_HEIGHT);
    }

    public Grid(int width, int height) {
        validateDimensions(width, height);
        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];
        this.lock = new ReentrantReadWriteLock();
        initializeCells();
    }

    private void validateDimensions(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Grid dimensions must be positive");
        }
        if (width > SimulationConfig.MAX_GRID_SIZE || height > SimulationConfig.MAX_GRID_SIZE) {
            throw new IllegalArgumentException("Grid dimensions exceed maximum allowed size");
        }
    }

    private void initializeCells() {
        lock.writeLock().lock();
        try {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    cells[y][x] = new Cell(x, y);
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Gets a cell at the specified coordinates
     * @param x X coordinate
     * @param y Y coordinate
     * @return The cell at the specified location
     * @throws IllegalArgumentException if coordinates are invalid
     */
    public Cell getCell(int x, int y) {
        validateCoordinates(x, y);
        lock.readLock().lock();
        try {
            return cells[y][x];
        } finally {
            lock.readLock().unlock();
        }
    }

    private static final int[][] VON_NEUMANN_DIRECTIONS = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}}; // N, E, S, W
    private static final int[][] MOORE_DIRECTIONS = {{0, -1}, {1, -1}, {1, 0}, {1, 1},
                                                    {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}}; // N, NE, E, SE, S, SW, W, NW

    /**
     * Gets the neighborhood for a given position based on the specified type
     * @param x X coordinate
     * @param y Y coordinate
     * @param type Type of neighborhood (Von Neumann or Moore)
     * @return List of neighboring cells
     */
    public List<Cell> getNeighbors(int x, int y, SimulationConfig.NeighborhoodType type) {
        validateCoordinates(x, y);
        List<Cell> neighbors = new ArrayList<>();
        
        int[][] directions = type == SimulationConfig.NeighborhoodType.VON_NEUMANN ?
                            VON_NEUMANN_DIRECTIONS : MOORE_DIRECTIONS;
        
        lock.readLock().lock();
        try {
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                if (isValidPosition(newX, newY)) {
                    neighbors.add(cells[newY][newX]);
                }
            }
            return neighbors;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Gets the von Neumann neighborhood (4 adjacent cells)
     * @param x X coordinate
     * @param y Y coordinate
     * @return List of adjacent cells
     */
    public List<Cell> getNeighbors(int x, int y) {
        return getNeighbors(x, y, SimulationConfig.NeighborhoodType.VON_NEUMANN);
    }

    /**
     * Moves an entity from one cell to another
     * @param entity The entity to move
     * @param fromX Source X coordinate
     * @param fromY Source Y coordinate
     * @param toX Destination X coordinate
     * @param toY Destination Y coordinate
     */
    public void moveEntity(Entity entity, int fromX, int fromY, int toX, int toY) {
        validateCoordinates(fromX, fromY);
        validateCoordinates(toX, toY);
        
        Cell sourceCell = getCell(fromX, fromY);
        Cell targetCell = getCell(toX, toY);
        
        sourceCell.removeEntity(entity);
        targetCell.addEntity(entity);
        
        entity.setX(toX);
        entity.setY(toY);
    }

    /**
     * Adds an entity to the grid at the specified position
     * @param entity The entity to add
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void addEntity(Entity entity, int x, int y) {
        validateCoordinates(x, y);
        Cell cell = getCell(x, y);
        cell.addEntity(entity);
    }

    /**
     * Removes an entity from the grid
     * @param entity The entity to remove
     */
    public void removeEntity(Entity entity) {
        validateCoordinates(entity.getX(), entity.getY());
        Cell cell = getCell(entity.getX(), entity.getY());
        cell.removeEntity(entity);
    }

    private void validateCoordinates(int x, int y) {
        if (!isValidPosition(x, y)) {
            throw new IllegalArgumentException(
                String.format("Invalid coordinates: x=%d, y=%d", x, y));
        }
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Renders the current state of the grid as a string
     * @return String representation of the grid
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        lock.readLock().lock();
        try {
            // Top border
            sb.append("+").append("-".repeat(width)).append("+\n");
            
            // Grid content
            for (int y = 0; y < height; y++) {
                sb.append("|");
                for (int x = 0; x < width; x++) {
                    sb.append(cells[y][x].getDisplaySymbol());
                }
                sb.append("|\n");
            }
            
            // Bottom border
            sb.append("+").append("-".repeat(width)).append("+\n");
            
            return sb.toString();
        } finally {
            lock.readLock().unlock();
        }
    }
}
