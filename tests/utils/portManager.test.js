const PortManager = require('../../utils/portManager');
const net = require('net');

describe('Port Manager', () => {
    let portManager;
    let testServer;

    beforeEach(() => {
        portManager = new PortManager(3000, 3010);
    });

    afterEach((done) => {
        if (testServer) {
            testServer.close(done);
        } else {
            done();
        }
    });

    describe('isPortInUse', () => {
        it('should return false for unused port', async () => {
            const inUse = await portManager.isPortInUse(3000);
            expect(inUse).toBe(false);
        });

        it('should return true for port in use', (done) => {
            testServer = net.createServer().listen(3000, async () => {
                const inUse = await portManager.isPortInUse(3000);
                expect(inUse).toBe(true);
                done();
            });
        });
    });

    describe('getNextAvailablePort', () => {
        it('should return first available port', async () => {
            const port = await portManager.getNextAvailablePort();
            expect(port).toBe(3000);
        });

        it('should skip port in use and return next available', (done) => {
            testServer = net.createServer().listen(3000, async () => {
                const port = await portManager.getNextAvailablePort();
                expect(port).toBe(3001);
                done();
            });
        });

        it('should throw error if no ports available', (done) => {
            const servers = [];
            let portsOpened = 0;

            // Open servers on all ports in range
            for (let port = 3000; port <= 3010; port++) {
                const server = net.createServer().listen(port, () => {
                    portsOpened++;
                    if (portsOpened === 11) { // 11 ports (3000-3010 inclusive)
                        // All ports are now in use, test can proceed
                        portManager.getNextAvailablePort()
                            .catch(error => {
                                expect(error.message).toBe('No available ports in the specified range');
                                // Clean up servers
                                Promise.all(servers.map(s => new Promise(r => s.close(r))))
                                    .then(() => done());
                            });
                    }
                });
                servers.push(server);
            }
        });
    });
});
