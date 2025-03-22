# Ecosystem Simulation Game Rules

## 1. Environment
- Grid-based 2D world with scrollable view
- Color-coded cells for different entity types:
  - Gray: Empty cells
  - Blue: Cells with animals
  - Green: Cells with plants
  - Orange: Cells with both animals and plants
- Multiple entities per cell supported (shown as numbers)
- Anti-aliased graphics for smooth visualization

## 2. Entity Types

### 2.1 Animals
- Represented by blue circles
- Can move to adjacent cells
- Have energy and age attributes
- Can eat plants to gain energy
- **Attributes**:
  - Initial energy: Configurable (5-20)
  - Maximum energy: Configurable (5-30)
  - Maximum age: Configurable (3-20)
  - Reproductive age: Configurable (1-5)
- **Behavior**:
  - Move randomly to adjacent cells
  - Consume plants for energy
  - Reproduce when conditions are met
  - Die when energy depletes or max age reached

### 2.2 Plants
- Represented by green asterisks
- Static (don't move)
- Serve as food source for animals
- **Attributes**:
  - Initial energy: Configurable (5-20)
  - Maximum energy: Configurable (5-30)
  - Maximum age: Configurable (3-20)
- **Behavior**:
  - Remain in place
  - Can be consumed by animals
  - Die when energy depletes or max age reached

## 3. Game Mechanics

### 3.1 Movement System
- Two neighborhood types:
  - **Von Neumann**: 4 adjacent cells (N, S, E, W)
  - **Moore**: 8 adjacent cells (includes diagonals)
- Toggle between types during setup
- Animals move one cell per cycle
- Random movement direction within neighborhood

### 3.2 Energy System
- All entities start with configurable initial energy
- Energy transfer during consumption is configurable
- Maximum energy limits prevent overpowered entities
- Energy depletion leads to death

### 3.3 Reproduction
- Animals must reach reproductive age
- Requires another animal in neighborhood
- Energy cost for reproduction
- Population limited by available space

## 4. User Interface

### 4.1 Control Panel
- **Population Controls**:
  - "Animals" button: Set initial animal count
  - "Plants" button: Set initial plant count
  - Input validation with min/max limits
  
- **Simulation Controls**:
  - Start/Pause/Resume button
  - Stop button
  - Reset button
  - Neighborhood type toggle
  - Show Stats button

### 4.2 Configuration Panel
- **Animal Parameters**:
  - Initial Energy slider
  - Maximum Energy slider
  - Energy Transfer slider
  - Reproductive Age slider
  - Maximum Age slider
  
- **Plant Parameters**:
  - Initial Energy slider
  - Maximum Energy slider
  - Maximum Age slider
  
- **Reset to Defaults** button

### 4.3 Grid Display
- Scrollable view for large grids
- Color-coded cells for entity types
- Entity count display for multiple entities
- Smooth graphics with anti-aliasing
- Real-time updates

### 4.4 Statistics Display
- Current cycle counter
- Animal population counter
- Plant population counter
- Birth/Death statistics
- CSV data export

## 5. Data Collection

### 5.1 Real-time Statistics
- Population counts
- Birth/Death events
- Current cycle
- Entity interactions

### 5.2 CSV Export
- File: `simulation_data/ecosystem_stats.csv`
- Format: Cycle;Animals;Plants;Births;Deaths
- View using "Show Stats" button
- Data preserved between runs

## 6. Configuration Limits

### 6.1 Population
- Minimum Animals: 1
- Maximum Animals: Grid size - Plants
- Minimum Plants: 1
- Maximum Plants: Grid size - Animals

### 6.2 Parameters
- Initial Energy: 5-20
- Maximum Energy: 5-30
- Energy Transfer: 1-10
- Reproductive Age: 1-5
- Maximum Age: 3-20

## 7. Technical Notes

### 7.1 Performance
- Thread-safe implementation
- Efficient grid rendering
- Smooth UI updates
- Memory-efficient entity management

### 7.2 Error Handling
- Input validation for all parameters
- Graceful handling of invalid states
- Clear error messages
- Safe state recovery

## 8. Tips for Success
1. Balance initial populations
2. Monitor energy transfer rates
3. Adjust reproductive parameters
4. Consider neighborhood type impact
5. Use statistics for optimization
6. Export data for analysis
