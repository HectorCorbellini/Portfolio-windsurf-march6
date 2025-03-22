package com.isla.ecosystem.core;

import com.isla.ecosystem.config.SimulationConfig;
import com.isla.ecosystem.entity.Animal;
import com.isla.ecosystem.entity.Entity;
import com.isla.ecosystem.entity.Plant;
import com.isla.ecosystem.grid.Cell;
import com.isla.ecosystem.grid.Grid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Controls the main simulation logic and lifecycle.
 */
public class SimulationController {
    private static final Logger logger = LoggerFactory.getLogger(SimulationController.class);
    private final Grid grid;
    private final List<Animal> animals;
    private final List<Plant> plants;
    private final Random random;
    private final AtomicInteger currentCycle;
    private final StatisticsCollector stats;
    private volatile boolean isRunning;
    private volatile SimulationConfig.NeighborhoodType neighborhoodType = SimulationConfig.NeighborhoodType.VON_NEUMANN;

    public SimulationController() {
        this.grid = new Grid();
        this.animals = new CopyOnWriteArrayList<>();
        this.plants = new CopyOnWriteArrayList<>();
        this.random = new Random();
        this.currentCycle = new AtomicInteger(0);
        this.stats = new StatisticsCollector();
        this.isRunning = false;
    }

    /**
     * Initializes the simulation with starting entities
     */
    public void initialize() {
        logger.info("Initializing simulation...");
        int animals = SimulationConfig.getInitialAnimalCount();
        int plants = SimulationConfig.getInitialPlantCount();
        initializeEntities(animals, plants);
        isRunning = true;
        logger.info("Simulation initialized with {} animals and {} plants",
            animals, plants);
    }

    private void initializeEntities(int animals, int plants) {
        // Initialize animals
        for (int i = 0; i < animals; i++) {
            Position pos = findRandomEmptyPosition();
            Animal animal = new Animal(pos.x(), pos.y());
            this.animals.add(animal);
            grid.addEntity(animal, pos.x(), pos.y());
        }

        // Initialize plants
        for (int i = 0; i < plants; i++) {
            Position pos = findRandomEmptyPosition();
            Plant plant = new Plant(pos.x(), pos.y());
            this.plants.add(plant);
            grid.addEntity(plant, pos.x(), pos.y());
        }
    }

    /**
     * Executes one simulation cycle
     * @return true if the simulation should continue, false if it should stop
     */
    public boolean executeCycle() {
        if (!isRunning) {
            return false;
        }

        logger.debug("Executing cycle {}", currentCycle.get());
        
        processAnimals();
        processPlants();

        // Update statistics
        stats.updateStats(currentCycle.get(), animals.size(), plants.size());
        
        // Check if ecosystem is balanced
        boolean isBalanced = checkEcosystemBalance();
        
        currentCycle.incrementAndGet();
        return isBalanced;
    }
    
    private void processAnimals() {
        List<Animal> animalsCopy = new ArrayList<>(animals);
        Collections.shuffle(animalsCopy); // Randomize movement order
        
        for (Animal animal : animalsCopy) {
            if (!animals.contains(animal)) continue; // Skip if animal was removed
            
            // Age and energy check
            animal.incrementAge();
            animal.reduceEnergy(1); // Movement costs energy
            
            if (animal.isDead()) {
                removeEntity(animal);
                stats.recordDeath();
                continue;
            }

            // Movement and interaction
            moveAnimal(animal);
            
            // Reproduction check
            if (animal.canReproduce() && animal.getEnergy() > SimulationConfig.getEnergyTransfer()) {
                tryReproduceAnimal(animal);
            }
        }
    }
    
    private void processPlants() {
        List<Plant> plantsCopy = new ArrayList<>(plants);
        for (Plant plant : plantsCopy) {
            if (!plants.contains(plant)) continue; // Skip if plant was removed
            
            plant.incrementAge();
            plant.addEnergy(1); // Plants gain energy each cycle
            
            if (plant.isDead()) {
                removeEntity(plant);
                stats.recordDeath();
                continue;
            }
        }
    }

