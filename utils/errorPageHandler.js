const fs = require('fs').promises;
const path = require('path');

/**
 * Handles serving error pages based on status code
 */
class ErrorPageHandler {
    constructor() {
        this.errorPages = {
            404: '/public/error/404.html',
            500: '/public/error/500.html'
        };
    }

    /**
     * Serves the appropriate error page based on status code
     * @param {http.ServerResponse} res - The response object
     * @param {number} statusCode - HTTP status code
     * @param {string} contentType - Content type of the request (html or json)
     * @param {Error} [error] - Optional error object for logging
     */
    async serveErrorPage(res, statusCode, contentType, error) {
        try {
            // If API request (JSON), send JSON error
            if (contentType === 'json') {
                const jsonError = {
                    error: {
                        status: statusCode,
                        message: this.getErrorMessage(statusCode),
                        timestamp: new Date().toISOString()
                    }
                };
                
                if (error && process.env.NODE_ENV === 'development') {
                    jsonError.error.detail = error.message;
                    jsonError.error.stack = error.stack;
                }

                res.writeHead(statusCode, { 'Content-Type': 'application/json' });
                res.end(JSON.stringify(jsonError));
                return;
            }

            // For HTML requests, serve error page
            const errorPagePath = path.join(
                process.cwd(),
                this.errorPages[statusCode] || this.errorPages[500]
            );

            try {
                const content = await fs.readFile(errorPagePath, 'utf-8');
                res.writeHead(statusCode, { 'Content-Type': 'text/html' });
                res.end(content);
            } catch (readError) {
                // Fallback to basic HTML if error page not found
                const basicError = `
                    <!DOCTYPE html>
                    <html>
                        <head>
                            <title>Error ${statusCode}</title>
                        </head>
                        <body>
                            <h1>Error ${statusCode}</h1>
                            <p>${this.getErrorMessage(statusCode)}</p>
                            <a href="/">Return Home</a>
                        </body>
                    </html>
                `;
                res.writeHead(statusCode, { 'Content-Type': 'text/html' });
                res.end(basicError);
            }
        } catch (err) {
            console.error('Error serving error page:', err);
            res.writeHead(500, { 'Content-Type': 'text/plain' });
            res.end('Internal Server Error');
        }
    }

    /**
     * Gets the error message for a status code
     * @param {number} statusCode - HTTP status code
     * @returns {string} Error message
     */
    getErrorMessage(statusCode) {
        const messages = {
            400: 'Bad Request',
            401: 'Unauthorized',
            403: 'Forbidden',
            404: 'Page Not Found',
            500: 'Internal Server Error',
            502: 'Bad Gateway',
            503: 'Service Unavailable'
        };
        return messages[statusCode] || 'An error occurred';
    }
}

module.exports = new ErrorPageHandler();
