/**
 * Client-side error handling utilities
 * Provides consistent error handling across the application
 */
import notificationSystem from '../notifications.js';

/**
 * Custom application error class
 */
export class AppError extends Error {
    constructor(message, code = 'UNKNOWN_ERROR', data = null) {
        super(message);
        this.name = 'AppError';
        this.code = code;
        this.data = data;
        
        // Capture stack trace
        if (Error.captureStackTrace) {
            Error.captureStackTrace(this, AppError);
        }
    }
}

/**
 * Error types for consistent error classification
 */
export const ErrorTypes = {
    API_ERROR: 'api_error',
    VALIDATION_ERROR: 'validation_error',
    AUTH_ERROR: 'auth_error',
    NETWORK_ERROR: 'network_error',
    UNKNOWN_ERROR: 'unknown_error'
};

/**
 * Maps HTTP status codes to error types
 * @param {number} statusCode - HTTP status code
 * @returns {string} Error type
 */
export function mapStatusToErrorType(statusCode) {
    if (!statusCode) return ErrorTypes.UNKNOWN_ERROR;
    
    switch (statusCode) {
        case 400:
            return ErrorTypes.VALIDATION_ERROR;
        case 401:
        case 403:
            return ErrorTypes.AUTH_ERROR;
        case 404:
            return ErrorTypes.API_ERROR;
        case 500:
        case 502:
        case 503:
        case 504:
            return ErrorTypes.API_ERROR;
        default:
            return ErrorTypes.UNKNOWN_ERROR;
    }
}

/**
 * Gets a user-friendly error message based on error type and specific error
 * @param {string} errorType - Type of error from ErrorTypes
 * @param {Error|string} error - The error object or message
 * @returns {string} User-friendly error message
 */
export function getUserFriendlyErrorMessage(errorType, error) {
    const defaultMessages = {
        [ErrorTypes.API_ERROR]: 'The server encountered an error. Please try again later.',
        [ErrorTypes.VALIDATION_ERROR]: 'Please check your input and try again.',
        [ErrorTypes.AUTH_ERROR]: 'You do not have permission to perform this action.',
        [ErrorTypes.NETWORK_ERROR]: 'Network error. Please check your connection and try again.',
        [ErrorTypes.UNKNOWN_ERROR]: 'An unexpected error occurred. Please try again.'
    };
    
    // Use error message if available, otherwise use default message
    return (error && error.message) ? error.message : defaultMessages[errorType] || defaultMessages[ErrorTypes.UNKNOWN_ERROR];
}

/**
 * Unified method to handle API errors
 * @param {Error} error - The error that occurred
 * @param {Object} options - Error handling options
 * @returns {Object} - Structured error object
 */
export function handleApiError(error, options = {}) {
    const {
        notification = null,
        context = 'API Request',
        silent = false
    } = options;
    
    // Determine error type
    const statusCode = error.status || (error.response ? error.response.status : null);
    const errorType = mapStatusToErrorType(statusCode);
    
    // Get user-friendly message
    const errorMessage = getUserFriendlyErrorMessage(errorType, error);
    
    // Log error to console
    console.error(`${context} Error:`, error);
    
    // Show notification if not silent
    if (!silent) {
        if (notification) {
            notificationSystem.update(
                notification,
                errorMessage,
                'error'
            );
        } else {
            notificationSystem.show(
                errorMessage,
                'error'
            );
        }
    }
    
    // Return structured error object for further handling if needed
    return {
        type: errorType,
        message: errorMessage,
        originalError: error,
        statusCode
    };
}

/**
 * Handles errors consistently across the application
 * @param {Error} error - The error to handle
 * @param {Object} options - Error handling options
 * @returns {void}
 */
export function handleError(error, options = {}) {
    const {
        silent = false,
        notificationId = null,
        logToConsole = true,
        defaultMessage = 'An unexpected error occurred'
    } = options;
    
    // Log to console if enabled
    if (logToConsole) {
        console.error('Error:', error);
    }
    
    // Get appropriate error message
    const errorMessage = error.message || defaultMessage;
    
    // Show notification if not silent
    if (!silent) {
        if (notificationId) {
            notificationSystem.update(
                notificationId,
                `Error: ${errorMessage}`,
                'error'
            );
        } else {
            notificationSystem.show(
                `Error: ${errorMessage}`,
                'error'
            );
        }
    }
    
    // Return the error for further handling if needed
    return error;
}

/**
 * Creates a safe wrapper around async functions to handle errors consistently
 * @param {Function} fn - The async function to wrap
 * @param {Object} options - Error handling options
 * @returns {Function} - Wrapped function that handles errors
 */
export function createSafeAsyncHandler(fn, options = {}) {
    return async function safeAsyncHandler(...args) {
        try {
            return await fn(...args);
        } catch (error) {
            return handleError(error, options);
        }
    };
}

export default {
    AppError,
    handleError,
    createSafeAsyncHandler
};
