/**
 * Portfolio Website Server
 * This is a simple HTTP server that serves static files for the portfolio website.
 * It handles basic routing and mime-type detection for various file types.
 */

const http = require('http');
<<<<<<< HEAD
const { ErrorTypes, AppError, logError, createErrorResponse } = require('./utils/errorHandler');
const MiddlewareManager = require('./middleware/middlewareManager');
const { requestLogger, corsMiddleware, securityMiddleware } = require('./middleware/common');
const apiHandler = require('./middleware/apiHandler');
const StaticHandler = require('./handlers/staticHandler');
const PortManager = require('./utils/portManager');

class PortfolioServer {
    constructor() {
        this.middlewareManager = new MiddlewareManager();
        this.staticHandler = new StaticHandler();
        this.portManager = new PortManager(3000, 3010);
        this.setupMiddleware();
    }

    /**
     * Set up middleware chain
     */
    setupMiddleware() {
        this.middlewareManager.use(requestLogger);
        this.middlewareManager.use(corsMiddleware);
        this.middlewareManager.use(securityMiddleware);
        this.middlewareManager.use(apiHandler); // Add API handler before static files
        this.middlewareManager.use(async (req, res) => {
            await this.staticHandler.handle(req, res);
        });
    }

    /**
     * Handle incoming requests
     * @param {http.IncomingMessage} req - Request object
     * @param {http.ServerResponse} res - Response object
     */
    async handleRequest(req, res) {
        try {
            // Execute middleware chain
            await this.middlewareManager.execute(req, res);
        } catch (error) {
            this.handleError(res, error);
        }
    }

    /**
     * Handle errors
     * @param {http.ServerResponse} res - Response object
     * @param {Error} error - Error object
     */
    handleError(res, error) {
        const appError = error instanceof AppError
            ? error
            : new AppError(ErrorTypes.SERVER_ERROR, 'Internal Server Error', error);
        
        logError(appError);
        const { statusCode, headers, body } = createErrorResponse(appError);
        res.writeHead(statusCode, headers);
        res.end(JSON.stringify(body));
    }

    /**
     * Start the server
     * @returns {Promise<void>}
     */
    async start() {
        try {
            const port = await this.portManager.getNextAvailablePort();
            const server = http.createServer(this.handleRequest.bind(this));
            
            server.listen(port, () => {
                console.log(`Server is running on port ${port}`);
                console.log(`Access the portfolio at http://localhost:${port}`);
                console.log('Press Ctrl+C to stop the server');
            });

            // Handle server errors
            server.on('error', (error) => {
                console.error('Server error:', error);
                process.exit(1);
            });
        } catch (error) {
            console.error('Failed to start server:', error);
            process.exit(1);
        }
    }
}

// Create and start server
const server = new PortfolioServer();
server.start();
=======
const fs = require('fs');
const path = require('path');
const url = require('url');

const PORT = process.env.PORT || 3001;

// Define MIME types for file extensions
const MIME_TYPES = {
    '.html': 'text/html',
    '.css': 'text/css',
    '.js': 'text/javascript',
    '.json': 'application/json',
    '.png': 'image/png',
    '.jpg': 'image/jpeg',
    '.jpeg': 'image/jpeg',
    '.gif': 'image/gif',
    '.svg': 'image/svg+xml',
    '.ico': 'image/x-icon',
};

/**
 * Logger utility for consistent logging format
 * @param {string} level - Log level (info, warn, error)
 * @param {string} message - Log message
 * @param {Object} [data] - Optional data to include in log
 */
function logger(level, message, data) {
    const timestamp = new Date().toISOString();
    const logData = data ? ` ${JSON.stringify(data)}` : '';
    console[level](`[${timestamp}] [${level.toUpperCase()}] ${message}${logData}`);
}

/**
 * Handle API endpoints
 * @param {Object} req - HTTP request object
 * @param {Object} res - HTTP response object
 * @param {string} pathname - URL pathname
 * @returns {boolean} - True if API was handled, false otherwise
 */
function handleApiEndpoints(req, res, pathname) {
    if (pathname === '/api/launch-ecosystem') {
        try {
            logger('info', `Handling API request: ${pathname}`);
            const launchHandler = require('./projects/ecosystem-simulation/launch.js');
            launchHandler(req, res);
            return true;
        } catch (error) {
            logger('error', 'Error launching ecosystem simulation', { error: error.message, stack: error.stack });
            res.writeHead(500, { 'Content-Type': 'application/json' });
            res.end(JSON.stringify({
                error: 'Error launching application',
                message: error.message
            }));
            return true;
        }
    }
    return false;
}

/**
 * Serve a static file
 * @param {Object} res - HTTP response object
 * @param {string} filePath - Path to the file
 * @param {string} contentType - MIME type of the file
 */
function serveStaticFile(res, filePath, contentType) {
    fs.readFile(filePath, (error, content) => {
        if (error) {
            if (error.code === 'ENOENT') {
                logger('warn', `File not found: ${filePath}, serving index.html instead`);
                // Page not found, serve index.html instead
                fs.readFile('./index.html', (err, content) => {
                    if (err) {
                        logger('error', 'Error loading index.html', { error: err.message });
                        res.writeHead(500, { 'Content-Type': 'text/plain' });
                        res.end('Internal Server Error - Could not load index.html');
                    } else {
                        res.writeHead(200, { 'Content-Type': 'text/html' });
                        res.end(content, 'utf-8');
                    }
                });
            } else {
                // Server error
                logger('error', `Server error when reading file: ${filePath}`, { error: error.code });
                res.writeHead(500, { 'Content-Type': 'text/plain' });
                res.end(`Internal Server Error: ${error.code}`);
            }
        } else {
            // Success
            res.writeHead(200, { 'Content-Type': contentType });
            res.end(content, 'utf-8');
        }
    });
}

/**
 * Process an HTTP request and handle errors
 * @param {Object} req - HTTP request object
 * @param {Object} res - HTTP response object
 */
function processRequest(req, res) {
    try {
        logger('info', `Request received: ${req.method} ${req.url}`);
        
        // Parse the URL
        const parsedUrl = url.parse(req.url, true);
        
        // Handle API endpoints first
        if (handleApiEndpoints(req, res, parsedUrl.pathname)) {
            return;
        }
        
        // Normalize the URL for static files
        let filePath = '.' + parsedUrl.pathname;
        if (filePath === './') {
            filePath = './index.html';
        }
        
        // Get the file extension and content type
        const extname = path.extname(filePath);
        const contentType = MIME_TYPES[extname] || 'application/octet-stream';
        
        // Serve the static file
        serveStaticFile(res, filePath, contentType);
    } catch (error) {
        logger('error', 'Unhandled error processing request', { error: error.message, stack: error.stack });
        res.writeHead(500, { 'Content-Type': 'text/plain' });
        res.end('Internal Server Error');
    }
}

// Create HTTP server
const server = http.createServer(processRequest);

// Handle server errors
server.on('error', (error) => {
    logger('error', 'Server error', { error: error.message, code: error.code });
    if (error.code === 'EADDRINUSE') {
        logger('error', `Port ${PORT} is already in use. Please choose a different port.`);
        process.exit(1);
    }
});

// Start the server
server.listen(PORT, () => {
    logger('info', `Server running at http://localhost:${PORT}/`);
    logger('info', 'Press Ctrl+C to stop the server');
});

// Handle process termination
process.on('SIGINT', () => {
    logger('info', 'Server shutting down...');
    server.close(() => {
        logger('info', 'Server has been terminated');
        process.exit(0);
    });
});
>>>>>>> 234ac52 (Implemented Overview buttons for projects and improved error handling)
