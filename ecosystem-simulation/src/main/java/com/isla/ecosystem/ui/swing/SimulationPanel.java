package com.isla.ecosystem.ui.swing;

import com.isla.ecosystem.config.SimulationConfig;
import com.isla.ecosystem.entity.Animal;
import com.isla.ecosystem.entity.Entity;
import com.isla.ecosystem.entity.Plant;
import com.isla.ecosystem.grid.Cell;
import com.isla.ecosystem.grid.Grid;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel for rendering the simulation grid using Swing graphics
 */
public class SimulationPanel extends JPanel {
    private static final int CELL_SIZE = 20;
    private static final int PADDING = 10;
    
    private Grid grid;
    private int cycle;
    private int animalCount;
    private int plantCount;
    
    public SimulationPanel() {
        setBackground(Color.WHITE);
    }
    
    public void updateState(Grid grid, int cycle, int animalCount, int plantCount) {
        this.grid = grid;
        this.cycle = cycle;
        this.animalCount = animalCount;
        this.plantCount = plantCount;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (grid == null) {
            drawPlaceholder(g);
            return;
        }
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = grid.getWidth();
        int height = grid.getHeight();
        
        // Draw grid cells
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                drawCell(g2d, x, y);
            }
        }
        
        // Draw grid lines
        g2d.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x <= width; x++) {
            g2d.drawLine(
                PADDING + x * CELL_SIZE, 
                PADDING, 
                PADDING + x * CELL_SIZE, 
                PADDING + height * CELL_SIZE
            );
        }
        
        for (int y = 0; y <= height; y++) {
            g2d.drawLine(
                PADDING, 
                PADDING + y * CELL_SIZE, 
                PADDING + width * CELL_SIZE, 
                PADDING + y * CELL_SIZE
            );
        }
        
        // Draw stats
        drawStats(g2d);
    }
    
    private void drawCell(Graphics2D g2d, int x, int y) {
        Cell cell = grid.getCell(x, y);
        List<Entity> entities = cell.getEntities();
        
        // Draw cell background
        if (entities.isEmpty()) {
            g2d.setColor(new Color(240, 240, 240));
        } else {
            // Count entities by type
            int animalCount = 0;
            int plantCount = 0;
            
            for (Entity entity : entities) {
                if (entity instanceof Animal) {
                    animalCount++;
                } else if (entity instanceof Plant) {
                    plantCount++;
                }
            }
            
            // Set background color based on contents
            if (animalCount > 0 && plantCount > 0) {
                // Mixed entities - orange
                g2d.setColor(new Color(255, 200, 100));
            } else if (animalCount > 0) {
                // Animals only - blue
                g2d.setColor(new Color(173, 216, 230));
            } else {
                // Plants only - green
                g2d.setColor(new Color(144, 238, 144));
            }
        }
        
        g2d.fillRect(
            PADDING + x * CELL_SIZE, 
            PADDING + y * CELL_SIZE, 
            CELL_SIZE, 
            CELL_SIZE
        );
        
        // Draw entity representation
        if (!entities.isEmpty()) {
            if (entities.size() <= 1) {
                // Single entity - draw its symbol
                Entity entity = entities.get(0);
                drawEntitySymbol(g2d, entity, x, y);
            } else {
                // Multiple entities - draw count
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
                String text = String.valueOf(entities.size());
                
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();
                
                g2d.drawString(
                    text,
                    PADDING + x * CELL_SIZE + (CELL_SIZE - textWidth) / 2,
                    PADDING + y * CELL_SIZE + (CELL_SIZE + textHeight / 2) / 2
                );
            }
        }
    }
    
    private void drawEntitySymbol(Graphics2D g2d, Entity entity, int x, int y) {
        int centerX = PADDING + x * CELL_SIZE + CELL_SIZE / 2;
        int centerY = PADDING + y * CELL_SIZE + CELL_SIZE / 2;
        
        if (entity instanceof Animal) {
            // Animal - draw a filled circle
            g2d.setColor(Color.BLUE);
            g2d.fillOval(
                centerX - CELL_SIZE / 3,
                centerY - CELL_SIZE / 3,
                CELL_SIZE / 3 * 2,
                CELL_SIZE / 3 * 2
            );
        } else if (entity instanceof Plant) {
            // Plant - draw a star/asterisk
            g2d.setColor(new Color(0, 100, 0));
            int size = CELL_SIZE / 3;
            
            g2d.drawLine(centerX, centerY - size, centerX, centerY + size);
            g2d.drawLine(centerX - size, centerY, centerX + size, centerY);
            g2d.drawLine(centerX - size * 2/3, centerY - size * 2/3, 
                        centerX + size * 2/3, centerY + size * 2/3);
            g2d.drawLine(centerX - size * 2/3, centerY + size * 2/3, 
                        centerX + size * 2/3, centerY - size * 2/3);
        }
    }
    
    private void drawStats(Graphics2D g2d) {
        int width = grid.getWidth();
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        String stats = String.format("Cycle: %d | Animals: %d | Plants: %d", 
                                     cycle, animalCount, plantCount);
        
        g2d.drawString(stats, PADDING, PADDING + grid.getHeight() * CELL_SIZE + 25);
    }
    
    private void drawPlaceholder(Graphics g) {
        g.setColor(Color.GRAY);
        g.setFont(new Font("SansSerif", Font.ITALIC, 16));
        g.drawString("Simulation not started", 
                    getWidth() / 2 - 80, getHeight() / 2);
    }
    
    @Override
    public Dimension getPreferredSize() {
        if (grid == null) {
            return new Dimension(400, 300);
        }
        
        return new Dimension(
            PADDING * 2 + grid.getWidth() * CELL_SIZE,
            PADDING * 2 + grid.getHeight() * CELL_SIZE + 40
        );
    }
}
