import { create } from 'zustand';
import type { Employee } from '../types';
import { employeeApi } from '../api';

interface EmployeeStore {
  employees: Employee[];
  loading: boolean;
  error: string | null;
  fetchEmployees: () => Promise<void>;
  addEmployee: (emp: Employee) => void;
  clearError: () => void;
}

export const useEmployeeStore = create<EmployeeStore>((set) => ({
  employees: [],
  loading: false,
  error: null,

  fetchEmployees: async () => {
    set({ loading: true, error: null });
    try {
      const data = await employeeApi.getAll();
      set({ employees: data, loading: false });
    } catch {
      set({ error: 'Failed to load employees', loading: false });
    }
  },

  addEmployee: (emp) =>
    set((state) => ({ employees: [...state.employees, emp] })),

  clearError: () => set({ error: null }),
}));
