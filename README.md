# IT342_G1_Loy_Lab1 - Authentication System

A full-stack authentication system built with **Spring Boot** backend and **React** frontend with JWT-based authentication, BCrypt password encryption, and MySQL database integration.

## Features

✅ **User Registration** - Secure user registration with encrypted passwords  
✅ **User Login** - JWT-based authentication system  
✅ **Protected Routes** - Dashboard accessible only to authenticated users  
✅ **User Profile** - View authenticated user information  
✅ **Password Encryption** - BCrypt hashing for secure password storage  
✅ **MySQL Database** - Persistent data storage  
✅ **CORS Support** - Configured for cross-origin requests  

---

## Backend (Spring Boot)

### Prerequisites
- Java 21
- Maven 3.8+
- MySQL 8.0+

### Setup Instructions

1. **Create MySQL Database:**
```bash
mysql -u root -p
CREATE DATABASE it342_auth_db;
EXIT;
```

2. **Configure Application Properties:**
Edit `backend/backend/src/main/resources/application.properties`

3. **Build the Backend:**
```bash
cd backend/backend
mvn clean install
```

4. **Run the Backend:**
```bash
mvn spring-boot:run
```

The server will start at `http://localhost:8080` with API base URL `http://localhost:8080/api`

### API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login user |
| GET | `/api/user/me` | Get current user (protected) |

---

## Frontend (React)

### Prerequisites
- Node.js 16+
- npm or yarn

### Setup Instructions

1. **Install Dependencies:**
```bash
cd web
npm install
```

2. **Run Development Server:**
```bash
npm run dev
```

The application will be available at `http://localhost:5173`

### Pages

- **Home** (`/`) - Landing page with navigation
- **Register** (`/register`) - User registration form
- **Login** (`/login`) - User login form
- **Dashboard** (`/dashboard`) - Protected user profile page (requires authentication)

---

## Testing the Application

### 1. Start MySQL Server

### 2. Create Database
```bash
mysql -u root -p
CREATE DATABASE it342_auth_db;
```

### 3. Start Backend Server
```bash
cd backend/backend
mvn spring-boot:run
```

### 4. Start Frontend Server (in a new terminal)
```bash
cd web
npm run dev
```

### 5. Test Registration and Login
1. Navigate to `http://localhost:5173`
2. Click Register and create a new account
3. You'll be redirected to Dashboard
4. Click Logout and log back in
5. Verify protected routes work correctly

---

## Technical Stack

**Backend:**
- Spring Boot 4.0.2
- Spring Security with JWT
- Spring Data JPA
- BCrypt Password Encryption
- MySQL Database
- Maven

**Frontend:**
- React 19.2
- React Router DOM
- Axios for HTTP requests
- Vite development server

---

## Security Features

🔒 Password encryption with BCrypt  
🔐 JWT token-based authentication  
🛡️ CORS configured for specific origins  
✅ Protected routes requiring authentication  
🔑 Secure token storage in localStorage  

---

## Author

Group 1 - Loy Lab 1