package com.isla.ecosystem.ui.swing;

import com.isla.ecosystem.grid.Grid;
import com.isla.ecosystem.ui.swing.components.ControlPanel;
import com.isla.ecosystem.ui.swing.components.GridPanel;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main panel that combines the grid visualization and control components.
 * Follows Single Responsibility Principle by delegating specific functionality to specialized components.
 */
public class SimulationPanel extends JPanel {
    private static final String CSV_DIR = "simulation_data";
    private static final String CSV_FILE = "ecosystem_stats.csv";
    
    private final GridPanel gridPanel;
    private final ControlPanel controlPanel;
    
    public SimulationPanel() {
        setLayout(new BorderLayout(0, 10));
        setBackground(Color.WHITE);
        
        // Initialize components
        gridPanel = new GridPanel();
        controlPanel = new ControlPanel();
        
        // Set up stats button callback
        controlPanel.setOnStatsButtonClick(unused -> showCsvContents());
        
        // Create a scrollable viewport for the grid
        JScrollPane scrollPane = createScrollableGridView();
        
        // Add components to layout
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JScrollPane createScrollableGridView() {
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }
    
    public void updateState(Grid grid, int cycle, int animalCount, int plantCount) {
        gridPanel.updateState(grid, cycle, animalCount, plantCount);
        controlPanel.setSimulationStarted(true);
    }
    
    public void resetView() {
        gridPanel.updateState(null, 0, 0, 0);
        controlPanel.reset();
    }
    
    /**
     * Shows the contents of the CSV file in a dialog
     */
    private void showCsvContents() {
        StringBuilder content = new StringBuilder();
        Path csvPath = Paths.get(CSV_DIR, CSV_FILE);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace(";", "\t");
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            content.append("Error reading CSV file: ").append(e.getMessage());
        }
        
        JTextArea textArea = new JTextArea(content.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        
        JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Simulation Statistics",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public ControlPanel getControlPanel() {
        return controlPanel;
    }
    
    @Override
    public Dimension getPreferredSize() {
        return gridPanel.getPreferredSize();
    }
}
