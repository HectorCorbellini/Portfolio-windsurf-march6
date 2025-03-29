/**
 * API Service
 * Centralizes all API calls in one place for better maintainability
 */
import { handleApiError, ErrorTypes } from '../utils/errorHandler.js';

/**
 * Handles API responses consistently
 * @param {Response} response - Fetch API response
 * @returns {Promise} - Resolved with response JSON or rejected with error
 */
const handleResponse = async (response) => {
    if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        const error = new Error(errorData.message || `HTTP error! Status: ${response.status}`);
        error.status = response.status;
        error.data = errorData;
        throw error;
    }
    
    return response.json();
};

/**
 * Makes an API request with consistent error handling
 * @param {string} url - The API endpoint URL
 * @param {Object} options - Request options (method, headers, body, etc.)
 * @param {Object} errorOptions - Error handling options
 * @returns {Promise} - Promise resolving to API response
 */
const makeRequest = async (url, options = {}, errorOptions = {}) => {
    try {
        const response = await fetch(url, {
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                ...options.headers
            },
            ...options
        });
        
        return await handleResponse(response);
    } catch (error) {
        // Network errors won't have a status
        if (!error.status) {
            error.type = ErrorTypes.NETWORK_ERROR;
        }
        
        throw error;
    }
};

/**
 * API service object with methods for different API endpoints
 */
const apiService = {
    /**
     * Launches the ecosystem simulation
     * @param {Object} notification - Optional notification to update
     * @returns {Promise} - Promise resolving to API response
     */
    launchEcosystemSimulation(notification = null) {
        return makeRequest('/api/launch-ecosystem', {
            method: 'POST'
        }).catch(error => {
            throw handleApiError(error, {
                notification,
                context: 'Ecosystem Simulation'
            });
        });
    },
    
    /**
     * Launches the Code Transformer application
     * @param {Object} notification - Optional notification to update
     * @returns {Promise} - Promise resolving to API response
     */
    launchCodeTransformer(notification = null) {
        return makeRequest('/api/launch-code-transformer', {
            method: 'POST'
        }).catch(error => {
            throw handleApiError(error, {
                notification,
                context: 'Code Transformer'
            });
        });
    },
    
    /**
     * Submits contact form data
     * @param {Object} formData - Form data object
     * @param {Object} notification - Optional notification to update
     * @returns {Promise} - Promise resolving to API response
     */
    submitContactForm(formData, notification = null) {
        // For now, this is a mock implementation
        // In a real application, this would be a real API call
        return new Promise((resolve, reject) => {
            // Validate form data
            if (!formData.name || !formData.email || !formData.message) {
                const error = new Error('Please fill in all required fields');
                error.type = ErrorTypes.VALIDATION_ERROR;
                
                handleApiError(error, {
                    notification,
                    context: 'Contact Form'
                });
                
                reject(error);
                return;
            }
            
            // Simulate API call with setTimeout
            setTimeout(() => {
                resolve({ message: 'Your message has been sent successfully!' });
            }, 1000);
        });
    }
};

export default apiService;
