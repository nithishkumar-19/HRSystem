import { create } from 'zustand';
import type { EmployeeDocument } from '../types';
import { documentApi } from '../api';

interface DocumentStore {
  documents: EmployeeDocument[];
  loading: boolean;
  error: string | null;
  fetchDocuments: () => Promise<void>;
  clearError: () => void;
}

export const useDocumentStore = create<DocumentStore>((set) => ({
  documents: [],
  loading: false,
  error: null,

  fetchDocuments: async () => {
    set({ loading: true, error: null });
    try {
      const data = await documentApi.getAll();
      set({ documents: data, loading: false });
    } catch (error :unknown){
      console.log(`Error while getting certificates: ${error}`);
      set({ error: 'Failed to load documents', loading: false });
    }
  },

  clearError: () => set({ error: null }),
}));
