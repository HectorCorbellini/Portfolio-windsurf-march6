package com.isla.ecosystem.ui.swing;

import com.isla.ecosystem.config.SimulationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel containing controls for customizing simulation parameters.
 * This allows users to experiment with different configurations to find 
 * a stable ecosystem balance.
 */
public class ConfigurationPanel extends JPanel implements ChangeListener, ActionListener {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationPanel.class);

    // UI Components
    private JSlider initialEnergySlider;
    private JSlider maxAnimalEnergySlider;
    private JSlider maxPlantEnergySlider;
    private JSlider energyTransferSlider;
    private JSlider reproductiveAgeSlider;
    private JSlider maxAgeSlider;
    private JButton resetButton;

    // Value labels
    private JLabel initialEnergyValue;
    private JLabel maxAnimalEnergyValue;
    private JLabel maxPlantEnergyValue;
    private JLabel energyTransferValue;
    private JLabel reproductiveAgeValue;
    private JLabel maxAgeValue;

    // Listener for configuration changes
    private ConfigurationChangeListener changeListener;

    public ConfigurationPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), 
                "Ecosystem Configuration",
                TitledBorder.CENTER, 
                TitledBorder.TOP));

        createComponents();
        layoutComponents();
        initializeValues();
    }

    /**
     * Set a listener to be notified when configuration values change
     */
    public void setConfigurationChangeListener(ConfigurationChangeListener listener) {
        this.changeListener = listener;
    }

    /**
     * Initialize all UI components
     */
    private void createComponents() {
        // Create sliders
        initialEnergySlider = createSlider(
                SimulationConfig.MIN_INITIAL_ENERGY, 
                SimulationConfig.MAX_INITIAL_ENERGY, 
                SimulationConfig.getInitialEnergy());
        
        maxAnimalEnergySlider = createSlider(
                SimulationConfig.MIN_MAX_ANIMAL_ENERGY, 
                SimulationConfig.MAX_MAX_ANIMAL_ENERGY, 
                SimulationConfig.getMaxAnimalEnergy());
        
        maxPlantEnergySlider = createSlider(
                SimulationConfig.MIN_MAX_PLANT_ENERGY, 
                SimulationConfig.MAX_MAX_PLANT_ENERGY, 
                SimulationConfig.getMaxPlantEnergy());
        
        energyTransferSlider = createSlider(
                SimulationConfig.MIN_ENERGY_TRANSFER, 
                SimulationConfig.MAX_ENERGY_TRANSFER, 
                SimulationConfig.getEnergyTransfer());
        
        reproductiveAgeSlider = createSlider(
                SimulationConfig.MIN_REPRODUCTIVE_AGE, 
                SimulationConfig.MAX_REPRODUCTIVE_AGE, 
                SimulationConfig.getReproductiveAge());
        
        maxAgeSlider = createSlider(
                SimulationConfig.MIN_MAX_AGE, 
                SimulationConfig.MAX_MAX_AGE, 
                SimulationConfig.getMaxAge());

        // Create value labels
        initialEnergyValue = new JLabel(String.valueOf(SimulationConfig.getInitialEnergy()));
        maxAnimalEnergyValue = new JLabel(String.valueOf(SimulationConfig.getMaxAnimalEnergy()));
        maxPlantEnergyValue = new JLabel(String.valueOf(SimulationConfig.getMaxPlantEnergy()));
        energyTransferValue = new JLabel(String.valueOf(SimulationConfig.getEnergyTransfer()));
        reproductiveAgeValue = new JLabel(String.valueOf(SimulationConfig.getReproductiveAge()));
        maxAgeValue = new JLabel(String.valueOf(SimulationConfig.getMaxAge()));

        // Reset button
        resetButton = new JButton("Reset to Defaults");
        resetButton.addActionListener(this);
    }

    /**
     * Utility method to create a slider with consistent properties
     */
    private JSlider createSlider(int min, int max, int value) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, value);
        slider.setMajorTickSpacing((max - min) / 4);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);
        return slider;
    }

    /**
     * Organize components in the panel
     */
    private void layoutComponents() {
        JPanel slidersPanel = new JPanel(new GridLayout(6, 3, 10, 5));
        slidersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add each slider with its label
        addSliderRow(slidersPanel, "Initial Energy:", initialEnergySlider, initialEnergyValue);
        addSliderRow(slidersPanel, "Max Animal Energy:", maxAnimalEnergySlider, maxAnimalEnergyValue);
        addSliderRow(slidersPanel, "Max Plant Energy:", maxPlantEnergySlider, maxPlantEnergyValue);
        addSliderRow(slidersPanel, "Energy Transfer:", energyTransferSlider, energyTransferValue);
        addSliderRow(slidersPanel, "Reproductive Age:", reproductiveAgeSlider, reproductiveAgeValue);
        addSliderRow(slidersPanel, "Max Age:", maxAgeSlider, maxAgeValue);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(resetButton);

        add(slidersPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Helper method to add a labeled slider row
     */
    private void addSliderRow(JPanel panel, String labelText, JSlider slider, JLabel valueLabel) {
        JLabel label = new JLabel(labelText, JLabel.RIGHT);
        JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        valuePanel.add(valueLabel);
        
        panel.add(label);
        panel.add(slider);
        panel.add(valuePanel);
    }

    /**
     * Set initial values for all components
     */
    private void initializeValues() {
        updateSliderValue(initialEnergySlider, initialEnergyValue, SimulationConfig.getInitialEnergy());
        updateSliderValue(maxAnimalEnergySlider, maxAnimalEnergyValue, SimulationConfig.getMaxAnimalEnergy());
        updateSliderValue(maxPlantEnergySlider, maxPlantEnergyValue, SimulationConfig.getMaxPlantEnergy());
        updateSliderValue(energyTransferSlider, energyTransferValue, SimulationConfig.getEnergyTransfer());
        updateSliderValue(reproductiveAgeSlider, reproductiveAgeValue, SimulationConfig.getReproductiveAge());
        updateSliderValue(maxAgeSlider, maxAgeValue, SimulationConfig.getMaxAge());
    }

    /**
     * Update the slider and its associated value label
     */
    private void updateSliderValue(JSlider slider, JLabel valueLabel, int value) {
        slider.setValue(value);
        valueLabel.setText(String.valueOf(value));
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        
        // Only update when user finishes dragging
        if (!source.getValueIsAdjusting()) {
            int value = source.getValue();
            
            if (source == initialEnergySlider) {
                initialEnergyValue.setText(String.valueOf(value));
                SimulationConfig.setInitialEnergy(value);
                notifyConfigChanged("initialEnergy", value);
            } else if (source == maxAnimalEnergySlider) {
                maxAnimalEnergyValue.setText(String.valueOf(value));
                SimulationConfig.setMaxAnimalEnergy(value);
                notifyConfigChanged("maxAnimalEnergy", value);
            } else if (source == maxPlantEnergySlider) {
                maxPlantEnergyValue.setText(String.valueOf(value));
                SimulationConfig.setMaxPlantEnergy(value);
                notifyConfigChanged("maxPlantEnergy", value);
            } else if (source == energyTransferSlider) {
                energyTransferValue.setText(String.valueOf(value));
                SimulationConfig.setEnergyTransfer(value);
                notifyConfigChanged("energyTransfer", value);
            } else if (source == reproductiveAgeSlider) {
                reproductiveAgeValue.setText(String.valueOf(value));
                SimulationConfig.setReproductiveAge(value);
                notifyConfigChanged("reproductiveAge", value);
            } else if (source == maxAgeSlider) {
                maxAgeValue.setText(String.valueOf(value));
                SimulationConfig.setMaxAge(value);
                notifyConfigChanged("maxAge", value);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            SimulationConfig.resetToDefaults();
            initializeValues();
            notifyConfigChanged("resetToDefaults", 0);
        }
    }
    
    /**
     * Notify listener about configuration changes
     */
    private void notifyConfigChanged(String paramName, int value) {
        logger.debug("Configuration parameter '{}' changed to: {}", paramName, value);
        if (changeListener != null) {
            changeListener.onConfigurationChanged(paramName, value);
        }
    }
    
    /**
     * Interface for receiving configuration change events
     */
    public interface ConfigurationChangeListener {
        void onConfigurationChanged(String paramName, int value);
    }
}
