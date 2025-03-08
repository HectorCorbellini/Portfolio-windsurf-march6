/**
 * Error handling utilities for the portfolio application
 * Provides consistent error handling and logging across the application
 */

/**
 * Standard error types for the application
 */
const ErrorTypes = {
    FILE_NOT_FOUND: 'FILE_NOT_FOUND',
    SERVER_ERROR: 'SERVER_ERROR',
    INVALID_REQUEST: 'INVALID_REQUEST'
};

/**
 * Maps error types to HTTP status codes
 */
const StatusCodes = {
    [ErrorTypes.FILE_NOT_FOUND]: 404,
    [ErrorTypes.SERVER_ERROR]: 500,
    [ErrorTypes.INVALID_REQUEST]: 400
};

/**
 * Custom error class for application-specific errors
 */
class AppError extends Error {
    constructor(type, message, originalError = null) {
        super(message);
        this.type = type;
        this.originalError = originalError;
        this.timestamp = new Date().toISOString();
    }
}

/**
 * Logs error details to console with consistent formatting
 * @param {Error} error - The error to log
 */
function logError(error) {
    console.error('=== Error Details ===');
    console.error(`Timestamp: ${new Date().toISOString()}`);
    console.error(`Type: ${error.type || 'UNKNOWN'}`);
    console.error(`Message: ${error.message}`);
    if (error.originalError) {
        console.error('Original Error:', error.originalError);
    }
    console.error('==================\n');
}

/**
 * Creates a standardized error response
 * @param {Error} error - The error object
 * @returns {Object} Formatted error response
 */
function createErrorResponse(error) {
    const statusCode = error.type ? StatusCodes[error.type] : 500;
    return {
        statusCode,
        headers: { 'Content-Type': 'application/json' },
        body: {
            error: {
                type: error.type || 'UNKNOWN_ERROR',
                message: error.message,
                timestamp: error.timestamp || new Date().toISOString()
            }
        }
    };
}

module.exports = {
    ErrorTypes,
    AppError,
    logError,
    createErrorResponse
};
