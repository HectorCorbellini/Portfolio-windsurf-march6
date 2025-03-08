/**
 * Static file handler for the portfolio server
 */

const fs = require('fs').promises;
const path = require('path');
const errorPageHandler = require('../utils/errorPageHandler');

class StaticHandler {
    constructor() {
        this.mimeTypes = {
            '.html': 'text/html',
            '.css': 'text/css',
            '.js': 'text/javascript',
            '.json': 'application/json',
            '.png': 'image/png',
            '.jpg': 'image/jpeg',
            '.jpeg': 'image/jpeg',
            '.gif': 'image/gif',
            '.svg': 'image/svg+xml',
            '.ico': 'image/x-icon'
        };
    }

    /**
     * Get content type based on file extension
     * @param {string} filePath - Path to the file
     * @returns {string} Content type
     */
    getContentType(filePath) {
        const ext = path.extname(filePath).toLowerCase();
        return this.mimeTypes[ext] || 'application/octet-stream';
    }

    /**
     * Normalize the request path
     * @param {string} reqPath - Request path
     * @returns {string} Normalized path
     */
    normalizePath(reqPath) {
        // Remove query strings and hash
        const cleanPath = reqPath.split('?')[0].split('#')[0];
        
        // Serve index.html for root path
        if (cleanPath === '/') {
            return path.join(process.cwd(), 'index.html');
        }

        // Remove leading slash and join with current directory
        return path.join(process.cwd(), cleanPath.replace(/^\//, ''));
    }

    /**
     * Handle static file requests
     * @param {http.IncomingMessage} req - Request object
     * @param {http.ServerResponse} res - Response object
     */
    async handle(req, res) {
        try {
            const filePath = this.normalizePath(req.url);
            const contentType = this.getContentType(filePath);
            
            try {
                const content = await fs.readFile(filePath);
                res.writeHead(200, { 'Content-Type': contentType });
                res.end(content);
            } catch (error) {
                if (error.code === 'ENOENT') {
                    // Try serving index.html as fallback for SPA routing
                    if (!req.url.startsWith('/api/')) {
                        try {
                            const indexContent = await fs.readFile(
                                path.join(process.cwd(), 'index.html')
                            );
                            res.writeHead(200, { 'Content-Type': 'text/html' });
                            res.end(indexContent);
                            return;
                        } catch (indexError) {
                            // If index.html is also missing, show 404
                            await errorPageHandler.serveErrorPage(res, 404, 'html');
                            return;
                        }
                    }
                    await errorPageHandler.serveErrorPage(res, 404, 'html');
                } else {
                    console.error('Error reading file:', error);
                    await errorPageHandler.serveErrorPage(res, 500, 'html', error);
                }
            }
        } catch (error) {
            console.error('Static handler error:', error);
            await errorPageHandler.serveErrorPage(res, 500, 'html', error);
        }
    }
}

module.exports = StaticHandler;
