package com.isla.ecosystem.core;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Collects and manages simulation statistics.
 * Handles CSV output for data analysis.
 */
public class StatisticsCollector {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsCollector.class);
    private static final String CSV_HEADER = "Time;Animals;Plants;Births;Deaths;Events\n";
    private static final String CSV_DIR = "simulation_data";
    private static final String CSV_FILE = "ecosystem_stats.csv";

    private final AtomicInteger births;
    private final AtomicInteger deaths;
    private final StringBuilder events;
    private final Path csvPath;

    public StatisticsCollector() {
        this.births = new AtomicInteger(0);
        this.deaths = new AtomicInteger(0);
        this.events = new StringBuilder();
        this.csvPath = initializeCsvFile();
    }

    private Path initializeCsvFile() {
        try {
            Path dir = Paths.get(CSV_DIR);
            Files.createDirectories(dir);
            Path file = dir.resolve(CSV_FILE);
            
            // Create new file with header
            try (FileWriter writer = new FileWriter(file.toFile())) {
                writer.write(CSV_HEADER);
            }
            
            return file;
        } catch (IOException e) {
            logger.error("Failed to initialize CSV file", e);
            throw new RuntimeException("Failed to initialize statistics collection", e);
        }
    }

    public void recordBirth() {
        births.incrementAndGet();
    }

    public void recordDeath() {
        deaths.incrementAndGet();
    }

    public void recordEvent(String event) {
        synchronized (events) {
            if (events.length() > 0) {
                events.append(" / ");
            }
            events.append(event);
        }
    }

    /**
     * Updates statistics for the current cycle
     * @param cycle Current cycle number
     * @param animalCount Current number of animals
     * @param plantCount Current number of plants
     */
    public void updateStats(int cycle, int animalCount, int plantCount) {
        String currentEvents;
        synchronized (events) {
            currentEvents = events.toString();
            events.setLength(0); // Clear events for next cycle
        }

        String stats = String.format("%d;%d;%d;%d;%d;%s\n",
            cycle + 1, // Add 1 to make it 1-based for display
            animalCount,
            plantCount,
            births.get(),
            deaths.get(),
            currentEvents);

        try (FileWriter writer = new FileWriter(csvPath.toFile(), true)) {
            writer.write(stats);
        } catch (IOException e) {
            logger.error("Failed to write statistics to CSV", e);
        }

        // Log current state
        logger.info("Cycle {}: {} animals, {} plants, {} births, {} deaths",
            cycle + 1, animalCount, plantCount, births.get(), deaths.get());
    }

    public int getBirths() {
        return births.get();
    }

    public int getDeaths() {
        return deaths.get();
    }

    public String getEvents() {
        synchronized (events) {
            return events.toString();
        }
    }
}
