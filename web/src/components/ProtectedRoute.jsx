import { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import authService from '../services/authService';

const ProtectedRoute = ({ children }) => {
  const [status, setStatus] = useState('checking');

  useEffect(() => {
    const checkSession = async () => {
      try {
        await authService.getCurrentUser();
        setStatus('authenticated');
      } catch {
        setStatus('unauthenticated');
      }
    };
    checkSession();
  }, []);

  if (status === 'checking') {
    return <div>Loading...</div>;
  }

  if (status === 'unauthenticated') {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default ProtectedRoute;
