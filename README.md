# School Management System (SIM)

## Overview
Based on Spring Boot (Backend) and Vue.js (Frontend), this School Management System (SIM) is designed to streamline school operations, including user management, academic tracking, and teacher evaluations.

## Phase 1: Core Infrastructure & Authentication
This phase establishes the foundation of the application, including the project structure, database connection, and a secure authentication system using JWT.

### Tech Stack
*   **Backend**: Java 21, Spring Boot 3.4.2, PostgreSQL, Flyway, Spring Security (JWT)
*   **Frontend**: Vue 3, Vite, Tailwind CSS 4, Pinia, Axios

### Features Implemented
*   **User Management**: `User` entity with Role-Based Access Control (Admin, Operator, Teacher, Student).
*   **Authentication**: Secure Login API with JWT generation and validation.
*   **Database**: PostgreSQL integration with Flyway for version-controlled migrations.
*   **Frontend UI**: Responsive Login page and protected Home page using Tailwind CSS.
*   **State Management**: Pinia store for handling authentication state.

## Project Structure
```
sim-spring-bot/
├── backend/            # Spring Boot Application
│   ├── src/main/java/  # Source Code
│   └── pom.xml         # Maven Config
├── frontend/           # Vue.js Application
│   ├── src/            # Source Code
│   └── package.json    # NPM Config
└── README.md           # Project Documentation
```

## Setup & Running

### Prerequisites
*   Java 17+
*   Node.js 18+
*   PostgreSQL (Database: `sim_db`)

### Backend Setup
1.  Navigate to the backend directory:
    ```bash
    cd backend
    ```
2.  Configure database credentials in `src/main/resources/application.yml` if necessary.
3.  Run the application:
    ```bash
    mvn spring-boot:run
    ```
    *The application will start on `http://localhost:8080`.*

### Frontend Setup
1.  Navigate to the frontend directory:
    ```bash
    cd frontend
    ```
2.  Install dependencies:
    ```bash
    npm install
    ```
3.  Start the development server:
    ```bash
    npm run dev
    ```
    *The application will start on `http://localhost:5173`.*

### Default Credentials
*   **Username**: `admin`
*   **Password**: `password` (seeded via Flyway)

## API Endpoints (Phase 1)
*   `POST /api/auth/login`: Authenticate user and return JWT.

## Docs
*   [Technical Requirements (TRD)](technical_requirements.md)
*   [Database Schema (ERD)](database.puml)
