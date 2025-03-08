/**
 * Portfolio Website Server
 * This is a simple HTTP server that serves static files for the portfolio website.
 * It handles basic routing and mime-type detection for various file types.
 */

const http = require('http');
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
