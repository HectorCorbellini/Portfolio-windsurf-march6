package com.isla.ecosystem;

import com.isla.ecosystem.config.SimulationConfig;
import com.isla.ecosystem.core.SimulationController;
import com.isla.ecosystem.ui.swing.SimulationFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main entry point for the ecosystem simulation.
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting ecosystem simulation...");
        
        // Set up UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                logger.warn("Could not set system look and feel", e);
            }
            
            // Create and show UI
            SimulationFrame frame = new SimulationFrame();
            frame.setVisible(true);
            frame.initialize();
        });
    }

    /**
     * Launches the console version of the simulation (legacy)
     */
    public static void runConsoleSimulation() {
        logger.info("Starting console simulation...");
        
        SimulationController controller = new SimulationController();
        controller.initialize();

        // Main simulation loop
        while (controller.executeCycle()) {
            // Display current grid state
            clearScreen();
            System.out.println(controller.getGrid().toString());
            
            // Pause between cycles
            try {
                Thread.sleep(SimulationConfig.PAUSE_BETWEEN_CYCLES);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Display final state
        clearScreen();
        System.out.println(controller.getGrid().toString());
        System.out.println("\nSimulation ended at cycle " + controller.getCurrentCycle());
        
        // Stop the simulation
        controller.stop();
        logger.info("Simulation completed.");
    }

    /**
     * Clears the console screen
     */
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
