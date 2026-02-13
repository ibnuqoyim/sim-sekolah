# School Management System (SIM)

## Overview
Based on Spring Boot (Backend) and Vue.js (Frontend), this School Management System (SIM) is designed to streamline school operations, including user management, academic tracking, and teacher evaluations.

## Project Status
**Current Phase**: Phase 2 (User & School Management) - COMPLETED

### Completed Phases
- **Phase 1: Core Infrastructure & Authentication**: Project setup, Database, JWT Authentication.
- **Phase 2: User, School, Teacher, & Student Management**: CRUD operations for users, school profile management, and role-based access control.

## Tech Stack
*   **Backend**: Java 21, Spring Boot 3.4.2, PostgreSQL, Flyway, Spring Security (JWT)
*   **Frontend**: Vue 3, Vite, Tailwind CSS 4, Pinia, Axios

## Features Implemented
### Phase 1: Core
*   **Authentication**: Secure Login API with JWT generation and validation.
*   **Database**: PostgreSQL integration with Flyway migrations.
*   **Frontend**: Responsive Login and Home pages.

### Phase 2: Management
*   **School Profile**: Admin can update school details (Name, Address, Logo, Contact).
*   **User Management**: Admin can Create, Read, and Delete users (Admin, Operator, Teacher, Student).
*   **Teacher Management**: Operators and Admins can manage Teacher profiles (linked to Users).
*   **Student Management**: Operators and Admins can manage Student profiles (linked to Users).
*   **Role-Based Access Control (RBAC)**: secure endpoints based on roles.
*   **UI/UX Improvements**: Modern dashboard design, responsive layouts, and consistent styling.

## Project Structure
```
sim-spring-bot/
├── backend/            # Spring Boot Application
│   ├── src/main/java/  # Source Code
│   ├── .env.example    # Environment variables template
│   └── pom.xml         # Maven Config
├── frontend/           # Vue.js Application
│   ├── src/            # Source Code
│   ├── .env.example    # Environment variables template
│   └── package.json    # NPM Config
└── README.md           # Project Documentation
```

## Setup & Running

### Prerequisites
*   Java 21+
*   Node.js 18+
*   PostgreSQL (Database: `sim_db`)

### Backend Setup
1.  Navigate to the backend directory:
    ```bash
    cd backend
    ```
2.  Create a `.env` file (copy from `.env.example` if available) or set the following variables:
    ```properties
    DB_URL=jdbc:postgresql://localhost:5432/sim_db
    DB_USERNAME=postgres
    DB_PASSWORD=postgres
    SERVER_PORT=8080
    JWT_SECRET=YourSuperSecretKeyHere
    JWT_EXPIRATION_MS=86400000
    CORS_ALLOWED_ORIGINS=http://localhost:5173
    ```
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
2.  Create a `.env` file:
    ```properties
    VITE_API_BASE_URL=http://localhost:8080/api
    ```
3.  Install dependencies:
    ```bash
    npm install
    ```
4.  Start the development server:
    ```bash
    npm run dev
    ```
    *The application will start on `http://localhost:5173`.*

## Default Credentials
*   **Username**: `admin`
*   **Password**: `password` (seeded via Flyway `V1__init.sql`)

## API Endpoints Overview
*   **Auth**: `POST /api/auth/login`
*   **School**: `GET /api/school/profile` (Public), `PUT /api/school/profile` (Admin)
*   **Users**: `GET/POST/DELETE /api/users` (Admin)
*   **Teachers**: `GET/POST/DELETE /api/teachers` (Operator/Admin)
*   **Students**: `GET/POST/DELETE /api/students` (Operator/Admin)

## Documentation
*   [Technical Requirements (TRD)](technical_requirements.md)
*   [Database Schema (ERD)](database.puml)
