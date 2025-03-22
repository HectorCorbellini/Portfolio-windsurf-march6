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
    private JSlider animalInitialEnergySlider;
    private JSlider plantInitialEnergySlider;
    private JSlider maxAnimalEnergySlider;
    private JSlider maxPlantEnergySlider;
    private JSlider animalEnergyTransferSlider;
    private JSlider animalReproductiveAgeSlider;
    private JSlider maxAnimalAgeSlider;
    private JSlider maxPlantAgeSlider;
    private JButton resetButton;

    // Value labels
    private JLabel animalInitialEnergyValue;
    private JLabel plantInitialEnergyValue;
    private JLabel maxAnimalEnergyValue;
    private JLabel maxPlantEnergyValue;
    private JLabel animalEnergyTransferValue;
    private JLabel animalReproductiveAgeValue;
    private JLabel maxAnimalAgeValue;
    private JLabel maxPlantAgeValue;

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
        animalInitialEnergySlider = createSlider(
                SimulationConfig.MIN_INITIAL_ENERGY, 
                SimulationConfig.MAX_INITIAL_ENERGY, 
                SimulationConfig.getInitialEnergy());
        
        plantInitialEnergySlider = createSlider(
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
        
        animalEnergyTransferSlider = createSlider(
                SimulationConfig.MIN_ENERGY_TRANSFER, 
                SimulationConfig.MAX_ENERGY_TRANSFER, 
                SimulationConfig.getEnergyTransfer());
        
        animalReproductiveAgeSlider = createSlider(
                SimulationConfig.MIN_REPRODUCTIVE_AGE, 
                SimulationConfig.MAX_REPRODUCTIVE_AGE, 
                SimulationConfig.getReproductiveAge());
        
        maxAnimalAgeSlider = createSlider(
                SimulationConfig.MIN_MAX_AGE, 
                SimulationConfig.MAX_MAX_AGE, 
                SimulationConfig.getMaxAge());
        
        maxPlantAgeSlider = createSlider(
                SimulationConfig.MIN_MAX_AGE, 
                SimulationConfig.MAX_MAX_AGE, 
                SimulationConfig.getMaxAge());

        // Create value labels
        animalInitialEnergyValue = new JLabel(String.valueOf(SimulationConfig.getInitialEnergy()));
        plantInitialEnergyValue = new JLabel(String.valueOf(SimulationConfig.getInitialEnergy()));
        maxAnimalEnergyValue = new JLabel(String.valueOf(SimulationConfig.getMaxAnimalEnergy()));
        maxPlantEnergyValue = new JLabel(String.valueOf(SimulationConfig.getMaxPlantEnergy()));
        animalEnergyTransferValue = new JLabel(String.valueOf(SimulationConfig.getEnergyTransfer()));
        animalReproductiveAgeValue = new JLabel(String.valueOf(SimulationConfig.getReproductiveAge()));
        maxAnimalAgeValue = new JLabel(String.valueOf(SimulationConfig.getMaxAge()));
        maxPlantAgeValue = new JLabel(String.valueOf(SimulationConfig.getMaxAge()));

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
        // Create two panels for animal and plant parameters
        JPanel animalPanel = new JPanel();
        animalPanel.setLayout(new BoxLayout(animalPanel, BoxLayout.Y_AXIS));
        animalPanel.setBorder(BorderFactory.createTitledBorder("Animal Parameters"));
        
        JPanel plantPanel = new JPanel();
        plantPanel.setLayout(new BoxLayout(plantPanel, BoxLayout.Y_AXIS));
        plantPanel.setBorder(BorderFactory.createTitledBorder("Plant Parameters"));
        
        // Add animal sliders to animal panel
        addCompactSliderRow(animalPanel, "Initial Energy:", animalInitialEnergySlider, animalInitialEnergyValue);
        addCompactSliderRow(animalPanel, "Max Energy:", maxAnimalEnergySlider, maxAnimalEnergyValue);
        addCompactSliderRow(animalPanel, "Energy Transfer:", animalEnergyTransferSlider, animalEnergyTransferValue);
        addCompactSliderRow(animalPanel, "Reproductive Age:", animalReproductiveAgeSlider, animalReproductiveAgeValue);
        addCompactSliderRow(animalPanel, "Max Age:", maxAnimalAgeSlider, maxAnimalAgeValue);
        
        // Add plant sliders to plant panel
        addCompactSliderRow(plantPanel, "Initial Energy:", plantInitialEnergySlider, plantInitialEnergyValue);
        addCompactSliderRow(plantPanel, "Max Energy:", maxPlantEnergySlider, maxPlantEnergyValue);
        addCompactSliderRow(plantPanel, "Max Age:", maxPlantAgeSlider, maxPlantAgeValue);
        
        // Main content panel to hold both animal and plant panels
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.add(animalPanel);
        contentPanel.add(Box.createHorizontalStrut(10)); // Add spacing between panels
        contentPanel.add(plantPanel);
        
        // Create a scroll pane to contain the content panel
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Create a panel for the button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(resetButton);

        // Create a main container with vertical BoxLayout
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        
        // Add components to the main container
        mainContainer.add(scrollPane);
        mainContainer.add(buttonPanel);
        
        // Add the main container to this panel
        add(mainContainer, BorderLayout.CENTER);
    }


    
    /**
     * Helper method to add a compact labeled slider row
     */
    private void addCompactSliderRow(JPanel panel, String labelText, JSlider slider, JLabel valueLabel) {
        JPanel rowPanel = new JPanel(new BorderLayout(5, 0));
        
        // Create a panel for the label
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        labelPanel.add(new JLabel(labelText));
        
        // Create a panel for the value
        JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        valuePanel.add(valueLabel);
        
        // Add label and value to the top of the row
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(labelPanel, BorderLayout.WEST);
        topPanel.add(valuePanel, BorderLayout.EAST);
        
        // Add the top panel and slider to the row panel
        rowPanel.add(topPanel, BorderLayout.NORTH);
        rowPanel.add(slider, BorderLayout.CENTER);
        
        // Add the row panel to the parent panel
        panel.add(rowPanel);
    }

    /**
     * Set initial values for all components
     */
    private void initializeValues() {
        updateSliderValue(animalInitialEnergySlider, animalInitialEnergyValue, SimulationConfig.getInitialEnergy());
        updateSliderValue(plantInitialEnergySlider, plantInitialEnergyValue, SimulationConfig.getInitialEnergy());
        updateSliderValue(maxAnimalEnergySlider, maxAnimalEnergyValue, SimulationConfig.getMaxAnimalEnergy());
        updateSliderValue(maxPlantEnergySlider, maxPlantEnergyValue, SimulationConfig.getMaxPlantEnergy());
        updateSliderValue(animalEnergyTransferSlider, animalEnergyTransferValue, SimulationConfig.getEnergyTransfer());
        updateSliderValue(animalReproductiveAgeSlider, animalReproductiveAgeValue, SimulationConfig.getReproductiveAge());
        updateSliderValue(maxAnimalAgeSlider, maxAnimalAgeValue, SimulationConfig.getMaxAge());
        updateSliderValue(maxPlantAgeSlider, maxPlantAgeValue, SimulationConfig.getMaxAge());
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
            
            if (source == animalInitialEnergySlider) {
                animalInitialEnergyValue.setText(String.valueOf(value));
                SimulationConfig.setInitialEnergy(value);
                notifyConfigChanged("animalInitialEnergy", value);
            } else if (source == plantInitialEnergySlider) {
                plantInitialEnergyValue.setText(String.valueOf(value));
                SimulationConfig.setInitialEnergy(value);
                notifyConfigChanged("plantInitialEnergy", value);
            } else if (source == maxAnimalEnergySlider) {
                maxAnimalEnergyValue.setText(String.valueOf(value));
                SimulationConfig.setMaxAnimalEnergy(value);
                notifyConfigChanged("maxAnimalEnergy", value);
            } else if (source == maxPlantEnergySlider) {
                maxPlantEnergyValue.setText(String.valueOf(value));
                SimulationConfig.setMaxPlantEnergy(value);
                notifyConfigChanged("maxPlantEnergy", value);
            } else if (source == animalEnergyTransferSlider) {
                animalEnergyTransferValue.setText(String.valueOf(value));
                SimulationConfig.setEnergyTransfer(value);
                notifyConfigChanged("animalEnergyTransfer", value);
            } else if (source == animalReproductiveAgeSlider) {
                animalReproductiveAgeValue.setText(String.valueOf(value));
                SimulationConfig.setReproductiveAge(value);
                notifyConfigChanged("animalReproductiveAge", value);
            } else if (source == maxAnimalAgeSlider) {
                maxAnimalAgeValue.setText(String.valueOf(value));
                SimulationConfig.setMaxAge(value);
                notifyConfigChanged("maxAnimalAge", value);
            } else if (source == maxPlantAgeSlider) {
                maxPlantAgeValue.setText(String.valueOf(value));
                SimulationConfig.setMaxAge(value);
                notifyConfigChanged("maxPlantAge", value);
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
