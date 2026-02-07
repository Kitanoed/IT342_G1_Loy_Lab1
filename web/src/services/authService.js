import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

// Create axios instance
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Handle response errors
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    // If session is missing/expired, clear local cached user.
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('user');
      localStorage.removeItem('isAuthenticated');
    }
    return Promise.reject(error);
  }
);

const authService = {
  /**
   * Register a new user
   */
  register: async (email, username, password, firstName, lastName) => {
    try {
      const response = await apiClient.post('/auth/register', {
        email,
        username,
        password,
        firstName,
        lastName,
      });

      return response.data;
    } catch (error) {
      throw error.response?.data?.message || error.message;
    }
  },

  /**
   * Login user
   */
  login: async (email, password) => {
    try {
      const response = await apiClient.post('/auth/login', {
        email,
        password,
      });

      localStorage.setItem('isAuthenticated', 'true');
      if (response.data.user) {
        localStorage.setItem('user', JSON.stringify(response.data.user));
      }

      return response.data;
    } catch (error) {
      throw error.response?.data?.message || error.message;
    }
  },

  /**
   * Get current user profile
   */
  getCurrentUser: async () => {
    try {
      const response = await apiClient.get('/user/me');
      localStorage.setItem('isAuthenticated', 'true');
      localStorage.setItem('user', JSON.stringify(response.data));
      return response.data;
    } catch (error) {
      throw error.response?.data?.message || error.message;
    }
  },

  /**
   * Logout user
   */
  logout: async () => {
    try {
      await apiClient.post('/auth/logout');
    } catch (error) {
      // Clear local state even if request fails.
    }
    localStorage.removeItem('user');
    localStorage.removeItem('isAuthenticated');
  },

  /**
   * Check if user is authenticated
   */
  isAuthenticated: () => {
    return localStorage.getItem('isAuthenticated') === 'true';
  },

  /**
   * Get cached user from local storage
   */
  getStoredUser: () => {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  },
};

export default authService;
