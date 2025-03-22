# Java Developer Portfolio

A modern, responsive portfolio website built with Node.js and vanilla JavaScript, showcasing Java development projects and professional experience.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Server Configuration](#server-configuration)
- [Customization](#customization)
- [Troubleshooting](#troubleshooting)
- [Browser Compatibility](#browser-compatibility)
- [License](#license)
- [Recent Improvements](#recent-improvements)

HectorCorbellini
- Email: hector.corbellini@gmail.com
- GitHub: [@HectorCorbellini](https://github.com/HectorCorbellini)
- LinkedIn: [Hector Corbellini](https://linkedin.com/in/hectorcorbellini)

- **Minimalistic Design**: Clean, professional dark theme with strong visual appeal
- **Fully Responsive**: Optimized for desktop, tablet, and mobile devices
- **Project Showcase**: Categorized display of projects (CodeGym Academy, Commercial, Team Projects)
- **Interactive Demos**: Direct launching of applications like the Ecosystem Simulation and Caesar Cipher
- **Project Overviews**: Modal-based detailed information about projects
- **Skills Section**: Visual representation of technical skills and expertise
- **About Me**: Professional introduction with photo
- **Contact Form**: Direct communication channel for potential clients
- **Social Media Integration**: Links to professional profiles
- **Smooth Animations**: Enhanced user experience with subtle animations

This project uses GitHub Personal Access Token for authentication. The token is configured with full repository access and GitHub Copilot permissions. To set up:

1. The token is stored securely in `docs/CREDENTIALS.md` (not in version control)
2. Run `./scripts/setup_git_credentials.sh` to configure Git with the token
3. Git operations will then work automatically without password prompts

**Important**: Never commit the token to version control. The script handles token storage securely.

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
├── index.html          # Main HTML file with portfolio content
├── server.js           # Node.js server for local development and API endpoints
├── package.json        # Project dependencies and scripts
├── start_portfolio.sh  # Quick start script for running the portfolio
├── README.md           # Project documentation and setup guide
├── css/
│   └── styles.css      # Main stylesheet with all styling
├── js/
│   └── main.js         # JavaScript for interactive functionality
├── images/             # Project images and profile photo
└── projects/           # Project-specific files and launch scripts
    └── ecosystem-simulation/  # Ecosystem Simulation project files
        └── launch.js   # Server-side script to launch the Java application
```

## 🚦 Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/HectorCorbellini/Portfolio-windsurf-march6-new.git
   cd Portfolio-windsurf-march6-new
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

6. **Quick Start Script**
   A convenient startup script is provided to quickly run the portfolio:
   ```bash
   ./start_portfolio.sh
   ```
   This script will:
   - Automatically navigate to the portfolio directory
   - Check for npm installation
   - Install missing dependencies if needed
   - Start the server at http://localhost:3001
   
   You can copy this script to your desktop for easy access.

## Server Configuration

The project uses a Node.js server (server.js) with these key features:

- **Port Configuration**: Default port is 3001 (configurable via PORT environment variable)
- **Static File Serving**: Automatically serves HTML, CSS, JS, and image files
- **MIME Type Support**: Properly sets content types for various file extensions
- **Fallback to Index**: Routes unknown paths back to index.html
- **API Endpoints**: Handles requests to launch applications like the Ecosystem Simulation
- **Process Management**: Executes and manages external applications

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

Each project object should include:
```javascript
{
  title: "Project Name",
  description: "Brief project description",
  image: "/images/project-image.jpg",
  tags: ["Java", "Spring", "MySQL"],
  demoLink: "https://demo-link.com", // Use '#' for projects with custom launch functionality
  codeLink: "https://github.com/username/repo",
  // Optional: For projects with detailed overviews
  overview: {
    features: ["Feature 1", "Feature 2"],
    technologies: ["Technology 1", "Technology 2"],
    instructions: ["Instruction 1", "Instruction 2"]
  }
}
```

### Interactive Applications

The portfolio supports launching Java applications directly from the browser:

1. **Create a launch script**: Add a server-side script in the project directory
2. **Update server.js**: Add an API endpoint to handle the launch request
3. **Configure the project**: Set `demoLink` to `#` and add a click handler for the Demo button

### Styling

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

This project is available under the MIT License. See the LICENSE file for details.

## Repository

This project is hosted on GitHub: [Portfolio-windsurf-march6-new](https://github.com/HectorCorbellini/Portfolio-windsurf-march6-new)

2. **Images Not Displaying**
   - Check file paths and extensions (use browser dev tools to see errors)
   - Ensure image files exist in the correct location
   - Verify file format matches extension (PNG files should have .png extension)
   - Use absolute paths starting with `/images/...`

3. **CSS Not Applying**
   - Clear browser cache with hard refresh (Ctrl+F5)
   - Check for CSS syntax errors in browser dev tools
   - Verify CSS file is being loaded (check network tab in dev tools)

4. **JavaScript Not Working**
   - Check browser console for errors (F12 > Console tab)
   - Ensure script tags are properly placed in HTML
   - Verify event listeners are attached to existing elements

## Browser Compatibility

This portfolio is designed to work on all modern browsers including:
- Chrome (v80+)
- Firefox (v75+)
- Safari (v13+)
- Edge (v80+)

## Recent Improvements

### Code Refactoring
- The codebase has been refactored to improve organization and reduce redundancy. Utility functions have been created for modal and notification handling.

### Standardized Image Paths
- All project images now use standardized relative paths from the `/images` directory instead of placeholder URLs.

### Enhanced Error Handling
- Improved error handling in the server to provide better logging and structured error responses for API endpoints.

### Updated Project Structure
- The project structure has been updated to reflect the new organization, including the addition of images in the `/images` directory.

## License

This project is available under the MIT License. See the LICENSE file for details.

---

Created by Héctor Corbellini for showcasing Java development skills and services.

Last updated: March 2026
