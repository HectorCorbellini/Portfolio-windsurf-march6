/**
 * Port Manager Utility
 * Handles port allocation and cleanup for the server
 */

const net = require('net');
const { exec } = require('child_process');

class PortManager {
    constructor(startPort = 3000, endPort = 3010) {
        this.startPort = startPort;
        this.endPort = endPort;
        this.currentPort = startPort;
    }

    /**
     * Checks if a port is in use
     * @param {number} port - Port to check
     * @returns {Promise<boolean>} True if port is in use
     */
    isPortInUse(port) {
        return new Promise((resolve) => {
            const server = net.createServer()
                .once('error', () => resolve(true))
                .once('listening', () => {
                    server.close();
                    resolve(false);
                })
                .listen(port);
        });
    }

    /**
     * Kills any process running on the specified port
     * @param {number} port - Port to kill process on
     * @returns {Promise<void>}
     */
    async killProcessOnPort(port) {
        return new Promise((resolve, reject) => {
            exec(`lsof -i :${port} | grep LISTEN | awk '{print $2}' | xargs kill -9`, (error) => {
                // Ignore errors as they likely mean no process was found
                resolve();
            });
        });
    }

    /**
     * Gets the next available port
     * @returns {Promise<number>} Available port number
     * @throws {Error} If no ports are available
     */
    async getNextAvailablePort() {
        for (let port = this.startPort; port <= this.endPort; port++) {
            const inUse = await this.isPortInUse(port);
            if (inUse) {
                await this.killProcessOnPort(port);
                // Double check the port is now available
                if (await this.isPortInUse(port)) {
                    continue;
                }
            }
            this.currentPort = port;
            return port;
        }
        throw new Error('No available ports in the specified range');
    }
}

module.exports = PortManager;
