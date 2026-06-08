import { apiClient } from './client';
import type { EmployeeDocument } from '../types';

export const documentApi = {
  getAll: () =>
    apiClient.get<EmployeeDocument[]>('/employee/documents').then((r) => r.data),

  approve: (id: number) =>
    apiClient.put(`/employee/documents/approve/${id}`).then((r) => r.data),

  reject: (id: number, remarks: string) =>
    apiClient
      .put(`/employee/documents/reject/${id}?remarks=${encodeURIComponent(remarks)}`)
      .then((r) => r.data),
};
