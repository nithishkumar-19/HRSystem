import { apiClient } from './client';
import type { Payslip, GeneratePayslipRequest } from '../types';

export const payslipApi = {
  getByEmployee: (empId: number | string) =>
    apiClient.get<Payslip[]>(`/payslip/employee/${empId}`).then((r) => r.data),

  generate: (data: GeneratePayslipRequest) =>
    apiClient.post<Payslip>('/payslip/generate', data).then((r) => r.data),
};
