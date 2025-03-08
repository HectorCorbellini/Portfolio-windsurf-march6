/**
 * Middleware Manager
 * Handles the registration and execution of middleware functions
 */

class MiddlewareManager {
    constructor() {
        this.middlewares = [];
    }

    /**
     * Add a middleware function to the chain
     * @param {Function} middleware - Middleware function to add
     */
    use(middleware) {
        this.middlewares.push(middleware);
    }

    /**
     * Execute all middleware functions in sequence
     * @param {http.IncomingMessage} req - The request object
     * @param {http.ServerResponse} res - The response object
     * @returns {Promise<void>}
     */
    async execute(req, res) {
        let index = 0;

        const next = async () => {
            if (index < this.middlewares.length) {
                const middleware = this.middlewares[index];
                index++;
                await middleware(req, res, next);
            }
        };

        await next();
    }
}

module.exports = MiddlewareManager;
