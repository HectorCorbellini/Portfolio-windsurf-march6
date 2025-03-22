package com.isla.ecosystem.ui.swing.components;

import com.isla.ecosystem.config.SimulationConfig;
import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

public class InitialsDialog extends JDialog {
    private final JSpinner animalSpinner;
    private final JSpinner plantSpinner;
    private BiConsumer<Integer, Integer> onConfirm;

    public InitialsDialog(Frame owner) {
        super(owner, "Change Initial Counts", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Animal count
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Initial Animals:"), gbc);

        gbc.gridx = 1;
        animalSpinner = new JSpinner(new SpinnerNumberModel(
            SimulationConfig.getInitialAnimalCount(), 1, 100, 1));
        add(animalSpinner, gbc);

        // Plant count
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Initial Plants:"), gbc);

        gbc.gridx = 1;
        plantSpinner = new JSpinner(new SpinnerNumberModel(
            SimulationConfig.getInitialPlantCount(), 1, 100, 1));
        add(plantSpinner, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            if (onConfirm != null) {
                onConfirm.accept(
                    (Integer) animalSpinner.getValue(),
                    (Integer) plantSpinner.getValue()
                );
            }
            dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        pack();
        setLocationRelativeTo(owner);
    }

    public void setOnConfirm(BiConsumer<Integer, Integer> onConfirm) {
        this.onConfirm = onConfirm;
    }
}
