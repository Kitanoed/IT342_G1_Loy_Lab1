# Authentication System - Task Checklist

## Backend Setup
- [x] Create Maven pom.xml with dependencies (Spring Boot, JPA, Security, MySQL, JJWT)
- [x] Configure application.properties (MySQL, JWT settings, server config)
- [x] Create User entity with JPA mappings
- [x] Create UserRepository with custom queries
- [x] Create UserService with BCrypt password encoding
- [x] Create JwtUtil for token generation & validation
- [x] Create JwtAuthenticationFilter for JWT extraction
- [x] Create SecurityConfig with CORS & auth chain
- [x] Create AuthController (POST /auth/register, POST /auth/login)
- [x] Create UserController (GET /user/me with @Secured)
- [x] Create DTOs (RegisterRequest, LoginRequest, AuthResponse, UserDTO)
- [ ] Build backend JAR with Maven (fixing Java 21 → 17 version issue)
- [ ] Start backend server (port 8080, context-path /api)
- [ ] Verify backend endpoints are responding

## Frontend Setup
- [x] Create React project with Vite
- [x] Install dependencies (React Router, Axios)
- [x] Create authService.js with API client & interceptors
- [x] Create ProtectedRoute component for guarding routes
- [x] Create Register page (POST /api/auth/register)
- [x] Create Login page (POST /api/auth/login)
- [x] Create Dashboard page (protected, GET /api/user/me)
- [x] Create Home page (landing page)
- [x] Set up React Router with 4 routes
- [x] Start React frontend (npm run dev)

## Testing
- [ ] Test backend build succeeds (./mvnw.cmd clean package)
- [ ] Test POST /api/auth/register with valid data
- [ ] Test POST /api/auth/login with valid credentials
- [ ] Test GET /api/user/me with JWT token (returns user profile)
- [ ] Test GET /api/user/me without token (returns 401)
- [ ] Test React Register → Login → Dashboard flow
- [ ] Test token persistence in localStorage
- [ ] Test logout clears token & redirects to Login

## Database
- [ ] Verify MySQL running on localhost:3306
- [ ] Verify database `it342_auth_db` created with `user` table
- [ ] Verify Hibernate DDL auto-creates/updates schema

## Deployment Ready
- [ ] Backend running without errors
- [ ] Frontend connected to backend API
- [ ] All 3 auth endpoints functional
- [ ] Protected route working
- [ ] Full auth flow tested end-to-end
