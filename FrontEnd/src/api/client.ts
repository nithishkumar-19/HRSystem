import axios from 'axios';

const BASE_URL = 'http://localhost:8080';

export const apiClient = axios.create({
  baseURL: BASE_URL,
  headers: { 'Content-Type': 'application/json' },
});

// Attach Basic Auth token from localStorage on every request
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('auth');
  if (token) {
    config.headers['Authorization'] = `Basic ${token}`;
  }
  return config;
});

// Redirect to login on 401
apiClient.interceptors.response.use(
  (res) => res,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.clear();
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);
