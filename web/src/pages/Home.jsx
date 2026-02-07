import { Link } from 'react-router-dom';
import authService from '../services/authService';
import '../styles/Home.css';

const Home = () => {
  const isAuthenticated = authService.isAuthenticated();
  const user = authService.getStoredUser();

  return (
    <div className="home-container">
      <div className="home-content">
        <h1></h1>
        <p></p>

        <div className="button-group">
          {isAuthenticated ? (
            <>
              <p className="welcome-message">Welcome back, {user?.username || 'User'}!</p>
              <Link to="/dashboard" className="btn btn-primary">
                Go to Dashboard
              </Link>
              <button 
                onClick={() => {
                  authService.logout();
                  window.location.href = '/';
                }}
                className="btn btn-logout"
              >
                Logout
              </button>
            </>
          ) : (
            <>
              <Link to="/register" className="btn btn-primary">
                Register
              </Link>
              <Link to="/login" className="btn btn-secondary">
                Login
              </Link>
            </>
          )}
        </div>

        <div className="features">
          <h2>Features</h2>
          <ul>
            <li>✓ Secure user registration</li>
            <li>✓ JWT-based authentication</li>
            <li>✓ Password encryption with BCrypt</li>
            <li>✓ Protected routes</li>
            <li>✓ User profile management</li>
            <li>✓ MySQL database integration</li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Home;
