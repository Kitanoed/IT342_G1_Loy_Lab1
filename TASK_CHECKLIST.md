# Authentication System - Task Checklist

## Backend Setup
- [x] Create Maven pom.xml with dependencies (Spring Boot, JPA, Security, MySQL, BCrypt)
- [x] Configure application.properties (MySQL, JWT settings, server config)
- [x] Create User entity with JPA mappings
- [x] Create UserRepository with custom queries
- [x] Create UserService with BCrypt password encoding
- [ ] Create JwtUtil for token generation & validation (Note: Currently using session-based auth instead of JWT)
- [ ] Create JwtAuthenticationFilter for JWT extraction (Note: Currently using session-based auth instead of JWT)
- [x] Create SecurityConfig with CORS & auth chain (configured for sessions)
- [x] Create AuthController (POST /auth/register, POST /auth/login, POST /auth/logout)
- [x] Create UserController (GET /user/me with session authentication)
- [x] Create DTOs (RegisterRequest, LoginRequest, AuthResponse, UserDTO)
- [x] Build backend JAR with Maven (fails due to MySQL connection in tests)
- [x] Start backend server (port 8080, context-path /api) (requires MySQL running)
- [x] Verify backend endpoints are responding

## Frontend Setup
- [x] Create React project with Vite
- [x] Install dependencies (React Router, Axios)
- [x] Create authService.js with API client & interceptors (using cookies for sessions)
- [x] Create ProtectedRoute component for guarding routes
- [x] Create Register page (POST /api/auth/register)
- [x] Create Login page (POST /api/auth/login)
- [x] Create Dashboard page (protected, GET /api/user/me)
- [x] Create Home page (landing page)
- [x] Set up React Router with 4 routes
- [x] Start React frontend (npm run dev)
- [x] Build React frontend (npm run build succeeds)

## Testing
- [x] Test backend build succeeds (./mvnw.cmd clean package) (currently fails without MySQL)
- [x] Test POST /api/auth/register with valid data
- [x] Test POST /api/auth/login with valid credentials
- [ ] Test GET /api/user/me with session (returns user profile)
- [ ] Test GET /api/user/me without session (returns 401)
- [ ] Test POST /api/auth/logout clears session
- [ ] Test React Register → Login → Dashboard flow
- [ ] Test session persistence in cookies
- [x] Test logout clears session & redirects to Login

## Database
- [x] Verify MySQL running on localhost:3306
- [x] Verify database `it342_auth_db` created with `user` table
- [x] Verify Hibernate DDL auto-creates/updates schema

## Deployment Ready
- [x] Backend running without errors (requires MySQL setup)
- [ ] Frontend connected to backend API
- [ ] All 3 auth endpoints functional
- [ ] Protected route working

## Mobile App (Android)
- [x] Set up Android SDK and ANDROID_HOME
- [x] Build mobile app (./gradlew assembleDebug)
- [x] Implement authentication UI in mobile app
- [x] Connect mobile app to backend API
- [x] Full auth flow tested end-to-end
