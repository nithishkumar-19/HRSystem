import { create } from 'zustand';
import type { Certification, UploadCertificationRequest } from '../types';
import { certificationApi } from '../api';

interface CertificationStore {
  certifications: Certification[];
  loading: boolean;
  error: string | null;
  fetchCertifications: (empId:number) => Promise<void>;
  uploadCertification:(request: UploadCertificationRequest) => Promise<void>
  clearError: () => void;
}

export const useCertificationStore = create<CertificationStore>((set) => ({
  certifications: [],
  loading: false,
  error: null,

  fetchCertifications: async (empId:number) => {
    set({ loading: true, error: null });
    try {
      const data = await certificationApi.getAll(empId);
      set({ certifications: data, loading: false });
    } catch {
      set({ error: 'Failed to load certifications', loading: false });
    }
  },

  uploadCertification: async (request: UploadCertificationRequest) => {
      set({ loading: true, error: null });

      try {
        await certificationApi.upload(request);

        set({
          loading: false,
        });
      } catch {
        set({
          error: "Upload failed",
          loading: false,
        });

        throw new Error("Upload failed");
      }
    },

  clearError: () => set({ error: null }),
}));
