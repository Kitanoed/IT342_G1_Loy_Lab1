import { Link } from 'react-router-dom';
import authService from '../services/authService';
import '../styles/Home.css';

const Home = () => {
  const isAuthenticated = authService.isAuthenticated();
  const user = authService.getStoredUser();

  return (
    <div className="home-container">
      <div className="bg-orbs">
        <div className="orb"></div>
        <div className="orb"></div>
        <div className="orb"></div>
      </div>
      <div className="home-content">
        <h1>Mini App</h1>
        <p>Register, login, view dashboard, and logout.</p>

        <div className="button-group">
          {isAuthenticated ? (
            <>
              <p className="welcome-message">Welcome back, {user?.username || 'User'}!</p>
              <Link to="/dashboard" className="btn btn-primary">
                Go to Dashboard
              </Link>
              <button
                onClick={async () => {
                  await authService.logout();
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

        
      </div>
    </div>
  );
};

export default Home;