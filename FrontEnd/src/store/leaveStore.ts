import { create } from 'zustand';
import type { Leave } from '../types';
import { leaveApi } from '../api';
import { json } from 'react-router-dom';

interface LeaveStore {
  leaves: Leave[];
  Approvalleaves: Leave[];
  loading: boolean;
  error: string | null;
  fetchLeaves: (empId: number) => Promise<void>;
  fetchApprovalLeaves:(empId : number) => Promise<void>;
  clearError: () => void;
}

export const useLeaveStore = create<LeaveStore>((set) => ({
  leaves: [],
  Approvalleaves: [],
  loading: false,
  error: null,

  fetchLeaves: async (empId) => {
    set({ loading: true, error: null });
    try {
      const data = await leaveApi.getByEmployee(empId);
      console.log("Is data type" , typeof data);
      set({ leaves: data, loading: false });
    } catch(error : unknown) {
      console.log(error);
      set({ error: 'Failed to load leaves', loading: false });
    }
  },

  fetchApprovalLeaves: async (empId) => {
    set({ loading: true, error: null });
    try {
      console.log("Fetching Approval Leaves....!");
      const data = await leaveApi.getApprovalPendingLeaves(empId);
      
      set({ Approvalleaves: data, loading: false });
    } catch(error : unknown) {
      console.log(error);
      set({ error: 'Failed to load approval leaves', loading: false });
    }
  },

  clearError: () => set({ error: null }),
}));
