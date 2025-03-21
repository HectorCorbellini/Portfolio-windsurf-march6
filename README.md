# Professional Java Developer Portfolio

A clean, modern, and responsive portfolio website designed to showcase Java development skills, projects, and services. This portfolio features a minimalistic dark theme design optimized for both desktop and mobile devices.

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
- [Development Workflow](#development-workflow)

## Features

- **Minimalistic Design**: Clean, professional dark theme with strong visual appeal
- **Fully Responsive**: Optimized for desktop, tablet, and mobile devices
- **Project Showcase**: Categorized display of projects (CodeGym Academy, Commercial, Team Projects)
- **Skills Section**: Visual representation of technical skills and expertise
- **About Me**: Professional introduction with photo
- **Contact Form**: Direct communication channel for potential clients
- **Social Media Integration**: Links to professional profiles
- **Smooth Animations**: Enhanced user experience with subtle animations

## Technologies Used

- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **Server**: Node.js with native HTTP module
- **Styling**: Custom CSS with CSS Variables for theming
- **Icons**: Font Awesome
- **Fonts**: Google Fonts (Roboto, Montserrat)
- **Development**: Nodemon for auto-reloading during development

## Project Structure

```
portfolio/
├── index.html          # Main HTML file with portfolio content
├── server.js           # Node.js server for local development
├── package.json        # Project dependencies and scripts
├── start_portfolio.sh  # Quick start script for running the portfolio
├── README.md          # Project documentation and setup guide
├── css/
│   └── styles.css      # Main stylesheet with all styling
├── js/
│   └── main.js         # JavaScript for interactive functionality
└── images/             # Project images and profile photo
```

## Setup and Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/HectorCorbellini/Portfolio-windsurf-march6.git
   cd Portfolio-windsurf-march6
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start the development server**
   ```bash
   npm run start
   ```

4. **Access the portfolio**
   Open your browser and navigate to:
   ```
   http://localhost:3001/
   ```

5. **Development mode with auto-reload**
   ```bash
   npm run dev
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

The project uses a simple Node.js server (server.js) with these key features:

- **Port Configuration**: Default port is 3001 (configurable via PORT environment variable)
- **Static File Serving**: Automatically serves HTML, CSS, JS, and image files
- **MIME Type Support**: Properly sets content types for various file extensions
- **Fallback to Index**: Routes unknown paths back to index.html

To change the default port:
1. Edit the PORT variable in server.js
2. Or set the PORT environment variable when running the server

```bash
PORT=8080 npm run start
```

## Customization

### Personal Information

Edit the following sections in `index.html`:

1. Update the `<title>` tag with your name
2. Modify the hero section content with your profession and location
3. Edit the About Me section with your personal information
4. Update the contact information and social media links
5. Replace the profile image at `/images/profile.png`

### Projects

Project data is stored in the `main.js` file. Modify the following arrays to add your own projects:

- `codegymProjects`: CodeGym Academy projects
- `commercialProjects`: Commercial applications
- `teamProjects`: Team and open-source projects

Each project object should include:
```javascript
{
  title: "Project Name",
  description: "Brief project description",
  image: "/images/project-image.jpg",
  tags: ["Java", "Spring", "MySQL"],
  demoLink: "https://demo-link.com",
  codeLink: "https://github.com/username/repo"
}
```

### Project Images

1. Place all project images in the `/images` directory
2. Reference images in `main.js` using the relative path `/images/filename.jpg`
3. Recommended image dimensions: 960x480px (2:1 aspect ratio)
4. Supported formats: JPG, PNG, WebP

Example:
```javascript
{
  title: "Caesar Cipher Encryption Tool",
  image: "/images/crypto.jpg",
  // ... other project details
}
```

### Styling

The color scheme can be modified in the `:root` section of `styles.css`. The current theme uses:

```css
:root {
  --primary-color: #6200ea;
  --secondary-color: #03dac6;
  --bg-dark: #121212;
  --bg-darker: #0a0a0a;
  --text-primary: #ffffff;
  --text-secondary: #b0b0b0;
  --card-bg: #1e1e1e;
}
```

## Troubleshooting

### Common Issues

1. **Server Port Conflict**
   - Error: `EADDRINUSE: address already in use :::3001`
   - Solution: Change the port in server.js or kill the process using the port
   ```bash
   # Find process using port 3001
   lsof -i :3001
   # Kill the process
   kill -9 [PID]
   ```

2. **Images Not Displaying**
   - Ensure images are placed in the `/images` directory
   - Use the correct relative path starting with `/images/`
   - Check file permissions (should be readable by the web server)
   - Verify file format matches extension
   - Check browser dev tools Network tab for 404 errors

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

## License

This project is available under the MIT License. See the LICENSE file for details.

## Development Workflow

### Branching Strategy

This project follows a multi-branch development workflow to ensure code quality and maintain a stable production environment:

1. **Main Branch (`master`)**
   - Contains production-ready code
   - Always stable and deployable
   - Protected from direct pushes
   - Changes only merged through pull requests

2. **Feature Branches (`feature/*`)**
   - Created for new features or enhancements
   - Example: `feature/image-management` for implementing new image handling
   - Isolated environment for development
   - Allows parallel work on different features

3. **Bug Fix Branches (`fix/*`)**
   - Created for bug fixes
   - Example: `fix/broken-links` for fixing navigation issues
   - Enables quick patches without disrupting feature development

### Why Multiple Branches?

1. **Risk Management**
   - Isolates experimental changes from stable code
   - Prevents bugs from affecting production
   - Enables easy rollback if issues arise

2. **Parallel Development**
   - Multiple developers can work simultaneously
   - Features can be developed independently
   - No blocking of work due to dependencies

3. **Code Review**
   - Changes are reviewed through pull requests
   - Team can discuss and improve code
   - Maintains code quality standards

4. **Testing**
   - Features can be tested in isolation
   - Integration testing before merging
   - Reduces conflicts and deployment issues

### Branch Naming Convention

- Feature branches: `feature/descriptive-name`
- Bug fixes: `fix/issue-description`
- Documentation: `docs/update-description`
- Example: `feature/image-management` for image handling improvements

### Workflow Example

1. Create a new feature branch:
   ```bash
   git checkout -b feature/new-feature
   ```

2. Make changes and commit:
   ```bash
   git add .
   git commit -m "feat: Add new feature"
   ```

3. Push to remote:
   ```bash
   git push -u origin feature/new-feature
   ```

4. Create pull request on GitHub
5. Review, discuss, and merge after approval

---
Created by Héctor Corbellini for showcasing Java development skills and services.

Last updated: March 2025