    private void moveAnimal(Animal animal) {
        List<Cell> neighbors = getShuffledNeighbors(animal.getX(), animal.getY());
        
        // First, look for plants to eat
        for (Cell cell : neighbors) {
            List<Entity> entities = cell.getEntities();
            for (Entity entity : entities) {
                if (entity instanceof Plant) {
                    animal.eat((Plant) entity);
                    if (((Plant) entity).isDead()) {
                        removeEntity(entity);
                        stats.recordDeath();
                    }
                    return; // Stop moving after eating
                }
            }
        }
        
        // If no plants found, move to a random neighbor cell
        if (!neighbors.isEmpty()) {
            Cell targetCell = neighbors.get(0); // Select the first neighbor after shuffling
            grid.moveEntity(animal, animal.getX(), animal.getY(),
                targetCell.getX(), targetCell.getY());
        }
    }
    
    private List<Cell> getShuffledNeighbors(int x, int y) {
        List<Cell> neighbors = grid.getNeighbors(x, y, neighborhoodType);
        Collections.shuffle(neighbors); // Randomize order
        return neighbors;
    }

    public void setNeighborhoodType(SimulationConfig.NeighborhoodType type) {
        this.neighborhoodType = type;
        logger.info("Neighborhood type set to: {}", type);
    }

    public SimulationConfig.NeighborhoodType getNeighborhoodType() {
        return neighborhoodType;
    }

    private void tryReproduceAnimal(Animal animal) {
        List<Cell> neighbors = getShuffledNeighbors(animal.getX(), animal.getY());
        
        // First check if there's another animal in a neighboring cell
        if (findMate(neighbors)) {
            // Look for an empty cell to place the offspring
            placeOffspring(animal, neighbors);
        }
    }
    
    private boolean findMate(List<Cell> neighbors) {
        for (Cell cell : neighbors) {
            for (Entity entity : cell.getEntities()) {
                if (entity instanceof Animal mate && 
                    mate.canReproduce() && 
                    mate.getEnergy() > SimulationConfig.getEnergyTransfer()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void placeOffspring(Animal parent, List<Cell> neighbors) {
        for (Cell cell : neighbors) {
            if (cell.isEmpty()) {
                Animal offspring = parent.reproduce(cell.getX(), cell.getY());
                animals.add(offspring);
                grid.addEntity(offspring, cell.getX(), cell.getY());
                stats.recordBirth();
                break;
            }
        }
    }

    private void removeEntity(Entity entity) {
        grid.removeEntity(entity);
        if (entity instanceof Animal) {
            animals.remove(entity);
        } else if (entity instanceof Plant) {
            plants.remove(entity);
        }
    }

    private Position findRandomEmptyPosition() {
        int maxAttempts = grid.getWidth() * grid.getHeight();
        int attempts = 0;
        
        while (attempts < maxAttempts) {
            int x = random.nextInt(grid.getWidth());
            int y = random.nextInt(grid.getHeight());
            
            Cell cell = grid.getCell(x, y);
            if (cell.isEmpty()) {
                return new Position(x, y);
            }
            attempts++;
        }
        
        throw new IllegalStateException("Could not find empty position in grid");
    }

    private boolean checkEcosystemBalance() {
        // Check if there's enough life
        if (animals.isEmpty() || plants.isEmpty()) {
            logger.info("Ecosystem unbalanced: No more {} remaining",
                animals.isEmpty() ? "animals" : "plants");
            return false;
        }

        // Check if there's too much life
        int maxAnimals = (grid.getWidth() * grid.getHeight()) - plants.size();
        if (animals.size() > maxAnimals) {
            logger.info("Ecosystem unbalanced: Too many animals");
            return false;
        }

        return true;
    }

    public Grid getGrid() {
        return grid;
    }

    public StatisticsCollector getStats() {
        return stats;
    }

    public int getCurrentCycle() {
        return currentCycle.get();
    }
    
    public int getAnimalCount() {
        return animals.size();
    }
    
    public int getPlantCount() {
        return plants.size();
    }
    
    public int getBirths() {
        return stats.getBirths();
    }
    
    public int getDeaths() {
        return stats.getDeaths();
    }

    public void stop() {
        isRunning = false;
    }

    private record Position(int x, int y) {}
}
