/**
 * Project data for the portfolio
 * Centralizes all project information in one place
 */
export const projectData = {
    codegymProjects: [
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
            demoLink: null,
            codeLink: "https://github.com/HectorCorbellini/Portfolio-windsurf-march6/tree/ecosystem-simulation-clean-code"
        }
    ],
    commercialProjects: [
        {
            title: "CRM System",
            description: "Custom CRM solution for a financial services company with client management, appointment scheduling, and document processing.",
            image: "/images/crm.jpg",
            tags: ["Java", "Spring Boot", "React"],
            demoLink: null,
            codeLink: null
        },
        {
            title: "Code Transformer",
            description: "Java application designed to combine multiple code files from a directory into a single text file for easy sharing with AI tools like ChatGPT, Gemini, and GitHub Copilot.",
            image: "/images/codeTransformer.png",
            tags: ["Java", "Swing UI", "File Processing"],
            demoLink: null,
            codeLink: "https://github.com/HectorCorbellini/code-transformer"
        }
    ],
    teamProjects: [
        {
            title: "Task Management Application",
            description: "Collaborative task management tool with real-time updates, file sharing, and team communication features.",
            image: "/images/taskmanager.jpg",
            tags: ["Java", "Spring", "WebSocket"],
            demoLink: null,
            codeLink: null
        },
        {
            title: "Healthcare Portal",
            description: "Patient management system with appointment scheduling, medical records, and billing integration.",
            image: "/images/healthcare.jpg",
            tags: ["Java", "Hibernate", "Angular"],
            demoLink: null,
            codeLink: null
        }
    ]
};

/**
 * Project configuration for detailed overviews
 */
export const projectConfig = {
    ecosystem: {
        title: "Ecosystem Simulation",
        overview: {
            description: "The Ecosystem Simulation is a Java-based application that models a dynamic ecosystem with various organisms interacting in a grid-based environment. This project demonstrates complex interactions between different species, energy dynamics, and population growth patterns.",
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
        }
    },
    caesarCipher: {
        title: "Caesar Cipher Encryption Tool",
        overview: {
            description: "The Caesar Cipher Encryption Tool is a Java-based web application that implements the classic Caesar cipher encryption method with a modern, user-friendly interface. This project demonstrates secure file handling, encryption algorithms, and responsive web design.",
            features: [
                "File-based encryption and decryption",
                "Customizable encryption key",
                "Secure file handling",
                "Modern web interface",
                "Real-time encryption preview"
            ],
            technologies: [
                "Java 17",
                "HTML5/CSS3",
                "JavaScript",
                "RESTful API design",
                "File handling libraries"
            ],
            instructions: [
                "Upload a text file or enter text directly",
                "Choose an encryption key (shift value)",
                "Select encrypt or decrypt operation",
                "Download the processed file"
            ]
        }
    },
    codeTransformer: {
        title: "Code Transformer",
        overview: {
            description: "Code Transformer is a Java application designed to combine multiple code files from a directory into a single text file. This makes it easier to share codebases with AI tools like ChatGPT, Gemini, GitHub Copilot, and Codeium for analysis and interpretation.",
            features: [
                "Directory Processing: Recursively processes all code files in a selected directory",
                "Intelligent Filtering: Automatically excludes non-code files (binaries, images, etc.)",
                "Structured Output: Preserves file paths and hierarchical structure in the output",
                "Drag & Drop Support: Easily select directories by dragging them into the application",
                "Clipboard Integration: Copy the generated code directly to clipboard",
                "AI Platform Integration: Dedicated buttons for popular AI platforms"
            ],
            technologies: [
                "Java 11+",
                "Swing UI Framework",
                "Maven for dependency management",
                "Clean Architecture principles"
            ],
            instructions: [
                "Select a directory containing code files",
                "Click 'Transform Code' to process the files",
                "View the combined output in the application",
                "Copy to clipboard or open directly in an AI platform",
                "Use the generated file to get AI assistance with your code"
            ]
        }
    }
};

/**
 * Notification configuration
 */
export const notificationConfig = {
    defaultDuration: 5000,
    fadeOutDuration: 500,
    types: {
        info: 'info',
        success: 'success',
        error: 'error'
    },
    icons: {
        loading: 'fas fa-spinner fa-spin',
        success: 'fas fa-check-circle',
        error: 'fas fa-exclamation-circle'
    }
};
