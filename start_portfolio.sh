#!/bin/bash

# Set error handling
set -e

# Change to the portfolio directory
cd "$(dirname "$0")"

echo "Starting Portfolio Website Setup..."

# Check if npm is installed
if ! command -v npm &> /dev/null; then
    echo "Error: npm is not installed. Please install Node.js and npm first."
    exit 1
fi

# Kill any existing process using port 3001
echo "Checking for existing processes on port 3001..."
if command -v fuser &> /dev/null; then
    fuser -k 3001/tcp 2>/dev/null || true
elif command -v lsof &> /dev/null; then
    # Alternative using lsof if fuser is not available
    kill $(lsof -t -i:3001) 2>/dev/null || true
fi

# Small delay to ensure port is released
sleep 1

# Install dependencies if node_modules doesn't exist
if [ ! -d "node_modules" ]; then
    echo "Installing dependencies..."
    npm install
fi

# Setup signal handling for graceful shutdown
function cleanup() {
    echo "Shutting down Portfolio Website..."
    # This will kill any background processes started by this script
    kill $(jobs -p) 2>/dev/null || true
    exit 0
}

# Register the cleanup function for these signals
trap cleanup SIGINT SIGTERM

# Set NODE_OPTIONS to limit memory usage and prevent unexpected terminations
export NODE_OPTIONS="--max-old-space-size=512"

# Start the server
echo "Starting Portfolio Website..."
echo "Once the server starts, open your browser and go to: http://localhost:3001"

# Use exec to replace the current process with npm
# This ensures signals are properly passed through
exec npm run start

# Note: The code below will not execute because of exec,
# but it's kept as a fallback in case exec is removed
npm run start

# Keep the script running until manually terminated
# wait
