/**
 * Middleware to handle API routes and return proper status codes
 * @param {http.IncomingMessage} req - The request object
 * @param {http.ServerResponse} res - The response object
 * @param {Function} next - The next middleware function
 */
async function apiHandler(req, res, next) {
    // Check if this is an API route
    if (req.url.startsWith('/api/')) {
        const error = {
            status: 404,
            message: 'API endpoint not found',
            path: req.url,
            timestamp: new Date().toISOString()
        };

        res.writeHead(404, { 
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'GET, POST, OPTIONS',
            'Access-Control-Allow-Headers': 'Content-Type'
        });
        res.end(JSON.stringify({ error }));
        return;
    }
    
    // Not an API route, continue to next middleware
    await next();
}

module.exports = apiHandler;
