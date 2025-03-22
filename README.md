# Ecosystem Simulation

**Repository**: [Portfolio-windsurf-march6](https://github.com/HectorCorbellini/Portfolio-windsurf-march6)

## Repository Information
For future commits, please upload to the following GitHub repository using a dedicated branch for the ecosystem simulation project:

```
# Set up the remote repository
git remote add origin https://github.com/HectorCorbellini/Portfolio-windsurf-march6.git

# Create a new branch for the ecosystem simulation project
git checkout -b ecosystem-simulation-clean-code

# Push to the new branch
git push -u origin ecosystem-simulation-clean-code
```

This approach keeps the ecosystem simulation project in its own branch, separate from other projects in the repository.

## Overview
This project simulates an ecosystem with animals and plants, allowing users to visualize interactions between different entities in a grid-based environment. The simulation is designed to demonstrate ecological concepts such as reproduction, energy consumption, and population dynamics.

## Features
- **Grid Visualization**: Displays the ecosystem in a 2D grid format using Swing UI.
- **Entity Types**: Supports two main entity types: Animals and Plants.
- **Interface-Based Design**: Uses interfaces (IEntity, IMovable, IReproducible) and an EntityType enum for better code organization and type safety.
- **Flexible Movement Patterns**: 
  - Von Neumann neighborhood (4 adjacent cells: N, S, E, W)
  - Moore neighborhood (8 adjacent cells: N, S, E, W, NE, NW, SE, SW)
- **Enhanced Controls**:
  - Start/Resume simulation
  - Pause for observation
  - Stop current run
  - Reset to initial state
  - Toggle between neighborhood types
- **Improved UI**:
  - Separate panels for animal and plant parameters
  - Scrollable configuration panel for small screens
  - Click-to-reset view functionality
  - Right-click panning for the simulation grid
- **Configurable Parameters**:
  - Initial energy levels
  - Maximum energy capacities
  - Energy transfer rates
  - Reproductive age thresholds
  - Maximum lifespan
- **Real-time Statistics**: Tracks and displays:
  - Births and deaths
  - Current population counts
  - Simulation cycle

## Clean Code Principles
This project implements several Clean Code principles to improve maintainability and extensibility:

- **Interface-Based Design**: Entities follow a clear interface hierarchy with `IEntity` as the base interface, and specialized interfaces like `IMovable` and `IReproducible` for specific behaviors.

- **Single Responsibility Principle**: Each class and interface has a well-defined responsibility. For example, movement logic is separated into the `IMovable` interface.

- **Type Safety**: Uses an `EntityType` enum instead of character literals to represent different entity types, reducing errors and improving code readability.

- **Separation of Concerns**: UI components are separated from simulation logic, and configuration is managed independently.

- **Improved Encapsulation**: Entity behaviors are properly encapsulated within their respective classes and interfaces.

## Structure
- **src/main/java/com/isla/ecosystem/**
  - `Main.java`: Application entry point
  - **config/**
    - `SimulationConfig.java`: Configuration parameters and constants
  - **core/**
    - `SimulationController.java`: Core simulation logic and lifecycle management
    - `StatisticsCollector.java`: Tracks simulation statistics
  - **entity/**
    - `IEntity.java`: Interface defining common entity behavior
    - `IMovable.java`: Interface for entities that can move
    - `IReproducible.java`: Interface for entities that can reproduce
    - `EntityType.java`: Enum representing different entity types
    - `Entity.java`: Base class implementing the IEntity interface
    - `Animal.java`: Animal behavior implementing IMovable and IReproducible
    - `Plant.java`: Plant behavior implementing IEntity
  - **grid/**
    - `Grid.java`: Thread-safe grid environment
    - `Cell.java`: Individual grid cell management
  - **ui/swing/**
    - `SimulationFrame.java`: Main UI window and controls
    - `SimulationPanel.java`: Grid visualization
    - `ConfigurationPanel.java`: Parameter adjustment controls

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
   - **Configuration Sliders**: Adjust ecosystem parameters to find a stable balance

3. **Monitoring**:
   - Watch entity interactions in the grid
   - Track population statistics
   - Observe movement patterns

## Technical Details
- Thread-safe implementation for concurrent entity processing
- Runtime-configurable simulation parameters via sliders
- Parameter validation with min/max constraints
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
