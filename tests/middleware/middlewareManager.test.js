const MiddlewareManager = require('../../middleware/middlewareManager');

describe('Middleware Manager', () => {
    let middlewareManager;
    let mockReq;
    let mockRes;

    beforeEach(() => {
        middlewareManager = new MiddlewareManager();
        mockReq = {};
        mockRes = {
            setHeader: jest.fn(),
            writeHead: jest.fn(),
            end: jest.fn()
        };
    });

    it('should execute middleware in sequence', async () => {
        const sequence = [];
        
        middlewareManager.use(async (req, res, next) => {
            sequence.push(1);
            await next();
        });

        middlewareManager.use(async (req, res, next) => {
            sequence.push(2);
            await next();
        });

        middlewareManager.use(async (req, res, next) => {
            sequence.push(3);
            await next();
        });

        await middlewareManager.execute(mockReq, mockRes);
        expect(sequence).toEqual([1, 2, 3]);
    });

    it('should handle middleware that modifies request/response', async () => {
        middlewareManager.use(async (req, res, next) => {
            req.modified = true;
            await next();
        });

        middlewareManager.use(async (req, res, next) => {
            res.setHeader('X-Modified', 'true');
            await next();
        });

        await middlewareManager.execute(mockReq, mockRes);
        expect(mockReq.modified).toBe(true);
        expect(mockRes.setHeader).toHaveBeenCalledWith('X-Modified', 'true');
    });

    it('should handle errors in middleware', async () => {
        const error = new Error('Middleware error');
        
        middlewareManager.use(async () => {
            throw error;
        });

        await expect(middlewareManager.execute(mockReq, mockRes))
            .rejects
            .toThrow('Middleware error');
    });

    it('should stop middleware chain on error', async () => {
        const sequence = [];
        
        middlewareManager.use(async (req, res, next) => {
            sequence.push(1);
            await next();
        });

        middlewareManager.use(async () => {
            sequence.push(2);
            throw new Error('Stop here');
        });

        middlewareManager.use(async (req, res, next) => {
            sequence.push(3);
            await next();
        });

        await expect(middlewareManager.execute(mockReq, mockRes))
            .rejects
            .toThrow('Stop here');
        expect(sequence).toEqual([1, 2]);
    });
});
