# Ecosystem Simulation

## Overview
This project simulates an ecosystem with animals and plants, allowing users to visualize interactions between different entities in a grid-based environment. The simulation is designed to demonstrate ecological concepts such as reproduction, energy consumption, and population dynamics.

## Features
- **Grid Visualization**: Displays the ecosystem in a 2D grid format using Swing UI.
- **Entity Types**: Supports two main entity types: Animals and Plants.
- **Flexible Movement Patterns**: 
  - Von Neumann neighborhood (4 adjacent cells: N, S, E, W)
  - Moore neighborhood (8 adjacent cells: N, S, E, W, NE, NW, SE, SW)
- **Enhanced Controls**:
  - Start/Resume simulation
  - Pause for observation
  - Stop current run
  - Reset to initial state
  - Toggle between neighborhood types
- **Real-time Statistics**: Tracks and displays:
  - Births and deaths
  - Current population counts
  - Simulation cycle

## Structure
- **src/main/java/com/isla/ecosystem/**
  - `Main.java`: Application entry point
  - **config/**
    - `SimulationConfig.java`: Configuration parameters and constants
  - **core/**
    - `SimulationController.java`: Core simulation logic and lifecycle management
    - `StatisticsCollector.java`: Tracks simulation statistics
  - **entity/**
    - `Entity.java`: Base class for simulation entities
    - `Animal.java`: Animal behavior and attributes
    - `Plant.java`: Plant behavior and attributes
  - **grid/**
    - `Grid.java`: Thread-safe grid environment
    - `Cell.java`: Individual grid cell management
  - **ui/swing/**
    - `SimulationFrame.java`: Main UI window and controls
    - `SimulationPanel.java`: Grid visualization

## Requirements
- Java Development Kit (JDK) 17 or higher
- Maven for dependency management and build automation
- Minimum screen resolution: 800x600

## Running the Simulation
1. Clone the repository
2. Compile the project:
   ```bash
   mvn compile
   ```
3. Run the simulation:
   ```bash
   mvn exec:java
   ```

## Using the Simulation
1. **Initial Setup**:
   - Select neighborhood type (Von Neumann/Moore) before starting
   - Review the initial grid state

2. **Controls**:
   - **Start/Resume**: Begin simulation or continue from pause
   - **Pause**: Temporarily halt for observation
   - **Stop**: End current simulation run
   - **Reset**: Return to initial state
   - **Neighborhood**: Toggle between movement patterns

3. **Monitoring**:
   - Watch entity interactions in the grid
   - Track population statistics
   - Observe movement patterns

## Technical Details
- Thread-safe implementation for concurrent entity processing
- Configurable simulation parameters via `SimulationConfig`
- Error handling for invalid positions and states
- Memory-efficient cell management using `CopyOnWriteArrayList`

## Future Enhancements
- Custom grid size configuration
- Save/load simulation states
- Multiple species support
- Environmental factors (temperature, resources)
- Advanced statistics and graphs
- Export simulation data to CSV

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.
