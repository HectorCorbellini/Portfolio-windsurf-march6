package com.isla.ecosystem.ui.swing;

import com.isla.ecosystem.config.SimulationConfig;
import com.isla.ecosystem.core.SimulationController;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Main frame for the ecosystem simulation UI
 */
public class SimulationFrame extends JFrame {
    private final SimulationPanel simulationPanel;
    private final JButton startButton;
    private final JButton pauseButton;
    private final JButton stopButton;
    private final JButton resetButton;
    private final JToggleButton neighborhoodButton;
    private final JLabel statusLabel;
    private final JLabel statisticsLabel;
    
    private SimulationController controller;
    private Thread simulationThread;
    private final AtomicBoolean paused = new AtomicBoolean(false);
    private final AtomicBoolean running = new AtomicBoolean(false);
    
    public SimulationFrame() {
        super("Ecosystem Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create UI components
        simulationPanel = new SimulationPanel();
        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        stopButton = new JButton("Stop");
        resetButton = new JButton("Reset");
        neighborhoodButton = new JToggleButton("Von Neumann");
        statusLabel = new JLabel("Ready");
        statisticsLabel = new JLabel("Births: 0 | Deaths: 0");
        
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        resetButton.setEnabled(false);
        
        // Configure neighborhood button
        neighborhoodButton.setToolTipText("Click to toggle between Von Neumann (4 neighbors) and Moore (8 neighbors) neighborhoods");
        
        // Layout
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(stopButton);
        controlPanel.add(resetButton);
        controlPanel.add(neighborhoodButton);
        controlPanel.add(statusLabel);
        
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        bottomPanel.add(controlPanel, BorderLayout.WEST);
        bottomPanel.add(statisticsLabel, BorderLayout.EAST);
        
        setLayout(new BorderLayout());
        add(simulationPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Event handlers
        startButton.addActionListener(e -> startSimulation());
        pauseButton.addActionListener(e -> togglePause());
        stopButton.addActionListener(e -> stopSimulation());
        resetButton.addActionListener(e -> resetSimulation());
        neighborhoodButton.addActionListener(e -> toggleNeighborhoodType());
        
        pack();
        setLocationRelativeTo(null);
    }
    
    public void initialize() {
        controller = new SimulationController();
        controller.initialize();
        updateUI();
    }
    
    private void startSimulation() {
        if (controller == null) {
            initialize();
        }
        
        if (running.get() && !paused.get()) {
            return; // Already running and not paused
        }
        
        if (paused.get()) {
            // Resume from pause
            paused.set(false);
            pauseButton.setText("Pause");
            statusLabel.setText("Running");
            return;
        }
        
        running.set(true);
        paused.set(false);
        
        // Update button states
        startButton.setText("Start");
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        pauseButton.setText("Pause");
        stopButton.setEnabled(true);
        resetButton.setEnabled(false);
        neighborhoodButton.setEnabled(false);
        
        // Start simulation in a separate thread
        simulationThread = new Thread(() -> {
            try {
                statusLabel.setText("Running");
                
                while (running.get() && controller.executeCycle()) {
                    // Update UI
                    SwingUtilities.invokeLater(this::updateUI);
                    
                    // Pause if requested
                    while (paused.get() && running.get()) {
                        Thread.sleep(100);
                    }
                    
                    // Wait for the next cycle
                    Thread.sleep(SimulationConfig.PAUSE_BETWEEN_CYCLES);
                }
                
                // Simulation ended
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Completed");
                    startButton.setEnabled(false);
                    pauseButton.setEnabled(false);
                    stopButton.setEnabled(false);
                    resetButton.setEnabled(true);
                    updateUI();
                });
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                running.set(false);
            }
        });
        
        simulationThread.start();
    }
    
    private void togglePause() {
        if (!running.get()) {
            return;
        }
        
        paused.set(!paused.get());
        
        if (paused.get()) {
            pauseButton.setText("Resume");
            statusLabel.setText("Paused");
            startButton.setText("Resume");
            startButton.setEnabled(true);
        } else {
            pauseButton.setText("Pause");
            statusLabel.setText("Running");
            startButton.setText("Start");
            startButton.setEnabled(false);
        }
    }
    
    private void stopSimulation() {
        if (!running.get()) {
            return;
        }
        
        // Stop the current simulation
        running.set(false);
        paused.set(false);
        try {
            simulationThread.join(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Update UI
        startButton.setText("Start");
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        pauseButton.setText("Pause");
        stopButton.setEnabled(false);
        resetButton.setEnabled(true);
        statusLabel.setText("Stopped");
    }
    
    private void resetSimulation() {
        // Stop if running
        if (running.get()) {
            stopSimulation();
        }
        
        // Reset the controller
        controller = new SimulationController();
        controller.initialize();
        
        // Update UI
        startButton.setText("Start");
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        pauseButton.setText("Pause");
        stopButton.setEnabled(false);
        resetButton.setEnabled(false);
        neighborhoodButton.setEnabled(true);
        statusLabel.setText("Ready");
        
        updateUI();
    }

    private void toggleNeighborhoodType() {
        if (controller != null) {
            SimulationConfig.NeighborhoodType newType = neighborhoodButton.isSelected() ?
                SimulationConfig.NeighborhoodType.MOORE :
                SimulationConfig.NeighborhoodType.VON_NEUMANN;
            
            controller.setNeighborhoodType(newType);
            neighborhoodButton.setText(newType.toString());
        }
    }
    
    private void updateUI() {
        if (controller != null) {
            simulationPanel.updateState(
                controller.getGrid(), 
                controller.getCurrentCycle(),
                controller.getAnimalCount(),
                controller.getPlantCount()
            );
            
            statisticsLabel.setText(String.format(
                "Births: %d | Deaths: %d", 
                controller.getBirths(), 
                controller.getDeaths()
            ));
        }
    }
}
