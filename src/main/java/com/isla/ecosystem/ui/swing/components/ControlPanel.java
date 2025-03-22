package com.isla.ecosystem.ui.swing.components;

import com.isla.ecosystem.config.SimulationConfig;
import com.isla.ecosystem.ui.swing.components.InitialsDialog;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Panel containing control buttons for the simulation.
 * Follows Single Responsibility Principle by handling only UI controls.
 */
public class ControlPanel extends JPanel {
    private static final Color BACKGROUND_GRAY = new Color(240, 240, 240);
    
    private final JButton statsButton;
    private final JButton changeInitialsButton;
    private final JLabel statusLabel;
    private Consumer<Void> onStatsButtonClick;
    private Consumer<Void> onChangeInitialsClick;
    
    public ControlPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setBackground(BACKGROUND_GRAY);
        
        // Create buttons panel with FlowLayout
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        buttonsPanel.setBackground(BACKGROUND_GRAY);
        
        // Create stats button and status label
        statsButton = createStatsButton();
        changeInitialsButton = createChangeInitialsButton();
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        statusLabel.setForeground(Color.RED);
        
        // Add buttons to panel
        buttonsPanel.add(statsButton);
        buttonsPanel.add(changeInitialsButton);
        
        // Add everything to the main panel
        add(buttonsPanel);
    }
    
    private JButton createStatsButton() {
        JButton button = new JButton("Show Stats");
        button.setVisible(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(100, 100, 255));
        button.setForeground(Color.WHITE);
        
        button.addActionListener(e -> {
            if (onStatsButtonClick != null) {
                onStatsButtonClick.accept(null);
            }
        });
        return button;
    }
    
    private JButton createChangeInitialsButton() {
        JButton button = new JButton("Change Initials");
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(100, 100, 255));
        button.setForeground(Color.WHITE);
        button.setVisible(false); // Initially hidden
        
        button.addActionListener(e -> {
            InitialsDialog dialog = new InitialsDialog((Frame) SwingUtilities.getWindowAncestor(this));
            dialog.setOnConfirm((animals, plants) -> {
                SimulationConfig.setInitialAnimalCount(animals);
                SimulationConfig.setInitialPlantCount(plants);
                if (onChangeInitialsClick != null) {
                    onChangeInitialsClick.accept(null);
                }
            });
            dialog.setVisible(true);
        });
        return button;
    }
    
    public void setOnStatsButtonClick(Consumer<Void> callback) {
        this.onStatsButtonClick = callback;
    }

    public void setOnChangeInitialsClick(Consumer<Void> callback) {
        this.onChangeInitialsClick = callback;
    }
    
    public void setSimulationStarted(boolean started) {
        // Enable/disable buttons based on simulation state
        if (started) {
            statsButton.setVisible(true);
            changeInitialsButton.setVisible(true); // Show the new button
        } else {
            statsButton.setVisible(false);
            changeInitialsButton.setVisible(false);
        }
        
        revalidate();
        repaint();
    }
    
    public void reset() {
        // Remove stats button
        statsButton.setVisible(false);
        changeInitialsButton.setVisible(false); // Hide the new button
        
        revalidate();
        repaint();
    }
} 