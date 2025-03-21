# Ecosystem Simulation Game Rules

## 1. Environment
- Grid-based 2D world (30x10 by default)
- Each cell can contain multiple entities (shown as numbers 2,3,4 or ? for more)
- Empty cells are represented by '.' (or empty space when visualized)

## 2. Entities

### 2.1 Animals
- Represented by letters starting from 'A'
- Initial population: 3
- Can move to adjacent cells
- Have energy and age attributes
- Can eat plants to gain energy
- **Duration**: Animals have a maximum age of 8 cycles and die if they reach this age or if their energy depletes.
- **Eating**: Animals gain 3 energy units when eating a plant.
- **Reproduction**: Animals can reproduce when they reach reproductive age (1 cycle) if they meet another animal in an adjacent cell.

### 2.2 Plants
- Represented by letters starting from 'o'
- Initial population: 5
- Static (don't move)
- Have energy attributes
- Can be eaten by animals
- **Duration**: Plants can be eaten by animals, which reduces their population.
- **Reproduction**: Plants do not reproduce. Their population only changes when they are eaten by animals.

## 3. Game Mechanics

### 3.1 Energy System
- Initial energy for all entities: 10
- Maximum energy for animals: Initial + 1
- Maximum energy for plants: Initial + 4
- Energy transfer during animal-plant interaction: 3 units

### 3.2 Life Cycle
- Reproductive age: 1
- Maximum age: 8
- Entities die when reaching maximum age
- Entities die when energy depletes

### 3.3 Movement
- Configurable neighborhood types:
  - **Von Neumann**: 4 adjacent cells (North, South, East, West)
  - **Moore**: 8 adjacent cells (includes diagonal neighbors)
- Animals can move one cell per turn
- Movement order is randomized
- Animals can't move to occupied cells
- Neighborhood type affects both movement and reproduction

### 3.4 Reproduction
- Entities must reach reproductive age
- Requires available adjacent cell
- New entities inherit parent's characteristics
- Population limited by grid size

### 3.5 Spawning Rules
- Can use rectangular areas for spawning
- Default spawn area: from (2,1) to (5,3)
- Option to spawn in lines instead of rectangles

## 4. Simulation Parameters

### 4.1 Time Control
- Maximum simulation duration: 30 cycles
- Pause between cycles: 1000ms (1 second)

### 4.2 Grid Configuration
- Width: 30 cells
- Height: 10 cells
- Maximum grid size: 1000x1000

### 4.3 Population Limits
- Total entities must not exceed grid size
- Animal population limit: (Width × Height) - Plant count

## 5. Controls and Visualization

### 5.1 Control Panel
- **Start/Resume**: Begin simulation or continue from pause
- **Pause**: Temporarily halt the simulation
- **Stop**: End the current simulation run
- **Reset**: Return to initial state
- **Neighborhood**: Toggle between Von Neumann and Moore patterns

### 5.2 Grid Display
- Empty cells: '.'
- Single entity: Entity's symbol
- 2-4 entities: Number of entities
- More than 4: '?'

### 5.3 Statistics Tracking
- Time step
- Animal count
- Plant count
- Birth count
- Death count
- Events (interactions)
- Current neighborhood type

## 6. Termination Conditions
- Maximum time reached (30 cycles)
- Ecosystem becomes unbalanced:
  - No life remaining
  - Population exceeds grid capacity

## 7. Data Output
- **CSV Format**: The simulation outputs data in CSV format for analysis.
- **Columns**:
  - Time step
  - Animal count
  - Plant count
  - Birth count
  - Death count
  - Events (interactions)
- **File Location**: The CSV file is saved in the `simulation_data` directory with the name `ecosystem_stats.csv`.
- **Creation Process**: The CSV file is created upon initializing the `StatisticsCollector` class, which writes the header row to the file. Each cycle, the statistics are appended to the CSV file, capturing the state of the ecosystem at each time step.
- Real-time grid visualization
- Event logging for interactions

## 8. Technical Requirements
- Thread-safe implementation
- Configurable parameters
- Error handling for invalid positions
- Memory-efficient cell management
- Concurrent entity processing
