import { apiClient } from './client';
import type { Leave, ApplyLeaveRequest } from '../types';

export const leaveApi = {
  getByEmployee: (empId: number) =>
    apiClient.get<Leave[]>(`/leave/employee/${empId}`).then((r) => r.data),

  getApprovalPendingLeaves: (empId:number) => 
    apiClient.get<Leave[]>(`/leave/employee/requested-leaves/${empId}`).then((r) => r.data),
  

  apply: (data: ApplyLeaveRequest) =>
    apiClient.post<Leave>('/leave/apply-leave', data).then((r) => r.data),

  approve: (id: number, remarks: string) =>
  apiClient.put(`/leave/approve/${id}?remarks=${encodeURIComponent(remarks)}`)
    .then(r => r.data),

  hrConfirmation: (id: number, remarks: string) =>
  apiClient.put(`/hr/leave-confirmation/${id}?remarks=${encodeURIComponent(remarks)}`)
    .then(r => r.data),

  reject: (id: number, remarks: string) =>
    apiClient.put(`/leave/reject/${id}?remarks=${encodeURIComponent(remarks)}`).then((r) => r.data),
};
