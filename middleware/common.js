/**
 * Common middleware functions for the portfolio server
 */

const { AppError, ErrorTypes } = require('../utils/errorHandler');

/**
 * Logs incoming requests
 */
const requestLogger = async (req, res, next) => {
    const timestamp = new Date().toISOString();
    console.log(`[${timestamp}] ${req.method} ${req.url}`);
    await next();
};

/**
 * Adds CORS headers to responses
 */
const corsMiddleware = async (req, res, next) => {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type');
    
    if (req.method === 'OPTIONS') {
        res.writeHead(204);
        res.end();
        return;
    }
    
    await next();
};

/**
 * Validates the request path for security
 */
const securityMiddleware = async (req, res, next) => {
    if (req.url.includes('..') || req.url.includes('//')) {
        throw new AppError(
            ErrorTypes.INVALID_REQUEST,
            'Invalid path: Potential directory traversal attempt'
        );
    }
    await next();
};

module.exports = {
    requestLogger,
    corsMiddleware,
    securityMiddleware
};
