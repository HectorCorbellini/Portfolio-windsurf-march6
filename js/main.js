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
