# Ecosystem Simulation

A Java-based ecosystem simulation that demonstrates the interactions between animals and plants in a grid environment. This project showcases clean code principles, object-oriented design, and modern Java practices.

## Overview
This project simulates an ecosystem with animals and plants, allowing users to visualize interactions between different entities in a grid-based environment. The simulation demonstrates ecological concepts such as reproduction, energy consumption, and population dynamics.

## Features
- **Grid Visualization**: 
  - Dynamic grid display with color-coded cells
  - Blue circles for animals
  - Green asterisks for plants
  - Orange cells for mixed populations
  - Entity count display for multiple entities
  - Smooth graphics with anti-aliasing
  
- **Enhanced Controls**:
  - Intuitive control panel for population settings
  - Start/Pause/Resume simulation
  - Stop current run
  - Reset to initial state
  - Toggle between neighborhood types
  - Real-time statistics display
  
- **Configuration Panel**:
  - Separate sections for animal and plant parameters
  - Adjustable sliders with immediate feedback
  - Parameter validation and constraints
  - Reset to defaults option
  
- **Statistics and Monitoring**:
  - Real-time population tracking
  - Birth and death counters
  - CSV export for data analysis
  - Cycle counter display
  - Grid state visualization

## New Features
- **Change Initials Functionality**:
  - Users can now modify the initial counts of animals and plants through a dedicated dialog.
  - The dialog allows setting the initial population counts before starting the simulation.

## Clean Code Implementation
The project follows clean code principles and SOLID design:

1. **Single Responsibility Principle**:
   - `GridPanel`: Handles only grid visualization
   - `ControlPanel`: Manages UI controls
   - `SimulationPanel`: Coordinates components
   
2. **Interface Segregation**:
   - Clear separation between UI components
   - Well-defined interfaces for entities
   - Specialized behaviors through interfaces
   
3. **Component-Based Architecture**:
   - Modular design with independent components
   - Clear communication channels
   - Easy to extend and modify

## Project Structure
```
src/main/java/com/isla/ecosystem/
├── Main.java                    # Application entry point
├── config/
│   └── SimulationConfig.java    # Configuration parameters
├── core/
│   ├── SimulationController.java # Core simulation logic
│   └── StatisticsCollector.java # Data collection
├── entity/
│   ├── interfaces/             # Entity behavior interfaces
│   ├── Animal.java            # Animal implementation
│   └── Plant.java            # Plant implementation
├── grid/
│   ├── Grid.java             # Thread-safe grid environment
│   └── Cell.java            # Cell management
└── ui/
    └── swing/
        ├── components/       # UI components
        │   ├── ControlPanel.java
        │   └── GridPanel.java
        ├── SimulationFrame.java
        └── SimulationPanel.java
```

## Requirements
- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher
- Minimum screen resolution: 1024x768

## Building and Running
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd ecosystem-simulation
   ```

2. Build with Maven:
   ```bash
   mvn clean package
   ```

3. Run the simulation:
   ```bash
   ./run.sh
   ```
   Or manually:
   ```bash
   java -jar target/ecosystem-simulation-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

## Usage Guide
1. **Initial Setup**:
   - Use the "Animals" and "Plants" buttons to set initial populations
   - Adjust configuration parameters using the sliders
   - Select neighborhood type (Von Neumann/Moore)

2. **Running the Simulation**:
   - Click "Start" to begin
   - Use "Pause" to freeze the simulation
   - "Stop" to end the current run
   - "Reset" to return to initial state

3. **Monitoring**:
   - Watch the grid for entity interactions
   - Check statistics in the bottom panel
   - View detailed stats using the "Show Stats" button
   - Export data to CSV for analysis

## Contributing
Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Commit your changes
5. Push to the branch
6. Create a pull request for your changes:
   - Visit the following link to create a pull request: [Create a Pull Request](https://github.com/HectorCorbellini/Portfolio-windsurf-march6/pull/new/ecosystem-simulation-clean-code)

## License
This project is licensed under the MIT License - see the LICENSE file for details.
