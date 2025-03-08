# Java Developer Portfolio

A modern, responsive portfolio website built with Node.js and vanilla JavaScript, showcasing Java development projects and professional experience.

## 🚀 Features

- **Modern Design**: Clean, responsive layout with smooth animations
- **Project Showcase**: Dynamic project filtering and categorization
- **Error Handling**: Custom error pages and API error responses
- **Modular Architecture**: Well-organized codebase with separation of concerns
- **Performance Optimized**: Fast loading times and efficient resource management
- **Cross-Browser Compatible**: Works seamlessly across different browsers
- **Mobile-First**: Fully responsive design for all device sizes

## 🛠️ Technical Stack

- **Backend**: Node.js with custom server implementation
- **Frontend**: Vanilla JavaScript with modern ES6+ features
- **Styling**: CSS3 with custom properties and BEM methodology
- **Testing**: Jest for unit testing
- **Error Handling**: Custom error handling system with fallbacks
- **Development**: Nodemon for development workflow

## 📁 Project Structure

```
portfolio/
├── public/               # Static assets
│   └── error/           # Error page templates
│       ├── 404.html
│       └── 500.html
├── css/                 # Stylesheets
│   ├── base/           # Base styles
│   │   ├── _reset.css
│   │   └── _variables.css
│   ├── components/     # Component styles
│   │   ├── _header.css
│   │   └── _projects.css
│   └── main.css        # Main stylesheet
├── js/                  # JavaScript files
│   └── main.js         # Main JavaScript file
├── handlers/            # Request handlers
│   └── staticHandler.js # Static file handler
├── middleware/          # Middleware components
│   ├── apiHandler.js    # API request handler
│   ├── common.js        # Common middleware
│   └── middlewareManager.js # Middleware system
├── utils/              # Utility functions
│   ├── errorHandler.js  # Error handling
│   ├── errorPageHandler.js # Error page serving
│   └── portManager.js   # Port management
├── tests/              # Test files
│   ├── handlers/       # Handler tests
│   ├── middleware/     # Middleware tests
│   └── utils/         # Utility tests
├── server.js           # Server entry point
└── package.json        # Project configuration
```

## 🚦 Getting Started

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd portfolio
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start the server**
   ```bash
   # Development mode
   npm run dev

   # Production mode
   npm start
   ```

The server will automatically find an available port between 3000 and 3010.

## 🔧 Configuration

The application can be configured through environment variables:

- `NODE_ENV`: Set to 'development' or 'production' (default: 'production')
- `PORT_MIN`: Minimum port number to try (default: 3000)
- `PORT_MAX`: Maximum port number to try (default: 3010)

## 🧪 Testing

Run the test suite:
```bash
# Run all tests
npm test

# Run tests with coverage
npm run test:coverage

# Watch mode for development
npm run test:watch
```

## 🛡️ Error Handling

The application implements a comprehensive error handling system:

- **API Errors**: JSON responses with detailed error information
- **HTML Errors**: Custom error pages with user-friendly messages
- **Fallback System**: Graceful degradation if error pages fail to load
- **Development Mode**: Detailed error information including stack traces

## 🔒 Security Features

- Path traversal protection
- CORS headers configuration
- Content-Type validation
- Error message sanitization
- Development/Production environment separation

## 📱 Responsive Design

The portfolio is fully responsive with breakpoints at:
- Mobile: < 480px
- Tablet: 480px - 768px
- Desktop: > 768px

## 🎨 Styling Architecture

Uses BEM (Block Element Modifier) methodology with a modular CSS structure:
- Base styles and variables
- Component-specific styles
- Utility classes
- Responsive design rules

## 🔄 Middleware System

Custom middleware implementation supporting:
- Async/await
- Error handling
- Request logging
- CORS
- Static file serving
- API routing

## 🐛 Debugging

Debug logs can be enabled by setting `DEBUG=true`:
```bash
DEBUG=true npm run dev
```

## 📈 Performance Optimization

- CSS and JavaScript minification
- Image optimization
- Efficient error handling
- Smart port management
- Caching headers

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👤 Author

Your Name
- GitHub: [@yourusername](https://github.com/yourusername)
- LinkedIn: [Your Name](https://linkedin.com/in/yourprofile)

## 🙏 Acknowledgments

- Font Awesome for icons
- Google Fonts for typography
- Jest for testing framework
- Node.js community
