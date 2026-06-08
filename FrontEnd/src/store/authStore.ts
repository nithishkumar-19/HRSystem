import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import type { AuthState, UserRole } from '../types';

interface AuthStore extends AuthState {
  login: (username:string, password: string, role: UserRole, employeeId: number | null) => void;
  logout: () => void;
  isAuthenticated: () => boolean;
}

export const useAuthStore = create<AuthStore>()(
  persist(
    (set, get) => ({
      token: null,
      role: null,
      employeeId: null,

      login: (username, password, role, employeeId) => {
        const token = btoa(`${username}:${password}`);
        set({ token, role, employeeId });
      },

      logout: () => {
        set({ token: null, role: null, employeeId: null });
      },

      isAuthenticated: () => !!get().token,
    }),
    {
      name: 'hrms-auth',
      // Sync token to localStorage key that axios interceptor reads
      onRehydrateStorage: () => (state) => {
        if (state?.token) {
          localStorage.setItem('auth', state.token);
        }
      },
    }
  )
);

// Keep legacy localStorage key in sync for the axios interceptor
useAuthStore.subscribe((state) => {
  if (state.token) {
    localStorage.setItem('auth', state.token);
  } else {
    localStorage.removeItem('auth');
  }
});
