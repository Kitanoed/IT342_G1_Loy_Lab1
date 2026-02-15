import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import authService from '../services/authService';
import '../styles/Dashboard.css';

const Dashboard = () => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUserProfile = async () => {
      try {
        const userData = await authService.getCurrentUser();
        setUser(userData);
      } catch (err) {
        setError(err.message || 'Failed to load user profile');
        console.error('Error fetching user:', err);
        navigate('/login');
      } finally {
        setLoading(false);
      }
    };

    // Fetch fresh user data
    fetchUserProfile();
  }, [navigate]);

  const handleLogout = async () => {
    await authService.logout();
    navigate('/login');
  };

  if (loading) {
    return (
      <div className="dashboard-container">
        <div className="bg-orbs">
          <div className="orb"></div>
          <div className="orb"></div>
          <div className="orb"></div>
        </div>
        <p style={{ position: 'relative', zIndex: 1, color: 'var(--text-secondary)', textAlign: 'center', paddingTop: '40vh' }}>Loading profile...</p>
      </div>
    );
  }

  return (
    <div className="dashboard-container">
      <div className="bg-orbs">
        <div className="orb"></div>
        <div className="orb"></div>
        <div className="orb"></div>
      </div>
      <div className="dashboard-header">
        <h1>Dashboard</h1>
        <button onClick={handleLogout} className="logout-button">
          Logout
        </button>
      </div>

      {error && <div className="error-message">{error}</div>}

      {user && (
        <div className="user-profile">
          <div className="profile-card">
            <h2>User Profile</h2>
            
            <div className="profile-info">
              <div className="info-item">
                <label>Full Name:</label>
                <p>{user.firstName} {user.lastName}</p>
              </div>

              <div className="info-item">
                <label>Username:</label>
                <p>{user.username}</p>
              </div>

              <div className="info-item">
                <label>Email:</label>
                <p>{user.email}</p>
              </div>

              <div className="info-item">
                <label>User ID:</label>
                <p>{user.id}</p>
              </div>

              <div className="info-item">
                <label>Member Since:</label>
                <p>{new Date(user.createdAt).toLocaleDateString()}</p>
              </div>

              <div className="info-item">
                <label>Last Updated:</label>
                <p>{new Date(user.updatedAt).toLocaleDateString()}</p>
              </div>
            </div>

            <button onClick={handleLogout} className="primary-button">
              Logout
            </button>
          </div>
        </div>
      )}

      {!user && !loading && (
        <div className="no-user">
          <p>Unable to load user profile. Please log in again.</p>
          <button onClick={() => navigate('/login')} className="primary-button">
            Go to Login
          </button>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
