/**
 * Ecosystem Simulation Launch Handler
 * This script handles the launching of the Ecosystem Simulation Java application
 */

const { exec } = require('child_process');
const path = require('path');
const fs = require('fs');

// Path to the ecosystem simulation project
const ECOSYSTEM_PATH = '/root/CascadeProjects/ISLA/ecosystem-simulation-';

// Function to handle the launch request
function handleLaunch(req, res) {
    console.log('Received request to launch ecosystem simulation');
    
    // Check if the ecosystem simulation path exists
    if (!fs.existsSync(ECOSYSTEM_PATH)) {
        console.error('Ecosystem simulation path does not exist:', ECOSYSTEM_PATH);
        res.writeHead(500, { 'Content-Type': 'text/plain' });
        return res.end('Error: Ecosystem simulation path not found');
    }
    
    // Path to the build and run script
    const scriptPath = path.join(ECOSYSTEM_PATH, 'run.sh');
    
    // Create the run script if it doesn't exist
    if (!fs.existsSync(scriptPath)) {
        console.log('Creating run script at:', scriptPath);
        
        const scriptContent = `#!/bin/bash
cd "${ECOSYSTEM_PATH}"
mvn clean compile assembly:single
java -jar target/ecosystem-simulation-1.0-SNAPSHOT-jar-with-dependencies.jar
`;
        
        try {
            fs.writeFileSync(scriptPath, scriptContent);
            fs.chmodSync(scriptPath, '755'); // Make executable
        } catch (error) {
            console.error('Error creating run script:', error);
            res.writeHead(500, { 'Content-Type': 'text/plain' });
            return res.end('Error creating run script');
        }
    }
    
    // Execute the script
    console.log('Executing script:', scriptPath);
    const child = exec(scriptPath, (error, stdout, stderr) => {
        if (error) {
            console.error('Error executing script:', error);
            console.error('stderr:', stderr);
            // We don't return here because the response has already been sent
        }
        console.log('stdout:', stdout);
    });
    
    // Send response immediately, don't wait for the process to complete
    res.writeHead(200, { 'Content-Type': 'text/plain' });
    res.end('Ecosystem Simulation launched successfully!');
}

module.exports = handleLaunch;
