/**
 * Code Transformer Launch Handler
 * This script handles the launching of the Code Transformer Java application
 */

const { exec } = require('child_process');
const path = require('path');
const fs = require('fs');
const { logger } = require('../../server/utils/logger');

// Path to the Code Transformer project
const CODE_TRANSFORMER_PATH = '/root/CascadeProjects/CODES_JOIN/CodesJOINER-Java-';
const JAR_PATH = path.join(CODE_TRANSFORMER_PATH, 'target/code-transformer-1.0-SNAPSHOT-jar-with-dependencies.jar');

/**
 * Handle the launch request for the Code Transformer
 * @param {Object} req - HTTP request object
 * @param {Object} res - HTTP response object
 */
async function handleLaunch(req, res) {
    logger('info', 'Launching Code Transformer');
    
    // Check if the Code Transformer path exists
    if (!fs.existsSync(CODE_TRANSFORMER_PATH)) {
        logger('error', `Code Transformer path does not exist: ${CODE_TRANSFORMER_PATH}`);
        res.writeHead(500, { 'Content-Type': 'application/json' });
        return res.end(JSON.stringify({ 
            success: false, 
            message: 'Code Transformer path not found' 
        }));
    }
    
    // Check if JAR file exists
    if (!fs.existsSync(JAR_PATH)) {
        logger('error', `Code Transformer JAR not found at: ${JAR_PATH}`);
        res.writeHead(404, { 'Content-Type': 'application/json' });
        return res.end(JSON.stringify({ 
            success: false, 
            message: 'Code Transformer JAR not found' 
        }));
    }
    
    // Path to the launch script
    const scriptPath = path.join(CODE_TRANSFORMER_PATH, 'run_code_transformer.sh');
    
    // Create the run script if it doesn't exist
    if (!fs.existsSync(scriptPath)) {
        logger('info', `Creating run script at: ${scriptPath}`);
        
        const scriptContent = `#!/bin/bash
cd "${CODE_TRANSFORMER_PATH}"
export DISPLAY=:0
java -jar "${JAR_PATH}"
`;
        
        try {
            fs.writeFileSync(scriptPath, scriptContent);
            fs.chmodSync(scriptPath, '755'); // Make executable
        } catch (error) {
            logger('error', `Error creating run script: ${error.message}`);
            res.writeHead(500, { 'Content-Type': 'application/json' });
            return res.end(JSON.stringify({ 
                success: false, 
                message: 'Error creating run script' 
            }));
        }
    }
    
    // Execute the script
    logger('info', `Executing script: ${scriptPath}`);
    const child = exec(`bash ${scriptPath}`, (error, stdout, stderr) => {
        if (error) {
            logger('error', `Error executing script: ${error.message}`);
            logger('error', `stderr: ${stderr}`);
            // We don't return here because the response has already been sent
        }
        logger('info', `stdout: ${stdout}`);
    });
    
    // Send response immediately, don't wait for the process to complete
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({ 
        success: true, 
        message: 'Code Transformer launched successfully!' 
    }));
}

module.exports = handleLaunch; 