# API Documentation

## Server

The server is built on Node.js without external dependencies, implementing a custom middleware system and error handling.

### Class: PortfolioServer

Main server class that initializes and manages the HTTP server.

```typescript
class PortfolioServer {
    constructor()
    async initialize(): Promise<void>
}
```

## Middleware

### Class: MiddlewareManager

Manages the middleware chain execution.

```typescript
class MiddlewareManager {
    use(middleware: Function): void
    execute(req: IncomingMessage, res: ServerResponse): Promise<void>
}
```

### Common Middleware

```typescript
// Request logging
function logRequest(req: IncomingMessage, res: ServerResponse, next: Function): Promise<void>

// CORS headers
function setCorsHeaders(req: IncomingMessage, res: ServerResponse, next: Function): Promise<void>
```

### API Handler

```typescript
function apiHandler(req: IncomingMessage, res: ServerResponse, next: Function): Promise<void>
```

## Handlers

### Class: StaticHandler

Handles serving static files and implements SPA routing fallback.

```typescript
class StaticHandler {
    constructor()
    getContentType(filePath: string): string
    normalizePath(reqPath: string): string
    handle(req: IncomingMessage, res: ServerResponse): Promise<void>
}
```

## Error Handling

### Class: ErrorPageHandler

Manages serving error pages and error responses.

```typescript
class ErrorPageHandler {
    constructor()
    serveErrorPage(
        res: ServerResponse, 
        statusCode: number, 
        contentType: 'html' | 'json', 
        error?: Error
    ): Promise<void>
    getErrorMessage(statusCode: number): string
}
```

### Error Types

```typescript
enum ErrorTypes {
    FILE_NOT_FOUND = 'FILE_NOT_FOUND',
    SERVER_ERROR = 'SERVER_ERROR',
    INVALID_REQUEST = 'INVALID_REQUEST'
}

class AppError extends Error {
    constructor(
        type: ErrorTypes,
        message: string,
        originalError?: Error
    )
}
```

## Port Management

### Class: PortManager

Manages port allocation and availability checking.

```typescript
class PortManager {
    constructor(minPort: number = 3000, maxPort: number = 3010)
    async getNextAvailablePort(): Promise<number>
    async isPortAvailable(port: number): Promise<boolean>
}
```

## Frontend JavaScript

### Main Module

```typescript
// Event Listeners
function initializeEventListeners(): void
function handleScroll(): void
function toggleMobileMenu(): void

// Project Filtering
function filterProjects(category: string): void
function animateProjectCards(): void

// Form Handling
function handleContactForm(event: Event): Promise<void>
```

## Response Types

### API Error Response

```typescript
interface ApiError {
    error: {
        status: number
        message: string
        path?: string
        timestamp: string
        detail?: string    // Only in development
        stack?: string     // Only in development
    }
}
```

## HTTP Status Codes

- 200: OK
- 404: Not Found
- 500: Internal Server Error

## Content Types

- `text/html`
- `text/css`
- `text/javascript`
- `application/json`
- `image/png`
- `image/jpeg`
- `image/gif`
- `image/svg+xml`
- `image/x-icon`
- `application/octet-stream` (default)
