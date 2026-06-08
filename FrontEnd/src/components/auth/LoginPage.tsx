import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authApi } from '../../api';
import { useAuthStore } from '../../store';
import { FormField } from '../shared/FormField';
import { Button } from '../shared/Button';
import './LoginPage.css';

export const LoginPage = () => {
  const navigate = useNavigate();
  const { login, isAuthenticated, role } = useAuthStore();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError]       = useState('');
  const [loading, setLoading]   = useState(false);

  // Already logged in — redirect
  if (isAuthenticated()) {
    const homeMap = { HR: '/hr', MANAGER: '/manager', EMPLOYEE: '/employee' };
    if (role) navigate(homeMap[role], { replace: true });
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      const data = await authApi.login({ username, password });
      login(username, password, data.role, data.employeeId ?? null);

      const homeMap = { HR: '/hr', MANAGER: '/manager', EMPLOYEE: '/employee' };
      navigate(homeMap[data.role], { replace: true });
    } catch {
      setError('Invalid username or password');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page">
      <div className="login-card">
        <div className="login-card__logo">HRMS</div>
        <p className="login-card__tagline">Human Resource Management System</p>

        <form className="login-card__form" onSubmit={handleSubmit}>
          {error && <div className="login-card__error" role="alert">{error}</div>}

          <FormField
            id="username"
            label="Username"
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            autoComplete="username"
            required
          />

          <FormField
            id="password"
            label="Password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            autoComplete="current-password"
            required
          />

          <Button
            type="submit"
            variant="primary"
            loading={loading}
            className="login-card__submit"
          >
            Sign in
          </Button>
        </form>
      </div>
    </div>
  );
};
