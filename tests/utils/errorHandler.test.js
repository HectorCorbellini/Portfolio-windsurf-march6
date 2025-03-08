const { ErrorTypes, AppError, createErrorResponse } = require('../../utils/errorHandler');

describe('Error Handler', () => {
    describe('AppError', () => {
        it('should create an error with type and message', () => {
            const error = new AppError(ErrorTypes.FILE_NOT_FOUND, 'File not found');
            expect(error.type).toBe(ErrorTypes.FILE_NOT_FOUND);
            expect(error.message).toBe('File not found');
            expect(error.timestamp).toBeDefined();
        });

        it('should store original error if provided', () => {
            const originalError = new Error('Original error');
            const error = new AppError(ErrorTypes.SERVER_ERROR, 'Server error', originalError);
            expect(error.originalError).toBe(originalError);
        });
    });

    describe('createErrorResponse', () => {
        it('should create proper error response for FILE_NOT_FOUND', () => {
            const error = new AppError(ErrorTypes.FILE_NOT_FOUND, 'File not found');
            const response = createErrorResponse(error);
            
            expect(response.statusCode).toBe(404);
            expect(response.headers['Content-Type']).toBe('application/json');
            expect(response.body.error.type).toBe(ErrorTypes.FILE_NOT_FOUND);
            expect(response.body.error.message).toBe('File not found');
            expect(response.body.error.timestamp).toBeDefined();
        });

        it('should create proper error response for SERVER_ERROR', () => {
            const error = new AppError(ErrorTypes.SERVER_ERROR, 'Server error');
            const response = createErrorResponse(error);
            
            expect(response.statusCode).toBe(500);
            expect(response.body.error.type).toBe(ErrorTypes.SERVER_ERROR);
        });

        it('should handle unknown error types with 500 status', () => {
            const error = new Error('Unknown error');
            const response = createErrorResponse(error);
            
            expect(response.statusCode).toBe(500);
            expect(response.body.error.type).toBe('UNKNOWN_ERROR');
        });
    });
});
