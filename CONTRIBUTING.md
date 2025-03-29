# Contributing Guide

Thank you for your interest in contributing to this professional Java Developer Portfolio project. This document provides guidelines and instructions for contributing to or customizing this portfolio.

## Development Environment Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start the development server**
   ```bash
   npm run dev
   ```
   This will start the server with hot reloading enabled.

## Project Structure

- `server.js` - Main Node.js server file
- `server/` - Server-side utilities and configuration
- `js/` - Frontend JavaScript files
- `css/` - Stylesheet files
- `images/` - Project and profile images
- `projects/` - Project demo files and launch scripts
- `index.html` - Main HTML file

## Customization Guide

### Modifying Personal Information

Edit the `index.html` file to update:
- Your name and profession in the header
- About Me section content
- Contact information and social links

### Customizing Projects

Projects are defined in `js/data/projects.js`. Update this file to add or modify projects:

```javascript
{
  title: "Project Name",
  description: "Project description",
  image: "/images/project-image.jpg",
  tags: ["Tag1", "Tag2", "Tag3"],
  demoLink: "/path/to/demo.html", // or null if no demo
  codeLink: "https://github.com/username/repo" // or null if private
}
```

For projects with launchers (like Java applications), you'll need to:
1. Create a launch script in the `projects/<project-name>/` directory
2. Add a route handler in `server/router.js`
3. Create an API service method in `js/services/api.js`

### Adding Custom Demos

To add a custom demo page:
1. Create an HTML file in `projects/<project-name>/index.html`
2. Update the project entry in `js/data/projects.js` with the correct `demoLink`

### Styling Customization

The main styles are in `css/styles.css`. The portfolio uses CSS variables for theming, which can be found at the top of the stylesheet:

```css
:root {
  --primary-color: #6200ea;
  --secondary-color: #03dac6;
  --bg-dark: #121212;
  /* ...other variables */
}
```

## Performance Considerations

- Optimize image sizes using tools like [TinyPNG](https://tinypng.com/)
- Minify JavaScript and CSS for production
- Use appropriate image formats (SVG for icons, WebP for photos where possible)

## Testing

Please test your changes in multiple browsers to ensure compatibility:
- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Submitting Changes

1. Create a new branch for your changes
2. Make your changes and commit them
3. Test thoroughly
4. Submit a pull request with a clear description of your changes

## License

This project is under the MIT License. See the LICENSE file for details. 