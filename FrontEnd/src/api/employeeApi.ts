import { apiClient } from './client';
import type { Employee, CreateEmployeeRequest, EmployeeResponse } from '../types';

export const employeeApi = {
  getAll: () =>
    apiClient.get<Employee[]>('/employee/all').then((r) => r.data),

  create: (data: CreateEmployeeRequest) =>
    apiClient.post<Employee>('/employee/create', data).then((r) => r.data),
};
