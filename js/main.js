<<<<<<< HEAD
// DOM Elements
const header = document.querySelector('.header');
const menuButton = document.querySelector('.header__menu-button');
const nav = document.querySelector('.header__nav');
const projectsContainer = document.querySelector('.projects');
const filterButtons = document.querySelectorAll('.project-filters__button');

// Header scroll behavior
let lastScroll = 0;
window.addEventListener('scroll', () => {
    const currentScroll = window.pageYOffset;
    
    if (currentScroll > lastScroll && currentScroll > 100) {
        header.classList.add('header--hidden');
    } else {
        header.classList.remove('header--hidden');
    }
    
    lastScroll = currentScroll;
=======
/**
 * Utility function to create and display a modal with content
 * @param {string} title - The title of the modal
 * @param {string} content - HTML content for the modal body
 */
function createModal(title, content) {
    console.log(`Showing ${title} modal`);
    
    // Create modal container
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h2>${title}</h2>
                <button class="close-btn">&times;</button>
            </div>
            <div class="modal-body">
                ${content}
            </div>
        </div>
    `;
    
    // Add close functionality
    modal.querySelector('.close-btn').addEventListener('click', () => {
        document.body.removeChild(modal);
    });
    
    // Close on outside click
    modal.addEventListener('click', (e) => {
        if (e.target === modal) {
            document.body.removeChild(modal);
        }
    });
    
    // Add modal to page
    document.body.appendChild(modal);
}

/**
 * Function to show ecosystem simulation overview
 */
function showEcosystemOverview() {
    const content = `
        <div class="overview-section description-section">
            <h3>Project Overview</h3>
            <p>The Ecosystem Simulation is a Java-based application that models a dynamic ecosystem with various organisms interacting in a grid-based environment. This project demonstrates complex interactions between different species, energy dynamics, and population growth patterns.</p>
            
            <p>The simulation follows principles of Clean Code architecture with a clear separation of concerns between the model, view, and controller components. The UI is built using Java Swing, providing an intuitive interface for controlling and observing the simulation.</p>
            
            <p>Users can adjust various parameters to observe how changes affect the ecosystem's balance. The simulation tracks energy levels, reproduction rates, and population statistics in real-time, offering insights into ecological principles such as carrying capacity, predator-prey relationships, and resource competition.</p>
        </div>
        
        <div class="overview-section">
            <h3>Key Features</h3>
            <ul>
                <li>Grid-based environment with dynamic animal and plant interactions</li>
                <li>Energy-based ecosystem with predator-prey relationships</li>
                <li>Reproduction mechanics based on energy levels</li>
                <li>Configurable simulation parameters</li>
                <li>Real-time visualization using Java Swing</li>
            </ul>
        </div>
        <div class="overview-section">
            <h3>Technologies Used</h3>
            <ul>
                <li>Java 17</li>
                <li>Swing UI Framework</li>
                <li>Maven for dependency management</li>
                <li>Clean Code architecture</li>
            </ul>
        </div>
        <div class="overview-section">
            <h3>How to Use</h3>
            <ol>
                <li>Click the Demo button to launch the simulation</li>
                <li>Use controls to start, pause, and reset the simulation</li>
                <li>Adjust parameters to see different behaviors</li>
                <li>Monitor energy levels and population statistics</li>
            </ol>
        </div>
    `;
    
    createModal('Ecosystem Simulation', content);
}

/**
 * Function to show Caesar Cipher overview
 */
function showCaesarCipherOverview() {
    const content = `
        <div class="overview-section description-section">
            <h3>Project Overview</h3>
            <p>The Caesar Cipher Encryption Tool is a Java-based web application that implements the classic Caesar cipher encryption method with a modern, user-friendly interface. This project demonstrates secure file handling, encryption algorithms, and responsive web design.</p>
            
            <p>The application follows modern web development practices and security principles, ensuring safe handling of user files and encryption processes. The UI is designed to be intuitive and responsive, making it accessible across different devices.</p>
        </div>
        
        <div class="overview-section">
            <h3>Key Features</h3>
            <ul>
                <li>File-based encryption and decryption</li>
                <li>Customizable encryption key</li>
                <li>Secure file handling</li>
                <li>Modern web interface</li>
                <li>Real-time encryption preview</li>
            </ul>
        </div>
        <div class="overview-section">
            <h3>Technologies Used</h3>
            <ul>
                <li>Java 17</li>
                <li>HTML5/CSS3</li>
                <li>JavaScript</li>
                <li>RESTful API design</li>
                <li>File handling libraries</li>
            </ul>
        </div>
        <div class="overview-section">
            <h3>How to Use</h3>
            <ol>
                <li>Upload a text file or enter text directly</li>
                <li>Choose an encryption key (shift value)</li>
                <li>Select encrypt or decrypt operation</li>
                <li>Download the processed file</li>
            </ol>
        </div>
    `;
    
    createModal('Caesar Cipher Encryption Tool', content);
}

/**
 * Utility function to create and display a notification
 * @param {string} message - The message to display
 * @param {string} type - Type of notification (info, success, error)
 * @param {string} icon - FontAwesome icon class
 * @param {number} duration - Duration in milliseconds before auto-removal
 */
function showNotification(message, type = 'info', icon = '', duration = 5000) {
    // Create notification element
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.innerHTML = icon ? `<i class="${icon}"></i> ${message}` : message;
    document.body.appendChild(notification);
    
    // Auto-remove after specified duration
    if (duration > 0) {
        setTimeout(() => {
            notification.classList.add('fade-out');
            setTimeout(() => {
                document.body.removeChild(notification);
            }, 500);
        }, duration);
    }
    
    return notification;
}

/**
 * Function to launch the ecosystem simulation
 */
/**
 * Updates an existing notification and schedules its removal
 * @param {HTMLElement} notification - The notification element to update
 * @param {string} message - The new message to display
 * @param {string} type - Type of notification (info, success, error)
 * @param {string} icon - FontAwesome icon class
 */
function updateAndRemoveNotification(notification, message, type, icon) {
    // Update notification
    notification.className = `notification ${type}`;
    notification.innerHTML = `<i class="${icon}"></i> ${message}`;
    
    // Remove notification after 5 seconds
    setTimeout(() => {
        notification.classList.add('fade-out');
        setTimeout(() => {
            if (notification.parentNode) {
                document.body.removeChild(notification);
            }
        }, 500);
    }, 5000);
}

/**
 * Function to launch the ecosystem simulation
 */
function launchSimulation() {
    // Show loading notification
    const notification = showNotification(
        'Launching Ecosystem Simulation...', 
        'info', 
        'fas fa-spinner fa-spin',
        0 // Don't auto-remove
    );
    
    // Call the API to launch the simulation
    fetch('/api/launch-ecosystem')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.text();
        })
        .then(data => {
            updateAndRemoveNotification(
                notification, 
                data, 
                'success', 
                'fas fa-check-circle'
            );
        })
        .catch(error => {
            console.error('Error:', error);
            updateAndRemoveNotification(
                notification, 
                'Error launching simulation', 
                'error', 
                'fas fa-exclamation-circle'
            );
        });
}

document.addEventListener('DOMContentLoaded', function() {
    // Update current year in footer
    document.getElementById('current-year').textContent = new Date().getFullYear();
    
    // Mobile menu toggle
    const hamburger = document.querySelector('.hamburger');
    const navLinks = document.querySelector('.nav-links');
    
    hamburger.addEventListener('click', () => {
        hamburger.classList.toggle('active');
        navLinks.classList.toggle('active');
    });
    
    // Close mobile menu when clicking on a link
    document.querySelectorAll('.nav-links a').forEach(link => {
        link.addEventListener('click', () => {
            hamburger.classList.remove('active');
            navLinks.classList.remove('active');
        });
    });
    
    // Smooth scrolling for navigation links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            
            const targetId = this.getAttribute('href');
            const targetElement = document.querySelector(targetId);
            
            if (targetElement) {
                window.scrollTo({
                    top: targetElement.offsetTop - 80,
                    behavior: 'smooth'
                });
            }
        });
    });
    
    // Project data
    const codegymProjects = [
        {
            title: "Caesar Cipher Encryption Tool",
            description: "A Java-based file encryption/decryption application implementing the Caesar cipher method with a modern web interface.",
            image: "/images/crypto.jpg",
            tags: ["Java", "Cryptography", "Web UI"],
            demoLink: "/projects/caesar-cipher/index.html",
            codeLink: "https://github.com/HectorCorbellini/encryptor-windsurf"
        },
        {
            title: "Ecosystem Simulation",
            description: "A Java-based ecosystem simulation demonstrating complex interactions between animals and plants in a grid-based environment. Features include energy dynamics, reproduction mechanics, and configurable parameters.",
            image: "/images/ecosystem.png",
            tags: ["Java", "Swing UI", "Simulation"],
            demoLink: "#",
            overview: {
                features: [
                    "Grid-based environment with dynamic animal and plant interactions",
                    "Energy-based ecosystem with predator-prey relationships",
                    "Reproduction mechanics based on energy levels",
                    "Configurable simulation parameters",
                    "Real-time visualization using Java Swing"
                ],
                technologies: [
                    "Java 17",
                    "Swing UI Framework",
                    "Maven for dependency management",
                    "Clean Code architecture"
                ],
                instructions: [
                    "Click the Demo button to launch the simulation",
                    "Use controls to start, pause, and reset the simulation",
                    "Adjust parameters to see different behaviors",
                    "Monitor energy levels and population statistics"
                ]
            },
            codeLink: "https://github.com/HectorCorbellini/Portfolio-windsurf-march6/tree/ecosystem-simulation-clean-code"
        },
        {
            title: "E-commerce Platform",
            description: "Full-featured e-commerce platform with product catalog, shopping cart, and payment processing.",
            image: "/images/ecommerce.jpg",
            tags: ["Java", "Spring MVC", "Hibernate", "MySQL"],
            demoLink: "#",
            codeLink: "#"
        }
    ];
    
    const commercialProjects = [
        {
            title: "Banking System",
            description: "Secure banking application with transaction management and account services.",
            image: "/images/banking.jpg",
            tags: ["Java", "Spring Security", "PostgreSQL"],
            demoLink: "#",
            codeLink: "#"
        },
        {
            title: "Inventory Management",
            description: "Enterprise inventory tracking system with barcode scanning and reporting features.",
            image: "/images/inventory.jpg",
            tags: ["Java", "Spring Boot", "React", "MongoDB"],
            demoLink: "#",
            codeLink: "#"
        },
        {
            title: "CRM Solution",
            description: "Customer relationship management system with lead tracking and sales analytics.",
            image: "/images/crm.jpg",
            tags: ["Java", "Microservices", "Docker", "Kubernetes"],
            demoLink: "#",
            codeLink: "#"
        }
    ];
    
    const teamProjects = [
        {
            title: "Eco Tracker",
            description: "Open-source application for tracking and reducing carbon footprint with community features.",
            image: "/images/ecotracker.jpg",
            tags: ["Java", "Spring Boot", "Vue.js", "PostgreSQL"],
            demoLink: "#",
            codeLink: "#"
        },
        {
            title: "Community Aid Platform",
            description: "Platform connecting volunteers with people in need during crisis situations.",
            image: "/images/community.jpg",
            tags: ["Java", "Spring", "Angular", "Firebase"],
            demoLink: "#",
            codeLink: "#"
        },
        {
            title: "Open Learning",
            description: "Educational platform providing free programming courses to underserved communities.",
            image: "/images/learning.jpg",
            tags: ["Java", "Spring Boot", "React", "MongoDB"],
            demoLink: "#",
            codeLink: "#"
        }
    ];
    
    /**
     * Creates the image element for a project card
     * @param {Object} project - Project data object
     * @returns {HTMLElement} - The image container element
     */
    function createCardImage(project) {
        const imageDiv = document.createElement('div');
        imageDiv.className = 'card-image';
        
        const img = document.createElement('img');
        img.src = project.image;
        img.alt = project.title;
        
        imageDiv.appendChild(img);
        return imageDiv;
    }
    
    /**
     * Creates the content section for a project card
     * @param {Object} project - Project data object
     * @returns {HTMLElement} - The content container element
     */
    function createCardContent(project) {
        const contentDiv = document.createElement('div');
        contentDiv.className = 'card-content';
        
        // Title
        const title = document.createElement('h3');
        title.className = 'card-title';
        title.textContent = project.title;
        contentDiv.appendChild(title);
        
        // Description
        const description = document.createElement('p');
        description.className = 'card-description';
        description.textContent = project.description;
        contentDiv.appendChild(description);
        
        // Tags
        const tagsDiv = document.createElement('div');
        tagsDiv.className = 'card-tags';
        project.tags.forEach(tag => {
            const tagSpan = document.createElement('span');
            tagSpan.className = 'card-tag';
            tagSpan.textContent = tag;
            tagsDiv.appendChild(tagSpan);
        });
        contentDiv.appendChild(tagsDiv);
        
        // Links
        const linksDiv = document.createElement('div');
        linksDiv.className = 'card-links';
        
        // Demo button/link
        if (project.title === "Ecosystem Simulation") {
            const demoButton = document.createElement('button');
            demoButton.className = 'card-link';
            demoButton.innerHTML = '<i class="fas fa-play"></i> Demo';
            demoButton.addEventListener('click', launchSimulation);
            linksDiv.appendChild(demoButton);
        } else if (project.demoLink) {
            const demoLink = document.createElement('a');
            demoLink.href = project.demoLink;
            demoLink.className = 'card-link';
            demoLink.innerHTML = '<i class="fas fa-external-link-alt"></i> Demo';
            linksDiv.appendChild(demoLink);
        }
        
        // Code link
        const codeLink = document.createElement('a');
        codeLink.href = project.codeLink;
        codeLink.className = 'card-link';
        codeLink.innerHTML = '<i class="fab fa-github"></i> Code';
        linksDiv.appendChild(codeLink);
        
        // Overview button for projects with overview
        if (project.title === "Ecosystem Simulation" || project.title === "Caesar Cipher Encryption Tool") {
            const overviewButton = document.createElement('button');
            overviewButton.className = 'card-link';
            overviewButton.innerHTML = '<i class="fas fa-info-circle"></i> Overview';
            
            // Set the appropriate click handler based on the project
            if (project.title === "Ecosystem Simulation") {
                overviewButton.addEventListener('click', showEcosystemOverview);
            } else {
                overviewButton.addEventListener('click', showCaesarCipherOverview);
            }
            
            linksDiv.appendChild(overviewButton);
        }
        
        contentDiv.appendChild(linksDiv);
        return contentDiv;
    }
    
    /**
     * Function to create project cards
     * @param {Object} project - Project data object
     * @returns {HTMLElement} - The complete project card
     */
    function createProjectCard(project) {
        const card = document.createElement('div');
        card.className = 'project-card';
        
        card.appendChild(createCardImage(project));
        card.appendChild(createCardContent(project));
        
        return card;
    }
    
    // Populate project sections
    const projectsGrid = document.querySelector('.projects-grid');
    const commercialGrid = document.querySelector('.commercial-grid');
    const teamGrid = document.querySelector('.team-grid');
    
    codegymProjects.forEach(project => {
        projectsGrid.appendChild(createProjectCard(project));
    });
    
    commercialProjects.forEach(project => {
        commercialGrid.appendChild(createProjectCard(project));
    });
    
    teamProjects.forEach(project => {
        teamGrid.appendChild(createProjectCard(project));
    });
    
    // Contact form handling
    const contactForm = document.getElementById('contactForm');
    if (contactForm) {
        contactForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Get form data
            const name = document.getElementById('name').value;
            const email = document.getElementById('email').value;
            const subject = document.getElementById('subject').value;
            const message = document.getElementById('message').value;
            
            // Here you would typically send the data to a server
            // For now, we'll just log it to the console
            console.log('Form submission:', { name, email, subject, message });
            
            // Show success message using our notification utility
            showNotification(
                'Thank you for your message! I will get back to you soon.',
                'success',
                'fas fa-check-circle'
            );
            
            // Reset form
            contactForm.reset();
        });
    }
    
    // Intersection Observer for fade-in animations
    const observerOptions = {
        threshold: 0.1
    };
    
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('fade-in');
                observer.unobserve(entry.target);
            }
        });
    }, observerOptions);
    
    // Observe all section titles and project cards
    document.querySelectorAll('.section-title, .project-card').forEach(el => {
        el.style.opacity = '0';
        el.style.transform = 'translateY(20px)';
        el.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
        observer.observe(el);
    });
    
    // Add fade-in class for animation
    document.head.insertAdjacentHTML('beforeend', `
        <style>
            .fade-in {
                opacity: 1 !important;
                transform: translateY(0) !important;
            }
        </style>
    `);
>>>>>>> 234ac52 (Implemented Overview buttons for projects and improved error handling)
});

// Mobile menu toggle
menuButton.addEventListener('click', () => {
    nav.classList.toggle('header__nav--active');
    menuButton.setAttribute('aria-expanded', 
        menuButton.getAttribute('aria-expanded') === 'true' ? 'false' : 'true'
    );
});

// Project filtering
const projects = [
    {
        title: 'E-commerce Platform',
        description: 'A full-stack e-commerce solution built with Spring Boot and React',
        image: '/images/projects/ecommerce.jpg',
        tags: ['java', 'spring', 'web'],
        github: 'https://github.com/username/ecommerce',
        demo: 'https://demo.example.com/ecommerce'
    },
    // Add more projects here
];

function createProjectCard(project) {
    return `
        <article class="project-card fade-in">
            <img class="project-card__image" src="${project.image}" alt="${project.title}">
            <div class="project-card__content">
                <h3 class="project-card__title">${project.title}</h3>
                <p class="project-card__description">${project.description}</p>
                <div class="project-card__tags">
                    ${project.tags.map(tag => `<span class="project-card__tag">${tag}</span>`).join('')}
                </div>
                <div class="project-card__links">
                    <a href="${project.github}" class="project-card__link" target="_blank" rel="noopener">
                        GitHub
                    </a>
                    <a href="${project.demo}" class="project-card__link" target="_blank" rel="noopener">
                        Live Demo
                    </a>
                </div>
            </div>
        </article>
    `;
}

function filterProjects(filter) {
    const filteredProjects = filter === 'all' 
        ? projects 
        : projects.filter(project => project.tags.includes(filter));
    
    projectsContainer.innerHTML = filteredProjects.map(createProjectCard).join('');
}

// Initialize projects
filterProjects('all');

// Filter button click handlers
filterButtons.forEach(button => {
    button.addEventListener('click', () => {
        // Update active button
        filterButtons.forEach(btn => btn.classList.remove('project-filters__button--active'));
        button.classList.add('project-filters__button--active');
        
        // Filter projects
        filterProjects(button.dataset.filter);
    });
});

// Update current year in footer
document.getElementById('current-year').textContent = new Date().getFullYear();

// Close mobile menu when clicking on a link
document.querySelectorAll('.nav-links a').forEach(link => {
    link.addEventListener('click', () => {
        menuButton.classList.remove('header__menu-button--active');
        nav.classList.remove('header__nav--active');
    });
});

// Smooth scrolling for navigation links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function(e) {
        e.preventDefault();
        
        const targetId = this.getAttribute('href');
        const targetElement = document.querySelector(targetId);
        
        if (targetElement) {
            window.scrollTo({
                top: targetElement.offsetTop - 80,
                behavior: 'smooth'
            });
        }
    });
});

// Intersection Observer for fade-in animations
const observerOptions = {
    threshold: 0.1
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('fade-in');
            observer.unobserve(entry.target);
        }
    });
}, observerOptions);

// Observe all section titles and project cards
document.querySelectorAll('.section-title, .project-card').forEach(el => {
    el.style.opacity = '0';
    el.style.transform = 'translateY(20px)';
    el.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
    observer.observe(el);
});

// Add fade-in class for animation
document.head.insertAdjacentHTML('beforeend', `
    <style>
        .fade-in {
            opacity: 1 !important;
            transform: translateY(0) !important;
        }
    </style>
`);

// Contact form handling
const contactForm = document.getElementById('contactForm');
if (contactForm) {
    contactForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        // Get form data
        const name = document.getElementById('name').value;
        const email = document.getElementById('email').value;
        const subject = document.getElementById('subject').value;
        const message = document.getElementById('message').value;
        
        // Here you would typically send the data to a server
        // For now, we'll just log it to the console
        console.log('Form submission:', { name, email, subject, message });
        
        // Show success message (in a real app, this would happen after server confirmation)
        alert('Thank you for your message! I will get back to you soon.');
        
        // Reset form
        contactForm.reset();
    });
}
