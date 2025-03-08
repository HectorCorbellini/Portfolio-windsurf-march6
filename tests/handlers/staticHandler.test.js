const StaticHandler = require('../../handlers/staticHandler');
const fs = require('fs').promises;
const path = require('path');

jest.mock('fs', () => ({
    promises: {
        readFile: jest.fn()
    }
}));

describe('Static Handler', () => {
    let staticHandler;
    let mockReq;
    let mockRes;

    beforeEach(() => {
        staticHandler = new StaticHandler();
        mockReq = {
            url: '/test.html'
        };
        mockRes = {
            writeHead: jest.fn(),
            end: jest.fn()
        };
        jest.clearAllMocks();
    });

    describe('getContentType', () => {
        it('should return correct content type for known extensions', () => {
            expect(staticHandler.getContentType('test.html')).toBe('text/html');
            expect(staticHandler.getContentType('style.css')).toBe('text/css');
            expect(staticHandler.getContentType('script.js')).toBe('text/javascript');
        });

        it('should return octet-stream for unknown extensions', () => {
            expect(staticHandler.getContentType('unknown.xyz')).toBe('application/octet-stream');
        });
    });

    describe('normalizePath', () => {
        it('should return index.html for root path', () => {
            const result = staticHandler.normalizePath('/');
            expect(path.basename(result)).toBe('index.html');
        });

        it('should normalize file paths', () => {
            const result = staticHandler.normalizePath('/css/style.css');
            expect(path.basename(result)).toBe('style.css');
        });
    });

    describe('handle', () => {
        it('should serve existing files with correct content type', async () => {
            const fileContent = 'test content';
            fs.readFile.mockResolvedValue(fileContent);

            await staticHandler.handle({ url: '/test.html' }, mockRes);

            expect(mockRes.writeHead).toHaveBeenCalledWith(200, {
                'Content-Type': 'text/html'
            });
            expect(mockRes.end).toHaveBeenCalledWith(fileContent);
        });

        it('should serve index.html as fallback for missing files', async () => {
            const indexContent = 'index content';
            fs.readFile
                .mockRejectedValueOnce({ code: 'ENOENT' })
                .mockResolvedValueOnce(indexContent);

            await staticHandler.handle({ url: '/missing.html' }, mockRes);

            expect(mockRes.writeHead).toHaveBeenCalledWith(200, {
                'Content-Type': 'text/html'
            });
            expect(mockRes.end).toHaveBeenCalledWith(indexContent);
        });

        it('should throw error when both requested file and index.html are missing', async () => {
            fs.readFile
                .mockRejectedValueOnce({ code: 'ENOENT' })
                .mockRejectedValueOnce({ code: 'ENOENT' });

            await expect(staticHandler.handle({ url: '/missing.html' }, mockRes))
                .rejects
                .toThrow('File not found and fallback index.html is missing');
        });

        it('should handle server errors properly', async () => {
            fs.readFile.mockRejectedValue(new Error('Read error'));

            await expect(staticHandler.handle({ url: '/test.html' }, mockRes))
                .rejects
                .toThrow('Error reading file');
        });
    });
});
