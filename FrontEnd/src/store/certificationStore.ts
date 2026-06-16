import { create } from "zustand";
import type {
  Certification,
  UploadCertificationRequest,
} from "../types";
import { certificationApi } from "../api";

interface CertificationStore {
  certifications: Certification[];
  loading: boolean;
  notifications: number;
  error: string | null;

  fetchCertifications: (empId: number) => Promise<void>;
  uploadCertification: (
    request: UploadCertificationRequest
  ) => Promise<void>;
  fetchNotifications: (empId: number) => Promise<void>;
  clearError: () => void;
}

export const useCertificationStore = create<CertificationStore>((set) => ({
  certifications: [],
  loading: false,
  notifications: 0,
  error: null,

  fetchCertifications: async (empId: number) => {
    set({ loading: true, error: null });

    try {
      const data = await certificationApi.getAll(empId);

      set({
        certifications: data,
        loading: false,
      });
    } catch (error) {
      console.error(error);

      set({
        error: "Failed to load certifications",
        loading: false,
      });
    }
  },

  uploadCertification: async (
    request: UploadCertificationRequest
  ) => {
    set({ loading: true, error: null });

    try {
      await certificationApi.upload(request);

      set({
        loading: false,
      });
    } catch (error) {
      console.error(error);

      set({
        error: "Upload failed",
        loading: false,
      });

      throw error;
    }
  },

  fetchNotifications: async (empId: number) => {
    try {
      const data = await certificationApi.getNotificationCount(
        empId
      );

      set({
        notifications: data,
      });
    } catch (error) {
      console.error(error);

      set({
        error: "Failed to fetch notifications",
      });

      throw error;
    }
  },

  clearError: () =>
    set({
      error: null,
    }),
}));